package com.cloudmine.api.db;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;
import android.os.IBinder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Service that has a timer task that runs every minute. The task loads all of the requests with syncstatus = 0,
 * sets their status to 1, and performs all the requests. On a successful request, sets the status to 2; on failure,
 * sets the status back to 0. This is done in a separate thread; if the service is started while an old thread is
 * already running, it waits 5 seconds and retries. If it is still running after 5 seconds, it gives up.
 * To force the service to run, start with the FORCE_RUN_KEY boolean set to true
 *
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class RequestPerformerService extends Service {
    private static final Logger LOG = LoggerFactory.getLogger(RequestPerformerService.class);
    public static final String FORCE_RUN_KEY = "forceRun";
    public static final int MINUTES_BETWEEN_RUNS = 10;
    public static final int CONNECTION_TIMEOUT_SECONDS = 30;
    public static final int SO_TIMEOUT_SECONDS = 30;
    private RequestDBOpenHelper openHelper;
    private ConnectivityManager connectivityManager;

    private Timer timer;
    private final TimerTask run = new TimerTask() {
        //this lets us cancel the timer while it is running
        private AtomicBoolean keepRunning = new AtomicBoolean(true);
        private Thread requestPerformingThread = null;
        private int timedOutRequestCount = 0;
        public void run() {
            if(isAlreadyRunning()) {
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {}
                if(isAlreadyRunning()) {
                    return;
                }
            }
            LOG.debug("Running!");
            //Kick this off on its own thread so long running operations don't hog the timer thread
            requestPerformingThread = new Thread(new Runnable() {

                private static final int MAX_TIMEOUT_EXPONENT = 9;

                @Override
                public void run() {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    boolean isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
                    if (isConnected) {
                        Map<Integer, RequestDBObject> unsentRequests = openHelper.retrieveRequestsForSending(getApplicationContext());
                        if (unsentRequests == null || unsentRequests.isEmpty()) return;
                        for (Map.Entry<Integer, RequestDBObject> entries : unsentRequests.entrySet()) {
                            //Remaining requests will be skipped, set them unsynchronized
                            if (!keepRunning.get()) {
                                LOG.debug("Done running, setting remaining entries to unsynchronized: " + entries.getKey());
                                openHelper.setUnsychronized(entries.getKey());
                                continue;
                            }
                            sendRequest(entries.getKey(), entries.getValue());
                        }
                    }
                }

                /**
                 * Perform the request, and then set its sync status to either 0 if unsuccessful but retriable, 3
                 * if unsuccessful and not retriable, or 2 if successful
                 * @param id
                 * @param next
                 */
                private void sendRequest(final Integer id, RequestDBObject next) {
                    AndroidHttpClient androidClient = null;
                    HttpResponse response = null;
                    Throwable thrown = null;
                    try {
                        androidClient = createClient();
                        LOG.debug("Running request " + next);
                        HttpUriRequest request = next.toHttpRequest();
                        response = androidClient.execute(request);
                    } catch (ClientProtocolException e) {
                        thrown = e;
                        LOG.error("Protocol exception executing request " + next, e);
                    } catch (IOException e) {
                        thrown = e;
                        LOG.error("IO exception executing request " + next, e);
                    } catch(Throwable t) {
                        thrown = t;
                        LOG.error("Unknown exception occurred", t);
                    } finally {
                        if(androidClient != null)
                            androidClient.close();

                        int statusCode = getStatusCode(response);
                        if(wasSuccess(statusCode)) {
                            LOG.debug("Successfully performed request " + id);
                            openHelper.setSynchronized(id);
                        } else {
                            if(wasUnRetriable(statusCode)){
                                LOG.debug("Permanently failed request " + id);
                                openHelper.setPermanentlyFailed(id);
                            } else {
                                LOG.debug("Failed performing request " + id);
                                openHelper.setUnsychronized(id);
                            }

                        }
                        sleepIfTimedOut(response, thrown);
                    }
                }

                private String getResponseBody(HttpResponse serverResponse) {
                    String responseBody = "";
                    HttpEntity entity = null;
                    HttpEntity temp = serverResponse.getEntity();
                    if (temp != null) {
                        try {
                            entity = new BufferedHttpEntity(temp);
                            responseBody = EntityUtils.toString(entity, "UTF-8");
                        } catch (IOException e) {
                            LOG.error("Couldn't read message body", e);
                        }
                    }
                    return responseBody;
                }

                /**
                 * When a request times out, wait an increasing amount of time before trying again. Will
                 * increase exponentially till it reaches 512 seconds, then will remain there. Resets once
                 * requests stop timing out.
                 * @param response
                 * @param thrown
                 */
                private void sleepIfTimedOut(HttpResponse response, Throwable thrown) {
                    if(wasTimedOut(response, thrown)) {
                        timedOutRequestCount++;
                        int exponent = timedOutRequestCount < MAX_TIMEOUT_EXPONENT ?
                                timedOutRequestCount :
                                MAX_TIMEOUT_EXPONENT;
                        int waitTimeSeconds = (int) ((int)Math.pow(2, exponent) + Math.random()*10);
                        LOG.debug("Timed out, sleeping for " + waitTimeSeconds + " seconds");
                        try {
                            Thread.sleep(waitTimeSeconds * 1000);
                        } catch (InterruptedException e) {}
                    }else {
                        timedOutRequestCount = 0;
                    }
                }
            });
            requestPerformingThread.start();
        }

        private boolean isAlreadyRunning() {
            return requestPerformingThread != null && requestPerformingThread.isAlive();
        }

        @Override
        public boolean cancel() {
            boolean wasCancelled = super.cancel();
            LOG.debug("Cancelling RequestPerformerService...");
            keepRunning.set(false);
            return wasCancelled;
        }
    };

    private static boolean wasTimedOut(HttpResponse response, Throwable thrown) {
        if(thrown != null) {
            if(thrown instanceof SocketTimeoutException)
                return true;
            if(thrown instanceof NoHttpResponseException)
                return true;
            if(thrown instanceof ConnectTimeoutException)
                return true;
        }
        if(response != null && response.getStatusLine() != null) {
            int statusCode = response.getStatusLine().getStatusCode();
            return statusCode == 500;
        }
        return false;
    }

    private AndroidHttpClient createClient() {
        AndroidHttpClient client = AndroidHttpClient.newInstance("CloudMine", this);
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT_SECONDS * 1000);
        HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT_SECONDS * 1000);
        return client;
    }

    private boolean wasSuccess(int statusCode) {
        return wasBetween(statusCode, 199, 300);
    }

    private boolean wasBetween(int statusCode, int min, int max) {
        return (min < statusCode && statusCode < max);
    }

    private boolean wasUnRetriable(int statusCode) {
        return wasBetween(statusCode, 299, 501);
    }

    private int getStatusCode(HttpResponse response) {
        if(response == null || response.getStatusLine() == null) {
            LOG.debug("Response or status line was null, response: " + response);
            return 0;
        }
        int statusCode = response.getStatusLine().getStatusCode();
        LOG.debug("Status code: " + statusCode);
        return statusCode;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        LOG.debug("onStartCommand");
        boolean forceRun = intent.getBooleanExtra(FORCE_RUN_KEY, false);
        if(forceRun) {
            forceRun();
        }
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        catchUncaughtExceptions();

        LOG.debug("onCreate");

        connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        openHelper = new RequestDBOpenHelper(getApplicationContext());
        //If this is getting created, any requests that were in progress are not currently running, so set them back
        //to unsynchronized

        openHelper.setInProgressToUnsynchronized();
        timer = new Timer();
        timer.schedule(run, 0, MINUTES_BETWEEN_RUNS * 60 * 1000);
    }

    private void forceRun() {
        new Thread(run).start();
    }

    /**
     * This intercepts any uncaught exceptions and restarts the service
     */
    private void catchUncaughtExceptions() {
        final Context applicationContext = getApplicationContext();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {

                LOG.error("Crashed, restarting service ", throwable);
                applicationContext.startService(new Intent(applicationContext, RequestPerformerService.class));

                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LOG.debug("onDestroy");
        timer.cancel();
    }
}
