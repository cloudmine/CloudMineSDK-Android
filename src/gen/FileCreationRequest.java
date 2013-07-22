package com.cloudmine.api.rest;

public class FileCreationRequest extends com.cloudmine.api.rest.BaseFileCreationRequest {

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(file, sessionToken, successListener, errorListener);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(file, (com.cloudmine.api.CMSessionToken)null, successListener, errorListener);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.ErrorListener errorListener) {
        super(file, sessionToken, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse>)null, errorListener);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.android.volley.Response.ErrorListener errorListener) {
        super(file, (com.cloudmine.api.CMSessionToken)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse>)null, errorListener);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse> successListener) {
        super(file, sessionToken, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse> successListener) {
        super(file, (com.cloudmine.api.CMSessionToken)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.cloudmine.api.CMSessionToken sessionToken) {
        super(file, sessionToken, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file) {
        super(file, (com.cloudmine.api.CMSessionToken)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

}