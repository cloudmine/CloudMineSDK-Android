package com.cloudmine.api.rest;

public class FileCreationRequest extends com.cloudmine.api.rest.BaseFileCreationRequest {

    public FileCreationRequest(com.cloudmine.api.CMFile file) {
        super(file, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.cloudmine.api.CMSessionToken sessionToken) {
        super(file, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        super(file, (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        super(file, sessionToken, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse> successListener) {
        super(file, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse> successListener) {
        super(file, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse> successListener) {
        super(file, (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse> successListener) {
        super(file, sessionToken, serverFunction, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.android.volley.Response.ErrorListener errorListener) {
        super(file, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse>)null, errorListener);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.ErrorListener errorListener) {
        super(file, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse>)null, errorListener);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        super(file, (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse>)null, errorListener);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        super(file, sessionToken, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse>)null, errorListener);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(file, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(file, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(file, (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, errorListener);
    }

    public FileCreationRequest(com.cloudmine.api.CMFile file, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.FileCreationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(file, sessionToken, serverFunction, successListener, errorListener);
    }

}