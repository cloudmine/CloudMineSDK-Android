package com.cloudmine.api.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import com.cloudmine.api.CMObject;
import com.cloudmine.api.rest.RequestPerformerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: johnmccarthy
 * Date: 6/5/13
 * Time: 5:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocallySavableCMObject extends CMObject {
    private static final Logger LOG = LoggerFactory.getLogger(LocallySavableCMObject.class);
    private static CMObjectDBOpenHelper cmObjectDBOpenHelper;
    private static RequestDBOpenHelper requestDBOpenHelper;
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

    public static <OBJECT_TYPE extends LocallySavableCMObject> OBJECT_TYPE loadObject(Context context, String objectId) {
        return getCMObjectDBHelper(context).loadObjectById(objectId);
    }

    public static void deleteObject(Context context, String objectId) {
        getCMObjectDBHelper(context).deleteObjectById(objectId);
    }

    private Date lastSaveDate;

    public boolean saveLocally(Context context) {
        lastSaveDate = new Date();
        return getCMObjectDBHelper(context).insertCMObjectIfNewer(this);
    }

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

    public int getLastSavedDateAsSeconds() {
        if(lastSaveDate == null) return 0;
        return (int) (lastSaveDate.getTime() / 1000);
    }

    public Date getLastSaveDate() {
        return lastSaveDate;
    }

    public void setLastSaveDate(Date lastSaveDate) {
        this.lastSaveDate = lastSaveDate;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(CMObjectDBOpenHelper.OBJECT_ID_COLUMN, getObjectId());
        values.put(CMObjectDBOpenHelper.JSON_COLUMN, transportableRepresentation());
        values.put(CMObjectDBOpenHelper.SAVED_DATE_COLUMN, getLastSavedDateAsSeconds());
        values.put(CMObjectDBOpenHelper.CLASS_NAME_COLUMN, getClassName());
        return values;
    }
}
