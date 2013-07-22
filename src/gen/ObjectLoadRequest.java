package com.cloudmine.api.rest;

public class ObjectLoadRequest extends com.cloudmine.api.rest.BaseObjectLoadRequest {

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken sessionToken, android.os.Handler handler) {
        super(objectIds, sessionToken, handler);
    }

    public ObjectLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, android.os.Handler handler) {
        super((java.util.Collection<java.lang.String>)null, sessionToken, handler);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, android.os.Handler handler) {
        super(java.util.Collections.singleton(objectId), sessionToken, handler);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(objectIds, sessionToken, successListener, errorListener);
    }

    public ObjectLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super((java.util.Collection<java.lang.String>)null, sessionToken, successListener, errorListener);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(java.util.Collections.singleton(objectId), sessionToken, successListener, errorListener);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectIds, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(objectIds, (com.cloudmine.api.CMSessionToken)null, successListener, errorListener);
    }

    public ObjectLoadRequest(com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super((java.util.Collection<java.lang.String>)null, (com.cloudmine.api.CMSessionToken)null, successListener, errorListener);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, successListener, errorListener);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(objectIds, sessionToken, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super((java.util.Collection<java.lang.String>)null, sessionToken, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(java.util.Collections.singleton(objectId), sessionToken, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectIds, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(objectIds, (com.cloudmine.api.CMSessionToken)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super((java.util.Collection<java.lang.String>)null, (com.cloudmine.api.CMSessionToken)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

}