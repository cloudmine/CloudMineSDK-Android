package com.cloudmine.api.rest;

import android.os.Handler;
import android.os.Message;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.HasHandler;
import com.cloudmine.api.Strings;
import com.cloudmine.api.rest.callbacks.Callback;
import com.cloudmine.api.rest.response.ResponseBase;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public abstract class CloudMineRequest<RESPONSE> extends Request<RESPONSE>  implements HasHandler{
    protected static String BASE_URL = "https://api.cloudmine.me/v1/app/";
    protected static String USER = "/user";

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

    private static Map<String, String> DEFAULT_HEADERS = new HashMap<String, String>();
    static {
        DEFAULT_HEADERS.put(HeaderFactory.AGENT_HEADER_KEY, AndroidHeaderFactory.CLOUD_MINE_AGENT);
    }
    private Response.Listener<RESPONSE> responseListener;
    private String body;
    private String sessionTokenString;
    private Handler handler;

    private static String getUrl(String url) {
        return new StringBuilder(BASE_URL).append(CMApiCredentials.getApplicationIdentifier()).append(url).toString();
    }

    public CloudMineRequest(int method, String url, Response.Listener<RESPONSE> successListener, Response.ErrorListener errorListener) {
        this(method, url, null, null, successListener, errorListener);
    }

    public CloudMineRequest(int method, String url, CMSessionToken sessionToken, Response.Listener<RESPONSE> successListener, Response.ErrorListener errorListener) {
        this(method, url, null, sessionToken, successListener, errorListener);
    }

    public CloudMineRequest(int method, String url, String body, Response.Listener<RESPONSE> successListener, Response.ErrorListener errorListener) {
        this(method, url, body, null, successListener, errorListener);
    }

    public CloudMineRequest(int method, String url, String body, CMSessionToken sessionToken, Response.Listener<RESPONSE> successListener, Response.ErrorListener errorListener) {
        super(method, getUrl(url), errorListener);
        this.body = body;
        responseListener = successListener;
        boolean isValidSessionToken = !(sessionToken == null || CMSessionToken.FAILED.equals(sessionToken));
        if(isValidSessionToken) sessionTokenString = sessionToken.getSessionToken();
        System.out.println("url=" + getUrl(url) + " valid session? " + isValidSessionToken + " with body: " + body);
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

    @Override
    protected void deliverResponse(RESPONSE response) {
        if(handler != null) {
            synchronized (handler) { //see deliver error for why we check this twice
                if(handler != null) {
                    Message msg = Message.obtain(handler);
                    msg.obj = response;
                    msg.arg1 = getRequestType();
                    if(response instanceof ResponseBase) msg.arg2 = ((ResponseBase)response).getStatusCode();
                    handler.dispatchMessage(msg);
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
            synchronized (handler) {//only waste the time synchronizing if we have a handler
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
        Map<String, String> headerMap = new HashMap<String, String>(DEFAULT_HEADERS);
        headerMap.put(HeaderFactory.DEVICE_HEADER_KEY, getDeviceHeaderValue());
        headerMap.put(HeaderFactory.API_HEADER_KEY, CMApiCredentials.getApplicationApiKey()); //worry about sync issues? not now but could be an issue
        if(Strings.isNotEmpty(sessionTokenString)) headerMap.put(HeaderFactory.SESSION_TOKEN_HEADER_KEY, sessionTokenString);
        return headerMap;
    }

    private String getDeviceHeaderValue() {
        String deviceId = DeviceIdentifier.getUniqueId();
        String timingHeaders = ResponseTimeDataStore.getContentsAsStringAndClearMap();
        String deviceHeaderValue = deviceId;
        if(Strings.isNotEmpty(timingHeaders)) deviceHeaderValue += "; " + timingHeaders;
        return deviceHeaderValue;
    }

    public abstract int getRequestType();

    public void setHandler(Handler handler) {
        synchronized (handler) {
            this.handler = handler;
        }
    }
}
