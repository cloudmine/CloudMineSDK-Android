package com.cloudmine.api.db;

public class LocallySavableCMObject extends com.cloudmine.api.db.BaseLocallySavableCMObject {

    public static com.cloudmine.api.rest.CloudMineRequest searchObjects(android.content.Context context, java.lang.String searchString, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.searchObjects(context, searchString, token, serverFunction, listener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest searchObjects(android.content.Context context, java.lang.String searchString, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.searchObjects(context, searchString, (com.cloudmine.api.CMSessionToken)null, serverFunction, listener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest searchObjects(android.content.Context context, java.lang.String searchString, com.cloudmine.api.CMSessionToken token, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.searchObjects(context, searchString, token, (com.cloudmine.api.rest.options.CMServerFunction)null, listener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest searchObjects(android.content.Context context, java.lang.String searchString, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.searchObjects(context, searchString, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, listener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest searchObjects(android.content.Context context, java.lang.String searchString, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.searchObjects(context, searchString, token, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest searchObjects(android.content.Context context, java.lang.String searchString, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.searchObjects(context, searchString, (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest searchObjects(android.content.Context context, java.lang.String searchString, com.cloudmine.api.CMSessionToken token, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.searchObjects(context, searchString, token, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest searchObjects(android.content.Context context, java.lang.String searchString, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.searchObjects(context, searchString, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest searchObjects(android.content.Context context, java.lang.String searchString, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener) {
        return BaseLocallySavableCMObject.searchObjects(context, searchString, token, serverFunction, listener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest searchObjects(android.content.Context context, java.lang.String searchString, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener) {
        return BaseLocallySavableCMObject.searchObjects(context, searchString, (com.cloudmine.api.CMSessionToken)null, serverFunction, listener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest searchObjects(android.content.Context context, java.lang.String searchString, com.cloudmine.api.CMSessionToken token, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener) {
        return BaseLocallySavableCMObject.searchObjects(context, searchString, token, (com.cloudmine.api.rest.options.CMServerFunction)null, listener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest searchObjects(android.content.Context context, java.lang.String searchString, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener) {
        return BaseLocallySavableCMObject.searchObjects(context, searchString, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, listener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest searchObjects(android.content.Context context, java.lang.String searchString, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        return BaseLocallySavableCMObject.searchObjects(context, searchString, token, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest searchObjects(android.content.Context context, java.lang.String searchString, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        return BaseLocallySavableCMObject.searchObjects(context, searchString, (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest searchObjects(android.content.Context context, java.lang.String searchString, com.cloudmine.api.CMSessionToken token) {
        return BaseLocallySavableCMObject.searchObjects(context, searchString, token, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest searchObjects(android.content.Context context, java.lang.String searchString) {
        return BaseLocallySavableCMObject.searchObjects(context, searchString, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObjects(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        return BaseLocallySavableCMObject.loadObjects(context, objectIds, token, serverFunction, handler);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObjects(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        return BaseLocallySavableCMObject.loadObjects(context, objectIds, (com.cloudmine.api.CMSessionToken)null, serverFunction, handler);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObjects(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken token, android.os.Handler handler) {
        return BaseLocallySavableCMObject.loadObjects(context, objectIds, token, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObjects(android.content.Context context, java.util.Collection<java.lang.String> objectIds, android.os.Handler handler) {
        return BaseLocallySavableCMObject.loadObjects(context, objectIds, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObject(android.content.Context context, java.lang.String objectId, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        return BaseLocallySavableCMObject.loadObject(context, objectId, token, serverFunction, handler);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObject(android.content.Context context, java.lang.String objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        return BaseLocallySavableCMObject.loadObject(context, objectId, (com.cloudmine.api.CMSessionToken)null, serverFunction, handler);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObject(android.content.Context context, java.lang.String objectId, com.cloudmine.api.CMSessionToken token, android.os.Handler handler) {
        return BaseLocallySavableCMObject.loadObject(context, objectId, token, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObject(android.content.Context context, java.lang.String objectId, android.os.Handler handler) {
        return BaseLocallySavableCMObject.loadObject(context, objectId, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadAllObjects(android.content.Context context, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        return BaseLocallySavableCMObject.loadAllObjects(context, token, serverFunction, handler);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadAllObjects(android.content.Context context, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        return BaseLocallySavableCMObject.loadAllObjects(context, (com.cloudmine.api.CMSessionToken)null, serverFunction, handler);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadAllObjects(android.content.Context context, com.cloudmine.api.CMSessionToken token, android.os.Handler handler) {
        return BaseLocallySavableCMObject.loadAllObjects(context, token, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadAllObjects(android.content.Context context, android.os.Handler handler) {
        return BaseLocallySavableCMObject.loadAllObjects(context, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public static com.cloudmine.api.rest.CloudMineRequest saveObjects(android.content.Context context, java.util.Collection<com.cloudmine.api.CMObject> objects, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        return BaseLocallySavableCMObject.saveObjects(context, objects, token, serverFunction, handler);
    }

    public static com.cloudmine.api.rest.CloudMineRequest saveObjects(android.content.Context context, java.util.Collection<com.cloudmine.api.CMObject> objects, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        return BaseLocallySavableCMObject.saveObjects(context, objects, (com.cloudmine.api.CMSessionToken)null, serverFunction, handler);
    }

    public static com.cloudmine.api.rest.CloudMineRequest saveObjects(android.content.Context context, java.util.Collection<com.cloudmine.api.CMObject> objects, com.cloudmine.api.CMSessionToken token, android.os.Handler handler) {
        return BaseLocallySavableCMObject.saveObjects(context, objects, token, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public static com.cloudmine.api.rest.CloudMineRequest saveObjects(android.content.Context context, java.util.Collection<com.cloudmine.api.CMObject> objects, android.os.Handler handler) {
        return BaseLocallySavableCMObject.saveObjects(context, objects, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadAllObjects(android.content.Context context, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.loadAllObjects(context, token, serverFunction, listener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadAllObjects(android.content.Context context, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.loadAllObjects(context, (com.cloudmine.api.CMSessionToken)null, serverFunction, listener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadAllObjects(android.content.Context context, com.cloudmine.api.CMSessionToken token, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.loadAllObjects(context, token, (com.cloudmine.api.rest.options.CMServerFunction)null, listener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadAllObjects(android.content.Context context, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.loadAllObjects(context, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, listener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadAllObjects(android.content.Context context, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.loadAllObjects(context, token, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadAllObjects(android.content.Context context, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.loadAllObjects(context, (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadAllObjects(android.content.Context context, com.cloudmine.api.CMSessionToken token, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.loadAllObjects(context, token, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadAllObjects(android.content.Context context, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.loadAllObjects(context, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadAllObjects(android.content.Context context, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener) {
        return BaseLocallySavableCMObject.loadAllObjects(context, token, serverFunction, listener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadAllObjects(android.content.Context context, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener) {
        return BaseLocallySavableCMObject.loadAllObjects(context, (com.cloudmine.api.CMSessionToken)null, serverFunction, listener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadAllObjects(android.content.Context context, com.cloudmine.api.CMSessionToken token, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener) {
        return BaseLocallySavableCMObject.loadAllObjects(context, token, (com.cloudmine.api.rest.options.CMServerFunction)null, listener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadAllObjects(android.content.Context context, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener) {
        return BaseLocallySavableCMObject.loadAllObjects(context, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, listener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadAllObjects(android.content.Context context, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        return BaseLocallySavableCMObject.loadAllObjects(context, token, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadAllObjects(android.content.Context context, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        return BaseLocallySavableCMObject.loadAllObjects(context, (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadAllObjects(android.content.Context context, com.cloudmine.api.CMSessionToken token) {
        return BaseLocallySavableCMObject.loadAllObjects(context, token, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadAllObjects(android.content.Context context) {
        return BaseLocallySavableCMObject.loadAllObjects(context, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest saveObjects(android.content.Context context, java.util.Collection<com.cloudmine.api.CMObject> objects, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> listener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.saveObjects(context, objects, token, serverFunction, listener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest saveObjects(android.content.Context context, java.util.Collection<com.cloudmine.api.CMObject> objects, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> listener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.saveObjects(context, objects, (com.cloudmine.api.CMSessionToken)null, serverFunction, listener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest saveObjects(android.content.Context context, java.util.Collection<com.cloudmine.api.CMObject> objects, com.cloudmine.api.CMSessionToken token, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> listener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.saveObjects(context, objects, token, (com.cloudmine.api.rest.options.CMServerFunction)null, listener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest saveObjects(android.content.Context context, java.util.Collection<com.cloudmine.api.CMObject> objects, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> listener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.saveObjects(context, objects, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, listener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest saveObjects(android.content.Context context, java.util.Collection<com.cloudmine.api.CMObject> objects, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.saveObjects(context, objects, token, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest saveObjects(android.content.Context context, java.util.Collection<com.cloudmine.api.CMObject> objects, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.saveObjects(context, objects, (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest saveObjects(android.content.Context context, java.util.Collection<com.cloudmine.api.CMObject> objects, com.cloudmine.api.CMSessionToken token, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.saveObjects(context, objects, token, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest saveObjects(android.content.Context context, java.util.Collection<com.cloudmine.api.CMObject> objects, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.saveObjects(context, objects, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest saveObjects(android.content.Context context, java.util.Collection<com.cloudmine.api.CMObject> objects, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> listener) {
        return BaseLocallySavableCMObject.saveObjects(context, objects, token, serverFunction, listener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest saveObjects(android.content.Context context, java.util.Collection<com.cloudmine.api.CMObject> objects, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> listener) {
        return BaseLocallySavableCMObject.saveObjects(context, objects, (com.cloudmine.api.CMSessionToken)null, serverFunction, listener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest saveObjects(android.content.Context context, java.util.Collection<com.cloudmine.api.CMObject> objects, com.cloudmine.api.CMSessionToken token, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> listener) {
        return BaseLocallySavableCMObject.saveObjects(context, objects, token, (com.cloudmine.api.rest.options.CMServerFunction)null, listener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest saveObjects(android.content.Context context, java.util.Collection<com.cloudmine.api.CMObject> objects, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse> listener) {
        return BaseLocallySavableCMObject.saveObjects(context, objects, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, listener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest saveObjects(android.content.Context context, java.util.Collection<com.cloudmine.api.CMObject> objects, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        return BaseLocallySavableCMObject.saveObjects(context, objects, token, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest saveObjects(android.content.Context context, java.util.Collection<com.cloudmine.api.CMObject> objects, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        return BaseLocallySavableCMObject.saveObjects(context, objects, (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest saveObjects(android.content.Context context, java.util.Collection<com.cloudmine.api.CMObject> objects, com.cloudmine.api.CMSessionToken token) {
        return BaseLocallySavableCMObject.saveObjects(context, objects, token, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest saveObjects(android.content.Context context, java.util.Collection<com.cloudmine.api.CMObject> objects) {
        return BaseLocallySavableCMObject.saveObjects(context, objects, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.ObjectModificationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest searchObjects(android.content.Context context, java.lang.String searchString, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        return BaseLocallySavableCMObject.searchObjects(context, searchString, token, serverFunction, handler);
    }

    public static com.cloudmine.api.rest.CloudMineRequest searchObjects(android.content.Context context, java.lang.String searchString, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.os.Handler handler) {
        return BaseLocallySavableCMObject.searchObjects(context, searchString, (com.cloudmine.api.CMSessionToken)null, serverFunction, handler);
    }

    public static com.cloudmine.api.rest.CloudMineRequest searchObjects(android.content.Context context, java.lang.String searchString, com.cloudmine.api.CMSessionToken token, android.os.Handler handler) {
        return BaseLocallySavableCMObject.searchObjects(context, searchString, token, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
    }

    public static com.cloudmine.api.rest.CloudMineRequest searchObjects(android.content.Context context, java.lang.String searchString, android.os.Handler handler) {
        return BaseLocallySavableCMObject.searchObjects(context, searchString, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, handler);
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

    public static com.cloudmine.api.rest.CloudMineRequest loadObject(android.content.Context context, java.lang.String objectId, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.loadObject(context, objectId, token, serverFunction, listener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObject(android.content.Context context, java.lang.String objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.loadObject(context, objectId, (com.cloudmine.api.CMSessionToken)null, serverFunction, listener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObject(android.content.Context context, java.lang.String objectId, com.cloudmine.api.CMSessionToken token, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.loadObject(context, objectId, token, (com.cloudmine.api.rest.options.CMServerFunction)null, listener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObject(android.content.Context context, java.lang.String objectId, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.loadObject(context, objectId, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, listener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObject(android.content.Context context, java.lang.String objectId, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.loadObject(context, objectId, token, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObject(android.content.Context context, java.lang.String objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.loadObject(context, objectId, (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObject(android.content.Context context, java.lang.String objectId, com.cloudmine.api.CMSessionToken token, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.loadObject(context, objectId, token, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObject(android.content.Context context, java.lang.String objectId, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.loadObject(context, objectId, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObject(android.content.Context context, java.lang.String objectId, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener) {
        return BaseLocallySavableCMObject.loadObject(context, objectId, token, serverFunction, listener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObject(android.content.Context context, java.lang.String objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener) {
        return BaseLocallySavableCMObject.loadObject(context, objectId, (com.cloudmine.api.CMSessionToken)null, serverFunction, listener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObject(android.content.Context context, java.lang.String objectId, com.cloudmine.api.CMSessionToken token, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener) {
        return BaseLocallySavableCMObject.loadObject(context, objectId, token, (com.cloudmine.api.rest.options.CMServerFunction)null, listener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObject(android.content.Context context, java.lang.String objectId, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener) {
        return BaseLocallySavableCMObject.loadObject(context, objectId, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, listener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObject(android.content.Context context, java.lang.String objectId, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        return BaseLocallySavableCMObject.loadObject(context, objectId, token, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObject(android.content.Context context, java.lang.String objectId, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        return BaseLocallySavableCMObject.loadObject(context, objectId, (com.cloudmine.api.CMSessionToken)null, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObject(android.content.Context context, java.lang.String objectId, com.cloudmine.api.CMSessionToken token) {
        return BaseLocallySavableCMObject.loadObject(context, objectId, token, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObject(android.content.Context context, java.lang.String objectId) {
        return BaseLocallySavableCMObject.loadObject(context, objectId, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObjects(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.loadObjects(context, objectIds, token, serverFunction, listener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObjects(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.loadObjects(context, objectIds, (com.cloudmine.api.CMSessionToken)null, serverFunction, listener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObjects(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken token, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.loadObjects(context, objectIds, token, (com.cloudmine.api.rest.options.CMServerFunction)null, listener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObjects(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener, com.android.volley.Response.ErrorListener errorListener) {
        return BaseLocallySavableCMObject.loadObjects(context, objectIds, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, listener, errorListener);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObjects(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken token, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener) {
        return BaseLocallySavableCMObject.loadObjects(context, objectIds, token, serverFunction, listener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObjects(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener) {
        return BaseLocallySavableCMObject.loadObjects(context, objectIds, (com.cloudmine.api.CMSessionToken)null, serverFunction, listener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObjects(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.cloudmine.api.CMSessionToken token, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener) {
        return BaseLocallySavableCMObject.loadObjects(context, objectIds, token, (com.cloudmine.api.rest.options.CMServerFunction)null, listener, (com.android.volley.Response.ErrorListener)null);
    }

    public static com.cloudmine.api.rest.CloudMineRequest loadObjects(android.content.Context context, java.util.Collection<java.lang.String> objectIds, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> listener) {
        return BaseLocallySavableCMObject.loadObjects(context, objectIds, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, listener, (com.android.volley.Response.ErrorListener)null);
    }

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

}