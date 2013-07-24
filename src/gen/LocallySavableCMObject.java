package com.cloudmine.api.db;

public class LocallySavableCMObject extends com.cloudmine.api.db.BaseLocallySavableCMObject {

    public com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        return super.delete(context, sessionToken, serverFunction, successListener, errorListener);
    }

    public com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        return super.delete(context, (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, errorListener);
    }

    public com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        return super.delete(context, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        return super.delete(context, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        return super.delete(context, sessionToken, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        return super.delete(context, (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.ErrorListener errorListener) {
        return super.delete(context, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, com.android.volley.Response.ErrorListener errorListener) {
        return super.delete(context, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener) {
        return super.delete(context, sessionToken, serverFunction, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener) {
        return super.delete(context, (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener) {
        return super.delete(context, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener) {
        return super.delete(context, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        return super.delete(context, sessionToken, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        return super.delete(context, (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, com.cloudmine.api.CMSessionToken sessionToken) {
        return super.delete(context, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context) {
        return super.delete(context, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.delete(context, objectIds, sessionToken, serverFunction, successListener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.delete(context, java.util.Collections.singleton(objectId), sessionToken, serverFunction, successListener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.delete(context, objectIds, (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.lang.String objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.delete(context, java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.delete(context, objectIds, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.delete(context, java.util.Collections.singleton(objectId), sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.delete(context, objectIds, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.lang.String objectId, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.delete(context, java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.delete(context, objectIds, sessionToken, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.delete(context, java.util.Collections.singleton(objectId), sessionToken, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.delete(context, objectIds, (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.lang.String objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.delete(context, java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.delete(context, objectIds, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.delete(context, java.util.Collections.singleton(objectId), sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.delete(context, objectIds, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.lang.String objectId, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.delete(context, java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener) {
        return BaseLocallySavableCMObject.delete(context, objectIds, sessionToken, serverFunction, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener) {
        return BaseLocallySavableCMObject.delete(context, java.util.Collections.singleton(objectId), sessionToken, serverFunction, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener) {
        return BaseLocallySavableCMObject.delete(context, objectIds, (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.lang.String objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener) {
        return BaseLocallySavableCMObject.delete(context, java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, serverFunction, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener) {
        return BaseLocallySavableCMObject.delete(context, objectIds, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener) {
        return BaseLocallySavableCMObject.delete(context, java.util.Collections.singleton(objectId), sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener) {
        return BaseLocallySavableCMObject.delete(context, objectIds, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.lang.String objectId, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> successListener) {
        return BaseLocallySavableCMObject.delete(context, java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        return BaseLocallySavableCMObject.delete(context, objectIds, sessionToken, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        return BaseLocallySavableCMObject.delete(context, java.util.Collections.singleton(objectId), sessionToken, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        return BaseLocallySavableCMObject.delete(context, objectIds, (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.lang.String objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        return BaseLocallySavableCMObject.delete(context, java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken sessionToken) {
        return BaseLocallySavableCMObject.delete(context, objectIds, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.lang.String objectId, com.cloudmine.api.CMSessionToken sessionToken) {
        return BaseLocallySavableCMObject.delete(context, java.util.Collections.singleton(objectId), sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.util.Collection<java.lang.String> objectIds) {
        return BaseLocallySavableCMObject.delete(context, objectIds, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest delete(android.content.Context context, java.lang.String objectId) {
        return BaseLocallySavableCMObject.delete(context, java.util.Collections.singleton(objectId), (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

}