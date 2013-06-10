package com.cloudmine.api;

import android.content.ContentValues;
import android.content.Context;
import com.cloudmine.api.db.CMObjectDBOpenHelper;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: johnmccarthy
 * Date: 6/5/13
 * Time: 5:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocallySavableCMObject extends CMObject {

    private static CMObjectDBOpenHelper openHelper;
    private static synchronized CMObjectDBOpenHelper getDBHelper(Context context) {
        if(openHelper == null) {
            openHelper = new CMObjectDBOpenHelper(context.getApplicationContext());
        }
        return openHelper;
    }

    public static <OBJECT_TYPE extends LocallySavableCMObject> OBJECT_TYPE loadObject(Context context, String objectId) {
        return getDBHelper(context).loadObjectById(objectId);
    }

    private Date lastSaveDate;

    public boolean saveLocally(Context context) {
        lastSaveDate = new Date();
        return getDBHelper(context).insertCMObjectIfNewer(this);
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
        return values;
    }
}
