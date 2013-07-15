package com.cloudmine.api.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.cloudmine.api.CMObject;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.ObjectLoadRequest;
import com.cloudmine.api.rest.ObjectLoadRequestBuilder;
import com.cloudmine.api.rest.ObjectModificationRequest;
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

    public static RequestQueue loadObjects(Context context, Response.Listener<CMObjectResponse> listener, Response.ErrorListener errorListener) {
        return loadObjects(context, (Collection<String>) null, listener, errorListener);
    }

    public static RequestQueue loadObjects(Context context, String objectId, Response.Listener<CMObjectResponse> listener, Response.ErrorListener errorListener) {
        return loadObjects(context, Collections.singleton(objectId), listener, errorListener);
    }

    public static RequestQueue loadObjects(Context context, Collection <String> objectIds, Response.Listener<CMObjectResponse> listener, Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        queue.add(new ObjectLoadRequest(objectIds, listener, errorListener));
        return queue;
    }

    public static RequestQueue searchObjects(Context context, String searchString, Response.Listener<CMObjectResponse> listener, Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        queue.add(new ObjectLoadRequestBuilder(listener, errorListener).search(searchString).build());
        return queue;
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

    public RequestQueue save(Context context, Response.Listener<ObjectModificationResponse> successListener) {
        return save(context, successListener, null);
    }

    public RequestQueue save(Context context, Response.Listener< ObjectModificationResponse > successListener, Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        queue.add(new ObjectModificationRequest(this, successListener, errorListener));
        return queue;
    }

    public RequestQueue save(Context context, CMSessionToken sessionToken, Response.Listener<ObjectModificationResponse> successListener) {
        return save(context, sessionToken, successListener, null);
    }

    public RequestQueue save(Context context, CMSessionToken sessionToken, Response.Listener< ObjectModificationResponse > successListener, Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        queue.add(new ObjectModificationRequest(this, sessionToken,  successListener, errorListener));
        return queue;
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
