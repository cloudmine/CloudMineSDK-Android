package com.cloudmine.api.rest;

public class ObjectModificationRequest extends com.cloudmine.api.rest.BaseObjectModificationRequest {

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.CMSessionToken token, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(savable, token, successListener, errorListener);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(savable, (com.cloudmine.api.CMSessionToken)null, successListener, errorListener);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.CMSessionToken token, com.android.volley.Response.ErrorListener errorListener) {
        super(savable, token, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.android.volley.Response.ErrorListener errorListener) {
        super(savable, (com.cloudmine.api.CMSessionToken)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.CMSessionToken token, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener) {
        super(savable, token, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener) {
        super(savable, (com.cloudmine.api.CMSessionToken)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.CMSessionToken token) {
        super(savable, token, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable) {
        super(savable, (com.cloudmine.api.CMSessionToken)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.CMSessionToken userSession, com.cloudmine.api.rest.callbacks.Callback<com.cloudmine.api.rest.response.ObjectModificationResponse> callback) {
        super(savable, userSession, callback);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.rest.callbacks.Callback<com.cloudmine.api.rest.response.ObjectModificationResponse> callback) {
        super(savable, (com.cloudmine.api.CMSessionToken)null, callback);
    }

}