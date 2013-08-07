package com.cloudmine.api.rest;

public class ObjectModificationRequest extends com.cloudmine.api.rest.BaseObjectModificationRequest {

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.rest.callbacks.Callback<com.cloudmine.api.rest.response.ObjectModificationResponse> callback) {
        super(savable, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, callback);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.CMSessionToken userSession, com.cloudmine.api.rest.callbacks.Callback<com.cloudmine.api.rest.response.ObjectModificationResponse> callback) {
        super(savable, userSession, (com.cloudmine.api.rest.options.CMServerFunction)null, callback);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.cloudmine.api.rest.callbacks.Callback<com.cloudmine.api.rest.response.ObjectModificationResponse> callback) {
        super(savable, (com.cloudmine.api.CMSessionToken)null, serverFunction, callback);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.CMSessionToken userSession, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.cloudmine.api.rest.callbacks.Callback<com.cloudmine.api.rest.response.ObjectModificationResponse> callback) {
        super(savable, userSession, serverFunction, callback);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable) {
        super(savable, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.CMSessionToken token) {
        super(savable, token, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        super(savable, (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        super(savable, token, serverFunction, (com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener) {
        super(savable, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.CMSessionToken token, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener) {
        super(savable, token, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener) {
        super(savable, (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener) {
        super(savable, token, serverFunction, successListener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(savable, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.CMSessionToken token, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(savable, token, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(savable, (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(savable, token, serverFunction, (com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(savable, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.CMSessionToken token, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(savable, token, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(savable, (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, errorListener);
    }

    public ObjectModificationRequest(com.cloudmine.api.rest.Transportable savable, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(savable, token, serverFunction, successListener, errorListener);
    }

}