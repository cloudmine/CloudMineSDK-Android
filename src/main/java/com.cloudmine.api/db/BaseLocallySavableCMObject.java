package com.cloudmine.api.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMObject;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.JavaCMUser;
import com.cloudmine.api.LocallySavable;
import com.cloudmine.api.rest.BaseObjectDeleteRequest;
import com.cloudmine.api.rest.BaseObjectLoadRequest;
import com.cloudmine.api.rest.BaseObjectModificationRequest;
import com.cloudmine.api.rest.CloudMineRequest;
import com.cloudmine.api.rest.ObjectLoadRequestBuilder;
import com.cloudmine.api.rest.SharedRequestQueueHolders;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.ObjectModificationResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;
import me.cloudmine.annotations.Single;
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
public class BaseLocallySavableCMObject extends CMObject implements LocallySavable {

    private static final Logger LOG = LoggerFactory.getLogger(BaseLocallySavableCMObject.class);

    @Expand(isStatic = true)
    public static CloudMineRequest saveObjects(Context context, Collection <? extends CMObject> objects, @Optional CMSessionToken token, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<ObjectModificationResponse> listener, @Optional Response.ErrorListener errorListener) {
        CloudMineRequest request = new BaseObjectModificationRequest(CMObject.massTransportable(objects), token, apiCredentials, serverFunction, listener, errorListener);

        getRequestQueue(context).add(request);
        return request;
    }

    @Expand(isStatic = true)
    public static CloudMineRequest saveObjects(Context context, Collection <? extends CMObject> objects, @Optional CMSessionToken token, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, Handler handler) {
        CloudMineRequest request = new BaseObjectModificationRequest(CMObject.massTransportable(objects), token, apiCredentials, serverFunction, null, null);
        request.setHandler(handler);
        getRequestQueue(context).add(request);
        return request;
    }
    @Expand(isStatic = true)
    public static CloudMineRequest loadAllObjects(Context context, @Optional CMSessionToken token, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, Handler handler) {
        return loadObjects(context, (Collection<String>) null, token, apiCredentials, serverFunction, handler);
    }

    @Expand(isStatic = true)
    public static CloudMineRequest loadAllObjects(Context context, @Optional CMSessionToken token, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMObjectResponse> listener, @Optional Response.ErrorListener errorListener) {
        return loadObjects(context, (Collection<String>) null, token, apiCredentials, serverFunction, listener, errorListener);
    }


    @Expand(isStatic = true)
    public static CloudMineRequest loadObject(Context context, String objectId, @Optional CMSessionToken token, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMObjectResponse> listener, @Optional Response.ErrorListener errorListener) {
        return loadObjects(context, Collections.singleton(objectId), token, apiCredentials, serverFunction, listener, errorListener);
    }

    @Expand(isStatic = true)
    public static CloudMineRequest loadObjects(Context context, Collection <String> objectIds, @Optional CMSessionToken token, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, Response.Listener<CMObjectResponse> listener, @Optional Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        BaseObjectLoadRequest request = new BaseObjectLoadRequest(objectIds, token, apiCredentials, serverFunction, listener, errorListener);
        queue.add(request);
        return request;
    }

    @Expand(isStatic = true)
    public static CloudMineRequest loadObjects(Context context, Collection <String> objectIds, @Optional CMSessionToken token, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, Handler handler) {
        RequestQueue queue = getRequestQueue(context);
        BaseObjectLoadRequest request = new BaseObjectLoadRequest(objectIds, token, apiCredentials, serverFunction, null, null);
        request.setHandler(handler);
        queue.add(request);
        return request;
    }

    @Expand(isStatic = true)
    public static CloudMineRequest searchObjects(Context context, String searchString, @Optional CMSessionToken token, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMObjectResponse> listener, @Optional Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        BaseObjectLoadRequest request = new ObjectLoadRequestBuilder(token, listener, errorListener).search(searchString).runFunction(serverFunction).useCredentials(apiCredentials).build();
        queue.add(request);
        return request;
    }

    @Expand(isStatic = true)
    public static CloudMineRequest delete(Context context, @Single Collection<String> objectIds, @Optional CMSessionToken sessionToken, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<ObjectModificationResponse> successListener, @Optional Response.ErrorListener errorListener) {
        BaseObjectDeleteRequest deleteRequest = new BaseObjectDeleteRequest(objectIds, sessionToken, apiCredentials, serverFunction, successListener, errorListener);
        getRequestQueue(context).add(deleteRequest);
        return deleteRequest;
    }

    /**
     * Load the locally stored copy of the object with the given id. Will throw a {@link ClassCastException} if an object
     * exists with the given ID but is of a different type.
     * @param context activity context
     * @param objectId object id of the object to load
     * @param <OBJECT_TYPE> the type of the object to load. May be a superclass of the actual type
     * @return the object if it was found, or null
     */
    public static <OBJECT_TYPE extends BaseLocallySavableCMObject> OBJECT_TYPE loadLocalObject(Context context, String objectId) {
        return CMObjectDBOpenHelper.getCMObjectDBHelper(context).loadObjectById(objectId);
    }

