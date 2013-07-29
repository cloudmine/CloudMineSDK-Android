package com.cloudmine.api.rest;

public class ObjectDeleteRequest extends com.cloudmine.api.rest.BaseObjectDeleteRequest {

    public ObjectDeleteRequest(java.lang.String objectId) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(java.util.Collection<java.lang.String> objectId) {
        super(objectId, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken) {
        super(java.util.Collections.singleton(objectId), sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.CMSessionToken sessionToken) {
        super(objectId, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(java.lang.String objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        super(objectId, (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        super(java.util.Collections.singleton(objectId), sessionToken, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        super(objectId, sessionToken, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(java.lang.String objectId, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, responseListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(java.util.Collection<java.lang.String> objectId, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener) {
        super(objectId, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, responseListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener) {
        super(java.util.Collections.singleton(objectId), sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, responseListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener) {
        super(objectId, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, responseListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(java.lang.String objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, serverFunction, responseListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener) {
        super(objectId, (com.cloudmine.api.CMSessionToken)null, serverFunction, responseListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener) {
        super(java.util.Collections.singleton(objectId), sessionToken, serverFunction, responseListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener) {
        super(objectId, sessionToken, serverFunction, responseListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(java.lang.String objectId, com.android.volley.Response.ErrorListener errorListener) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public ObjectDeleteRequest(java.util.Collection<java.lang.String> objectId, com.android.volley.Response.ErrorListener errorListener) {
        super(objectId, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public ObjectDeleteRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.ErrorListener errorListener) {
        super(java.util.Collections.singleton(objectId), sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public ObjectDeleteRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.ErrorListener errorListener) {
        super(objectId, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public ObjectDeleteRequest(java.lang.String objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public ObjectDeleteRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        super(objectId, (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public ObjectDeleteRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        super(java.util.Collections.singleton(objectId), sessionToken, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public ObjectDeleteRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        super(objectId, sessionToken, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public ObjectDeleteRequest(java.lang.String objectId, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener, com.android.volley.Response.ErrorListener errorListener) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, responseListener, errorListener);
    }

    public ObjectDeleteRequest(java.util.Collection<java.lang.String> objectId, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener, com.android.volley.Response.ErrorListener errorListener) {
        super(objectId, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, responseListener, errorListener);
    }

    public ObjectDeleteRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener, com.android.volley.Response.ErrorListener errorListener) {
        super(java.util.Collections.singleton(objectId), sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, responseListener, errorListener);
    }

    public ObjectDeleteRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener, com.android.volley.Response.ErrorListener errorListener) {
        super(objectId, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, responseListener, errorListener);
    }

    public ObjectDeleteRequest(java.lang.String objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener, com.android.volley.Response.ErrorListener errorListener) {
        super(java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, serverFunction, responseListener, errorListener);
    }

    public ObjectDeleteRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener, com.android.volley.Response.ErrorListener errorListener) {
        super(objectId, (com.cloudmine.api.CMSessionToken)null, serverFunction, responseListener, errorListener);
    }

    public ObjectDeleteRequest(java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener, com.android.volley.Response.ErrorListener errorListener) {
        super(java.util.Collections.singleton(objectId), sessionToken, serverFunction, responseListener, errorListener);
    }

    public ObjectDeleteRequest(java.util.Collection<java.lang.String> objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener, com.android.volley.Response.ErrorListener errorListener) {
        super(objectId, sessionToken, serverFunction, responseListener, errorListener);
    }

    public ObjectDeleteRequest(boolean deleteAll) {
        super(deleteAll, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(boolean deleteAll, com.cloudmine.api.CMSessionToken sessionToken) {
        super(deleteAll, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(boolean deleteAll, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        super(deleteAll, (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(boolean deleteAll, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        super(deleteAll, sessionToken, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(boolean deleteAll, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener) {
        super(deleteAll, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, responseListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(boolean deleteAll, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener) {
        super(deleteAll, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, responseListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(boolean deleteAll, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener) {
        super(deleteAll, (com.cloudmine.api.CMSessionToken)null, serverFunction, responseListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(boolean deleteAll, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener) {
        super(deleteAll, sessionToken, serverFunction, responseListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ObjectDeleteRequest(boolean deleteAll, com.android.volley.Response.ErrorListener errorListener) {
        super(deleteAll, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public ObjectDeleteRequest(boolean deleteAll, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.ErrorListener errorListener) {
        super(deleteAll, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public ObjectDeleteRequest(boolean deleteAll, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        super(deleteAll, (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public ObjectDeleteRequest(boolean deleteAll, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        super(deleteAll, sessionToken, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public ObjectDeleteRequest(boolean deleteAll, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener, com.android.volley.Response.ErrorListener errorListener) {
        super(deleteAll, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, responseListener, errorListener);
    }

    public ObjectDeleteRequest(boolean deleteAll, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener, com.android.volley.Response.ErrorListener errorListener) {
        super(deleteAll, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, responseListener, errorListener);
    }

    public ObjectDeleteRequest(boolean deleteAll, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener, com.android.volley.Response.ErrorListener errorListener) {
        super(deleteAll, (com.cloudmine.api.CMSessionToken)null, serverFunction, responseListener, errorListener);
    }

    public ObjectDeleteRequest(boolean deleteAll, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> responseListener, com.android.volley.Response.ErrorListener errorListener) {
        super(deleteAll, sessionToken, serverFunction, responseListener, errorListener);
    }

}