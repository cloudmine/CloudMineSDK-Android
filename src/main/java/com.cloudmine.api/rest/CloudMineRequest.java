package com.cloudmine.api.rest;

import android.os.Handler;
import android.os.Message;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.HasHandler;
import com.cloudmine.api.rest.callbacks.Callback;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.ResponseBase;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public abstract class CloudMineRequest<RESPONSE> extends Request<RESPONSE>  implements HasHandler{

    public static final String REQUEST_TAG = "CloudMineRequest";

    protected static String BASE_URL = "https://api.cloudmine.me/v1/app/";
    protected static String USER = "/user";
    public static final CloudMineRequest FAKE_REQUEST = new CloudMineRequest(Method.DEPRECATED_GET_OR_POST, new CMURLBuilder("", true), null, null, null) {
        public static final int FAKE_REQUEST_TYPE = -1;
        @Override
        protected Response parseNetworkResponse(NetworkResponse networkResponse) {
            return null;
        }

        @Override
        public int getRequestType() {
            return FAKE_REQUEST_TYPE;
        }

        @Override
        public int compareTo(Object another) {
            return 0;
        }
    };

    protected static String user(String url) {
        return USER + url;
    }

    protected static Response.ErrorListener errorFromCallback(final Callback callback) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onFailure(volleyError, "VolleyError");
            }
        };
    }

    protected static <T> Response.Listener<T> successFromCallback(final Callback<T> callback) {
        return new Response.Listener<T>() {
            @Override
            public void onResponse(T t) {
                callback.onCompletion(t);
            }
        };
    }

    private static long applicationSoftTtl = 0;
    private static long applicationTtl = 120000;
    private static boolean isCachingEnabled = false;

    public static boolean isCachingEnabled() {
        return isCachingEnabled;
    }

    public static void setCachingEnabled(boolean isCachingEnabled) {
        CloudMineRequest.isCachingEnabled = isCachingEnabled;
    }

    public static long getApplicationSoftTtl() {
        return applicationSoftTtl;
    }

    /**
     * Set the default soft ttl, in milliseconds The soft ttl is how long a cache entry is able to stand in for a network call.
     * The default is 0, so all calls go to the network in addition to using the cache. This means if there is a cache entry,
     * the Response Listener or handle will receive two results - the cached value and the network value
     * If this value is modified, it should be from the base Application or every Activity, just like {@link CMApiCredentials#initialize(String, String, Object)}.
     * It should not be changed for a series of requests and then switched back - instead, make use of {@link #setSoftTtl(long)} and add
     * the request to the RequestQueue manually
     * @param applicationSoftTtl
     */
    public static void setApplicationSoftTtl(long applicationSoftTtl) {
        CloudMineRequest.applicationSoftTtl = applicationSoftTtl;
    }

    public static long getApplicationTtl() {
        return applicationTtl;
    }

    /**
     * Set the default ttl, in milliseconds. The TTL is how long a cache entry remains valid for - once a cache entry
     * is expired, it will be ignored and calls will go straight to network. Default is 2 minutes
     * If this value is modified, it should be from the base Application or every Activity, just like {@link CMApiCredentials#initialize(String, String, Object)}.
     * It should not be changed for a series of requests and then switched back - instead, make use of {@link #setTtl(long)} and add
     * the request to the RequestQueue manually
     * @param applicationTtl
     */
    public static void setApplicationTtl(long applicationTtl) {
        CloudMineRequest.applicationTtl = applicationTtl;
    }

    private long softTtl = applicationSoftTtl;
    private long ttl = applicationTtl;
    private Response.Listener<RESPONSE> responseListener;
    private String apiKey;
    private String body;
    private String sessionTokenString;
    private Handler handler;
    private final Object handlerLock = new Object();

    protected static String addServerFunction(String url, CMServerFunction serverFunction) {
        if(serverFunction != null) return url + "?" + serverFunction.asUrlString();
        else                       return url;
    }

    protected static String getUrl(CMApiCredentials apiCredentials, String url) {
        String baseUrl = apiCredentials.getBaseUrl();
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        if(!baseUrl.endsWith("/")) urlBuilder.append("/");
        return urlBuilder.append(apiCredentials.getIdentifier()).append(url).toString();
    }

    public CloudMineRequest(int method, CMURLBuilder url, CMServerFunction serverFunction, Response.Listener<RESPONSE> successListener, Response.ErrorListener errorListener) {
        this(method, url, null, null, serverFunction, successListener, errorListener);
    }


    public CloudMineRequest(int method, CMURLBuilder url, CMSessionToken sessionToken, CMServerFunction serverFunction, Response.Listener<RESPONSE> successListener, Response.ErrorListener errorListener) {
        this(method, url, null, sessionToken, serverFunction, successListener, errorListener);
    }

    public CloudMineRequest(int method, CMURLBuilder url, String body, CMServerFunction serverFunction, Response.Listener<RESPONSE> successListener, Response.ErrorListener errorListener) {
        this(method, url, body, null, serverFunction, successListener, errorListener);
    }

    public CloudMineRequest(int method, CMURLBuilder url, String body, CMSessionToken sessionToken, CMServerFunction serverFunction, Response.Listener<RESPONSE> successListener, Response.ErrorListener errorListener) {
        this(method, url.serverFunction(serverFunction).asUrlString(), body, sessionToken, successListener, errorListener);
    }

    public CloudMineRequest(int method, String url, String body, CMSessionToken sessionToken, CMServerFunction serverFunction, Response.Listener<RESPONSE> successListener, Response.ErrorListener errorListener) {
        this(method, addServerFunction(url, serverFunction), body, sessionToken, successListener, errorListener);
    }

    public CloudMineRequest(int method, String url, String body, CMSessionToken sessionToken, CMApiCredentials apiCredentials, CMServerFunction serverFunction, Response.Listener<RESPONSE> successListener, Response.ErrorListener errorListener) {
        this(method, addServerFunction(url, serverFunction), body, sessionToken, apiCredentials, successListener, errorListener);
    }

    public CloudMineRequest(int method, String url, String body, CMSessionToken sessionToken, CMApiCredentials apiCredentials, Response.Listener<RESPONSE> successListener, Response.ErrorListener errorListener) {
        super(method, getUrl(apiCredentials, url), errorListener);
        this.apiKey = apiCredentials == null ? CMApiCredentials.getApplicationApiKey() : apiCredentials.getApiKey();
        this.body = body;
        responseListener = successListener;
        boolean isValidSessionToken = !(sessionToken == null || CMSessionToken.FAILED.equals(sessionToken));
        if(isValidSessionToken) sessionTokenString = sessionToken.getSessionToken();
        setTag(REQUEST_TAG);
    }

    public CloudMineRequest(int method, String url, String body, CMSessionToken sessionToken, Response.Listener<RESPONSE> successListener, Response.ErrorListener errorListener) {
        this(method, url, body, sessionToken, CMApiCredentials.getCredentials(), successListener, errorListener);
    }

    @Override
    protected abstract Response<RESPONSE> parseNetworkResponse(NetworkResponse networkResponse);

    public byte[] getBody() {
        if(body == null) return null;
        try {
            return body.getBytes(getParamsEncoding());
        } catch (UnsupportedEncodingException e) {
            return body.getBytes();
        }
    }

    public String getBodyContentType() {
        return "application/json; charset=" + getParamsEncoding();
    }

    public void setSessionTokenString(String sessionTokenString) {
        this.sessionTokenString = sessionTokenString;
    }

    @Override
    protected void deliverResponse(RESPONSE response) {
        System.out.println("Delivering response " + response);
        if(handler != null) {
            synchronized (handlerLock) { //see deliver error for why we check this twice
                if(handler != null) {
                    Message msg = Message.obtain(handler);
                    msg.obj = response;
                    msg.arg1 = getRequestType();
                    if(response instanceof ResponseBase) msg.arg2 = ((ResponseBase)response).getStatusCode();
                    handler.sendMessage(msg);
                } else {
                    deliverResponseNoHandler(response);
                }
            }
        } else {
            deliverResponseNoHandler(response);
        }
    }

    private void deliverResponseNoHandler(RESPONSE response) {
        if(responseListener != null) responseListener.onResponse(response);
    }

    public void deliverError(VolleyError error) {
        if(handler != null) {
            synchronized (handlerLock) {//only waste the time synchronizing if we have a handler
                if(handler != null) {   //so we need to recheck that no one has messed with our handler
                    Message msg = Message.obtain(handler);
                    msg.obj = error;
                    msg.arg1 = getRequestType();
                    NetworkResponse networkResponse = error.networkResponse;
                    if(networkResponse != null) msg.arg2 = networkResponse.statusCode;
                    handler.dispatchMessage(msg);
                } else {
                    deliverErrorForNoHandler(error);
                }
            }
        }else {
            deliverErrorForNoHandler(error);
        }
    }

    private void deliverErrorForNoHandler(VolleyError error) {
        super.deliverError(error);
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headerMapping = AndroidHeaderFactory.getHeaderMapping(sessionTokenString, apiKey);
        return headerMapping;
    }

    protected Cache.Entry getCacheEntry(NetworkResponse response) {
        if(isCachingEnabled) {
            Cache.Entry entry = new Cache.Entry();
            entry.data = response.data;
            entry.serverDate = System.currentTimeMillis();
            entry.softTtl = System.currentTimeMillis() + getSoftTtl();
            entry.ttl = System.currentTimeMillis() + getTtl();
            return entry;
        }else {
            return getCacheEntry();
        }
    }

    public long getSoftTtl() {
        return softTtl;
    }

    public void setSoftTtl(long softTtl) {
        this.softTtl = softTtl;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public abstract int getRequestType();

    public void setHandler(Handler handler) {
        synchronized (handlerLock) {
            this.handler = handler;
        }
    }
}