    /**
     * Delete the specified object, if it exists
     * @param context activity context
     * @param objectId object id of object to delete
     * @return negative number if operation failed, 0 if succeeded but nothing was deleted, 1 if the object was deleted
     */
    public static int deleteLocalObject(Context context, String objectId) {
        return CMObjectDBOpenHelper.getCMObjectDBHelper(context).deleteObjectById(objectId);
    }

    /**
     * Load all of the objects of the specified class that are stored locally
     * @param context activity context
     * @param klass
     * @param <OBJECT_TYPE>
     * @return
     */
    public static <OBJECT_TYPE extends BaseLocallySavableCMObject> List<OBJECT_TYPE> loadLocalObjectsByClass(Context context, Class<OBJECT_TYPE> klass) {
        return CMObjectDBOpenHelper.getCMObjectDBHelper(context).loadObjectsByClass(klass);
    }

    public static List<BaseLocallySavableCMObject> loadLocalObjects(Context context) {
        return CMObjectDBOpenHelper.getCMObjectDBHelper(context).loadAllObjects();
    }

    private Date lastLocalSaveDate;

    /**
     * Save this object to local storage. Runs on the calling thread
     * @param context
     * @return true if the object was saved, false otherwise
     */
    public boolean saveLocally(Context context) {
        CMObjectDBOpenHelper cmObjectDBHelper = CMObjectDBOpenHelper.getCMObjectDBHelper(context);
        boolean wasSaved = cmObjectDBHelper.insertCMObjectIfNewer(this);
        if(wasSaved) lastLocalSaveDate = new Date();
        return wasSaved;
    }

    /**
     * Save this object to local storage, then eventually save it to the server. When the object is sent to the server,
     * the most recent version from the database is used - so calls to saveEventually or saveLocally that occur before
     * the request is sent will be sent to the server
     * @param context activity context
     * @return true if the request was inserted correctly and will eventually be saved
     */
    public boolean saveEventually(Context context) {
        return saveEventually(context,  null);
    }

    public boolean saveEventually(Context context, CMSessionToken sessionToken) {
        boolean wasCreated = saveLocally(context);
        LOG.debug("Was saved locally? " + wasCreated);

        if(wasCreated) {
            RequestDBObject request;
            if(sessionToken != null) {
                request = RequestDBObject.createUserObjectRequest(getObjectId(), sessionToken);
            } else
                request = RequestDBObject.createApplicationObjectRequest(getObjectId());
            try {
                RequestDBOpenHelper.getRequestDBOpenHelper(context).insertRequest(request);
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

    public int deleteLocally(Context context) {
        return BaseLocallySavableCMObject.deleteLocalObject(context, getObjectId());
    }

    @Expand
    public CloudMineRequest save(Context context, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener< ObjectModificationResponse > successListener, @Optional Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        BaseObjectModificationRequest request;
        if(isUserLevel()) {
            JavaCMUser user = getUser();
            if(user != null && user.getSessionToken() != null) {
                request = new BaseObjectModificationRequest(this, user.getSessionToken(), apiCredentials, serverFunction, successListener, errorListener);
            } else {
                if(errorListener != null) errorListener.onErrorResponse(new VolleyError("Can't save user level object when the associated user is not logged in"));
                return CloudMineRequest.FAKE_REQUEST;
            }
        } else {
            request = new BaseObjectModificationRequest(this, null, apiCredentials, serverFunction, successListener, errorListener);
        }

        queue.add(request);
        return request;
    }

    @Expand
    public CloudMineRequest save(Context context, CMSessionToken sessionToken, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener< ObjectModificationResponse > successListener, @Optional Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        BaseObjectModificationRequest request = new BaseObjectModificationRequest(this, sessionToken, apiCredentials, serverFunction, successListener, errorListener);
        queue.add(request);
        return request;
    }

    public void grantAccess(BaseLocallySavableCMAccessList list) {
        if(list == null)
            return;
        addAccessListId(list.getObjectId());
    }

    /**
     * Get the last time this object was stored locally. Returns 0 if the object has never been stored locally
     * @return
     */
    public int getLastLocalSavedDateAsSeconds() {
        if(lastLocalSaveDate == null) return 0;
        return (int) (lastLocalSaveDate.getTime() / 1000);
    }

    public Date getLastLocalSaveDate() {
        return lastLocalSaveDate;
    }

    protected void setLastLocalSaveDate(Date lastSaveDate) {
        this.lastLocalSaveDate = lastSaveDate;
    }

    @Expand
    public CloudMineRequest delete(Context context, @Optional CMSessionToken sessionToken, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<ObjectModificationResponse> successListener, @Optional Response.ErrorListener errorListener) {
        CloudMineRequest request = new BaseObjectDeleteRequest(Collections.singleton(getObjectId()), sessionToken, apiCredentials, serverFunction, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        return request;
    }

    /**
     * Used by the CMObjectDBOpenHelper to insert this object into the database
     * @return
     */
    ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(CMObjectDBOpenHelper.OBJECT_ID_COLUMN, getObjectId());
        values.put(CMObjectDBOpenHelper.JSON_COLUMN, transportableRepresentation());
        values.put(CMObjectDBOpenHelper.SAVED_DATE_COLUMN, getLastLocalSavedDateAsSeconds());
        values.put(CMObjectDBOpenHelper.CLASS_NAME_COLUMN, getClassName());
        return values;
    }
}
