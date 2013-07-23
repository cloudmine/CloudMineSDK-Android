package com.cloudmine.api.rest;

public class ObjectLoadRequest extends com.cloudmine.api.rest.BaseObjectLoadRequest {

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        super(objectIds, sessionToken, serverFunction, handler);
    }

    public ObjectLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        super((java.util.Collection<java.lang.String>)null, sessionToken, serverFunction, handler);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        super(java.util.Collections.singleton(objectId), sessionToken, serverFunction, handler);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        super(objectIds, (com.cloudmine.api.CMSessionToken)null, serverFunction, handler);
    }

    public ObjectLoadRequest(com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        super((java.util.Collection<java.lang.String>)null, (com.cloudmine.api.CMSessionToken)null, serverFunction, handler);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, serverFunction, handler);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken sessionToken, android.os.Handler handler) {
        super(objectIds, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public ObjectLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, android.os.Handler handler) {
        super((java.util.Collection<java.lang.String>)null, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, android.os.Handler handler) {
        super(java.util.Collections.singleton(objectId), sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectIds, android.os.Handler handler) {
        super(objectIds, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public ObjectLoadRequest(android.os.Handler handler) {
        super((java.util.Collection<java.lang.String>)null, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public ObjectLoadRequest(java.lang.String objectId, android.os.Handler handler) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(objectIds, sessionToken, serverFunction, successListener, errorListener);
    }

    public ObjectLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super((java.util.Collection<java.lang.String>)null, sessionToken, serverFunction, successListener, errorListener);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(java.util.Collections.singleton(objectId), sessionToken, serverFunction, successListener, errorListener);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(objectIds, (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, errorListener);
    }

    public ObjectLoadRequest(com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super((java.util.Collection<java.lang.String>)null, (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, errorListener);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, errorListener);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(objectIds, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public ObjectLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super((java.util.Collection<java.lang.String>)null, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(java.util.Collections.singleton(objectId), sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectIds, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(objectIds, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public ObjectLoadRequest(com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super((java.util.Collection<java.lang.String>)null, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(objectIds, sessionToken, serverFunction, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super((java.util.Collection<java.lang.String>)null, sessionToken, serverFunction, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(java.util.Collections.singleton(objectId), sessionToken, serverFunction, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(objectIds, (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super((java.util.Collection<java.lang.String>)null, (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(objectIds, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super((java.util.Collection<java.lang.String>)null, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(java.util.Collections.singleton(objectId), sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectIds, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(objectIds, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super((java.util.Collection<java.lang.String>)null, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

}