package com.cloudmine.api.rest;

public class FileLoadRequest extends com.cloudmine.api.rest.BaseFileLoadRequest {

    public FileLoadRequest(java.lang.String fileName, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileLoadResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(fileName, sessionToken, successListener, errorListener);
    }

    public FileLoadRequest(java.lang.String fileName, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileLoadResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(fileName, (com.cloudmine.api.CMSessionToken)null, successListener, errorListener);
    }

    public FileLoadRequest(java.lang.String fileName, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileLoadResponse> successListener) {
        super(fileName, sessionToken, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public FileLoadRequest(java.lang.String fileName, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileLoadResponse> successListener) {
        super(fileName, (com.cloudmine.api.CMSessionToken)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

}