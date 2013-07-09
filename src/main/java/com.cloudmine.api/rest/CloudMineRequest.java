package com.cloudmine.api.rest;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.Strings;
import com.cloudmine.api.rest.AndroidHeaderFactory;
import com.cloudmine.api.rest.HeaderFactory;
import com.cloudmine.api.rest.ResponseTimeDataStore;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public abstract class CloudMineRequest<RESPONSE> extends Request<RESPONSE> {

    protected static String BASE_URL = "https://api.cloudmine.me/v1/app/";
    private static AndroidHeaderFactory HEADER_FACTORY = new AndroidHeaderFactory();
    private static Map<String, String> DEFAULT_HEADERS = new HashMap<String, String>();
    static {
        DEFAULT_HEADERS.put(HeaderFactory.AGENT_HEADER_KEY, AndroidHeaderFactory.CLOUD_MINE_AGENT);
    }
    private Response.Listener<RESPONSE> responseListener;
    private String body;
    private String sessionTokenString;

    private static String getUrl(String url) {
        return new StringBuilder(BASE_URL).append(CMApiCredentials.getApplicationIdentifier()).append("/").append(url).toString();
    }

    public CloudMineRequest(int method, String url, Response.ErrorListener errorListener, Response.Listener<RESPONSE> successListener) {
        this(method, url, null, null, errorListener, successListener);
    }

    public CloudMineRequest(int method, String url, String body, CMSessionToken sessionToken, Response.ErrorListener errorListener, Response.Listener<RESPONSE> successListener) {
        super(method, getUrl(url), errorListener);
        this.body = body;
        responseListener = successListener;
        boolean isValidSessionToken = sessionToken != null && !CMSessionToken.FAILED.equals(sessionToken);
        if(isValidSessionToken) sessionTokenString = sessionToken.getSessionToken();

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
        if(responseListener != null) responseListener.onResponse(response);
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headerMap = new HashMap<String, String>(DEFAULT_HEADERS);
        headerMap.put(HeaderFactory.DEVICE_HEADER_KEY, getDeviceHeaderValue());
        headerMap.put(HeaderFactory.HEADER_KEY, CMApiCredentials.getApplicationApiKey()); //worry about sync issues? not now but could be an issue
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
}
