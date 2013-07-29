package com.cloudmine.api.rest;

public class ObjectLoadRequest extends com.cloudmine.api.rest.BaseObjectLoadRequest {

    public ObjectLoadRequest(com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super((java.util.Collection<java.lang.String>)null, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectId, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(objectId, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super((java.util.Collection<java.lang.String>)null, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(java.util.Collections.singleton(objectId), sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.CMSessionToken sessionToken, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(objectId, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super((java.util.Collection<java.lang.String>)null, (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(objectId, (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super((java.util.Collection<java.lang.String>)null, sessionToken, serverFunction, successListener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(java.util.Collections.singleton(objectId), sessionToken, serverFunction, successListener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(objectId, sessionToken, serverFunction, successListener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ObjectLoadRequest(com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super((java.util.Collection<java.lang.String>)null, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectId, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(objectId, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public ObjectLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super((java.util.Collection<java.lang.String>)null, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(java.util.Collections.singleton(objectId), sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.CMSessionToken sessionToken, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(objectId, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public ObjectLoadRequest(com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super((java.util.Collection<java.lang.String>)null, (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, errorListener);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, errorListener);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(objectId, (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, errorListener);
    }

    public ObjectLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super((java.util.Collection<java.lang.String>)null, sessionToken, serverFunction, successListener, errorListener);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(java.util.Collections.singleton(objectId), sessionToken, serverFunction, successListener, errorListener);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(objectId, sessionToken, serverFunction, successListener, errorListener);
    }

    public ObjectLoadRequest(android.os.Handler handler) {
        super((java.util.Collection<java.lang.String>)null, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public ObjectLoadRequest(java.lang.String objectId, android.os.Handler handler) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectId, android.os.Handler handler) {
        super(objectId, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public ObjectLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, android.os.Handler handler) {
        super((java.util.Collection<java.lang.String>)null, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, android.os.Handler handler) {
        super(java.util.Collections.singleton(objectId), sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.CMSessionToken sessionToken, android.os.Handler handler) {
        super(objectId, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public ObjectLoadRequest(com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        super((java.util.Collection<java.lang.String>)null, (com.cloudmine.api.CMSessionToken)null, serverFunction, handler);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, serverFunction, handler);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        super(objectId, (com.cloudmine.api.CMSessionToken)null, serverFunction, handler);
    }

    public ObjectLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        super((java.util.Collection<java.lang.String>)null, sessionToken, serverFunction, handler);
    }

    public ObjectLoadRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        super(java.util.Collections.singleton(objectId), sessionToken, serverFunction, handler);
    }

    public ObjectLoadRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        super(objectId, sessionToken, serverFunction, handler);
    }

}