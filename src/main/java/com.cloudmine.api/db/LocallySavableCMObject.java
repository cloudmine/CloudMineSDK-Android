package com.cloudmine.api.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cloudmine.api.CMObject;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.CMUser;
import com.cloudmine.api.rest.BaseObjectLoadRequest;
import com.cloudmine.api.rest.BaseObjectModificationRequest;
import com.cloudmine.api.rest.CloudMineRequest;
import com.cloudmine.api.rest.ObjectLoadRequestBuilder;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.ObjectModificationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.cloudmine.api.rest.SharedRequestQueueHolders.getRequestQueue;


/**
 * A {@link CMObject} that can be stored locally. Note that local storage happens on the calling thread.
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class LocallySavableCMObject extends CMObject {

    private static final Logger LOG = LoggerFactory.getLogger(LocallySavableCMObject.class);
    static CMObjectDBOpenHelper cmObjectDBOpenHelper;
    static RequestDBOpenHelper requestDBOpenHelper;


    static synchronized CMObjectDBOpenHelper getCMObjectDBHelper(Context context) {
        if(cmObjectDBOpenHelper == null) {
            cmObjectDBOpenHelper = new CMObjectDBOpenHelper(context.getApplicationContext());
        }
        return cmObjectDBOpenHelper;
    }

    static synchronized RequestDBOpenHelper getRequestDBOpenHelper(Context context) {
        if(requestDBOpenHelper == null) {
            requestDBOpenHelper = new RequestDBOpenHelper(context.getApplicationContext());
        }
        return requestDBOpenHelper;
    }

    public static CloudMineRequest saveObjects(Context context, Collection<CMObject> objects, Response.Listener<ObjectModificationResponse> listener, Response.ErrorListener errorListener) {
        return saveObjects(context, objects, null, listener, errorListener);
    }

    public static CloudMineRequest saveObjects(Context context, Collection <CMObject> objects, CMSessionToken token, Response.Listener<ObjectModificationResponse> listener, Response.ErrorListener errorListener) {

        CloudMineRequest request = new BaseObjectModificationRequest(CMObject.massTransportable(objects), token, listener, errorListener);
        getRequestQueue(context).add(request);
        return request;
    }

    public static CloudMineRequest loadAllObjects(Context context, Response.Listener<CMObjectResponse> listener, Response.ErrorListener errorListener) {
        return loadAllObjects(context, null, listener, errorListener);
    }

    public static CloudMineRequest loadAllObjects(Context context, CMSessionToken token, Response.Listener<CMObjectResponse> listener, Response.ErrorListener errorListener) {
        return loadObjects(context, (Collection<String>) null, token, listener, errorListener);
    }

    public static CloudMineRequest loadObject(Context context, String objectId, Response.Listener<CMObjectResponse> listener, Response.ErrorListener errorListener) {
        return loadObject(context, objectId, null, listener, errorListener);
    }

    public static CloudMineRequest loadObject(Context context, String objectId, CMSessionToken token, Response.Listener<CMObjectResponse> listener, Response.ErrorListener errorListener) {
        return loadObjects(context, Collections.singleton(objectId), token, listener, errorListener);
    }

    public static CloudMineRequest loadObject(Context context, String objectId, CMSessionToken token, Handler handler) {
        return loadObjects(context, Collections.singleton(objectId), token, handler);
    }

    public static CloudMineRequest loadObjects(Context context, Collection <String> objectIds, Response.Listener<CMObjectResponse> listener, Response.ErrorListener errorListener) {
        return loadObjects(context, objectIds, null, listener, errorListener);
    }

    public static CloudMineRequest loadObjects(Context context, Collection <String> objectIds, CMSessionToken token, Response.Listener<CMObjectResponse> listener, Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        BaseObjectLoadRequest request = new BaseObjectLoadRequest(objectIds, token, listener, errorListener);
        queue.add(request);
        return request;
    }

    public static CloudMineRequest loadObjects(Context context, Collection <String> objectIds, Handler handler) {
        return loadObjects(context,  objectIds, null, handler);
    }

    public static CloudMineRequest loadObjects(Context context, Collection <String> objectIds, CMSessionToken token, Handler handler) {
        BaseObjectLoadRequest request = new BaseObjectLoadRequest(objectIds, token, null, null);
        request.setHandler(handler);
        getRequestQueue(context).add(request);
        return request;
    }

    public static CloudMineRequest searchObjects(Context context, String searchString, Response.Listener<CMObjectResponse> listener, Response.ErrorListener errorListener) {
        return searchObjects(context, searchString, null, listener, errorListener);
    }

    public static CloudMineRequest searchObjects(Context context, String searchString, CMSessionToken token, Response.Listener<CMObjectResponse> listener, Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        BaseObjectLoadRequest request = new ObjectLoadRequestBuilder(token, listener, errorListener).search(searchString).build();
        queue.add(request);
        return request;
    }

    public static CloudMineRequest searchObjects(Context context, String searchString, Handler handler) {
        return searchObjects(context, searchString, null, handler);
    }

    public static CloudMineRequest searchObjects(Context context, String searchString, CMSessionToken token, Handler handler) {
        BaseObjectLoadRequest request = new ObjectLoadRequestBuilder(token, null, null).search(searchString).build();
        request.setHandler(handler);
        getRequestQueue(context).add(request);
        return request;
    }

    /**
     * Load the locally stored copy of the object with the given id. Will throw a {@link ClassCastException} if an object
     * exists with the given ID but is of a different type.
     * @param context activity context
     * @param objectId object id of the object to load
     * @param <OBJECT_TYPE> the type of the object to load. May be a superclass of the actual type
     * @return the object if it was found, or null
     */
    public static <OBJECT_TYPE extends LocallySavableCMObject> OBJECT_TYPE loadLocalObject(Context context, String objectId) {
        return getCMObjectDBHelper(context).loadObjectById(objectId);
    }

    /**
     * Delete the specified object, if it exists
     * @param context activity context
     * @param objectId object id of object to delete
     * @return negative number if operation failed, 0 if succeeded but nothing was deleted, 1 if the object was deleted
     */
    public static int deleteLocalObject(Context context, String objectId) {
        return getCMObjectDBHelper(context).deleteObjectById(objectId);
    }

    /**
     * Load all of the objects of the specified class that are stored locally
     * @param context activity context
     * @param klass
     * @param <OBJECT_TYPE>
     * @return
     */
    public static <OBJECT_TYPE extends LocallySavableCMObject> List<OBJECT_TYPE> loadLocalObjectsByClass(Context context, Class<OBJECT_TYPE> klass) {
        return getCMObjectDBHelper(context).loadObjectsByClass(klass);
    }

    public static List<LocallySavableCMObject> loadLocalObjects(Context context) {
        return getCMObjectDBHelper(context).loadAllObjects();
    }

    private Date lastSaveDate;

    /**
     * Save this object to local storage. Runs on the calling thread
     * @param context
     * @return true if the object was saved, false otherwise
     */
    public boolean saveLocally(Context context) {
        lastSaveDate = new Date();
        return getCMObjectDBHelper(context).insertCMObjectIfNewer(this);
    }

    /**
     * Save this object to local storage, then eventually save it to the server. When the object is sent to the server,
     * the most recent version from the database is used - so calls to saveEventually or saveLocally that occur before
     * the request is sent will be sent to the server
     * @param context activity context
     * @return true if the request was inserted correctly and will eventually be saved
     */
    public boolean saveEventually(Context context) {
        boolean wasCreated = saveLocally(context);
        LOG.debug("Was saved locally? " + wasCreated);

        if(wasCreated) {
            Request request = Request.createApplicationObjectRequest(getObjectId());
            try {
                getRequestDBOpenHelper(context).insertRequest(request);
                wasCreated = true;
                LOG.debug("Request was inserted");
            } catch (Exception e) {
                wasCreated = false;
                LOG.error("Failed", e);
            }
            if(wasCreated) {
                context.startService(new Intent(context, RequestPerformerService.class));
            }
        }
        return wasCreated;
    }

    public CloudMineRequest save(Context context, Response.Listener<ObjectModificationResponse> successListener) {
        return save(context, successListener, null);
    }

    public CloudMineRequest save(Context context, Response.Listener< ObjectModificationResponse > successListener, Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        BaseObjectModificationRequest request;
        if(isUserLevel()) {
            CMUser user = getUser();
            if(user != null && user.getSessionToken() != null) {
                request = new BaseObjectModificationRequest(this, user.getSessionToken(), successListener, errorListener);
            } else {
                if(errorListener != null) errorListener.onErrorResponse(new VolleyError("Can't save user level object when the associated user is not logged in"));
                return CloudMineRequest.FAKE_REQUEST;
            }
        } else {
            request = new BaseObjectModificationRequest(this, null, successListener, errorListener);
        }

        queue.add(request);
        return request;
    }

    public CloudMineRequest save(Context context, CMSessionToken sessionToken, Response.Listener<ObjectModificationResponse> successListener) {
        return save(context, sessionToken, successListener, null);
    }

    public CloudMineRequest save(Context context, CMSessionToken sessionToken, Response.Listener< ObjectModificationResponse > successListener, Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        BaseObjectModificationRequest request = new BaseObjectModificationRequest(this, sessionToken, successListener, errorListener);
        queue.add(request);
        return request;
    }

    public CloudMineRequest save(Context context, Handler handler) {
        RequestQueue queue = getRequestQueue(context);
        BaseObjectModificationRequest request = new BaseObjectModificationRequest(this, null, null, null);
        request.setHandler(handler);
        queue.add(request);
        return request;
    }

    /**
     * Get the last time this object was stored locally. Returns 0 if the object has never been stored locally
     * @return
     */
    public int getLastSavedDateAsSeconds() {
        if(lastSaveDate == null) return 0;
        return (int) (lastSaveDate.getTime() / 1000);
    }

    public Date getLastSaveDate() {
        return lastSaveDate;
    }

    void setLastSaveDate(Date lastSaveDate) {
        this.lastSaveDate = lastSaveDate;
    }

    /**
     * Used by the CMObjectDBOpenHelper to insert this object into the database
     * @return
     */
    ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(CMObjectDBOpenHelper.OBJECT_ID_COLUMN, getObjectId());
        values.put(CMObjectDBOpenHelper.JSON_COLUMN, transportableRepresentation());
        values.put(CMObjectDBOpenHelper.SAVED_DATE_COLUMN, getLastSavedDateAsSeconds());
        values.put(CMObjectDBOpenHelper.CLASS_NAME_COLUMN, getClassName());
        return values;
    }
}
