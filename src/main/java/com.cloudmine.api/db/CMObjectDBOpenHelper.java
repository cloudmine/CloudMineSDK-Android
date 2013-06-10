package com.cloudmine.api.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.cloudmine.api.CMObject;
import com.cloudmine.api.LocallySavableCMObject;
import com.cloudmine.api.Strings;
import com.cloudmine.api.rest.JsonUtilities;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: johnmccarthy
 * Date: 6/6/13
 * Time: 4:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class CMObjectDBOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "cmobjects.db";
    public static final String CM_OBJECT_TABLE = "CMObjectTable";
    public static final String OBJECT_ID_COLUMN = "OBJECT_ID";
    public static final String JSON_COLUMN = "JSON";
    public static final String SAVED_DATE_COLUMN = "SavedDate";
    public static final String SYNCED_DATE_COLUMN = "SyncedDate";
    public static final int DATABASE_VERSION = 1;

    private static final String CMOBJECT_DATABASE_CREATE = "create table " + CM_OBJECT_TABLE +
            " (" +
            OBJECT_ID_COLUMN + " text not null primary key, " +
            JSON_COLUMN + " text not null, " +
            SAVED_DATE_COLUMN + " integer not null, " +
            SYNCED_DATE_COLUMN + " integer" +
            ")";
    private static final String OBJECT_ID_WHERE = OBJECT_ID_COLUMN + "=?";
    private static final String UPDATE_OBJECT_WHERE = OBJECT_ID_WHERE + " AND " + SAVED_DATE_COLUMN + "<?";

    public CMObjectDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CMOBJECT_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        //TODO upgrade gracefully
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CM_OBJECT_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean insertCMObjectIfNewer(LocallySavableCMObject cmObject) {
        if(cmObject == null) return false;
        SQLiteDatabase db = getWritableDatabase();
        boolean wasInsertedOrUpdated;
        long insertResult = db.insert(CM_OBJECT_TABLE, null, cmObject.toContentValues());
        if(insertResult < 0) {
            int numUpdated = db.update(CM_OBJECT_TABLE, cmObject.toContentValues(), UPDATE_OBJECT_WHERE,
                    new String[]{cmObject.getObjectId(), String.valueOf(cmObject.getLastSavedDateAsSeconds())});
            wasInsertedOrUpdated = numUpdated > 0;
        } else {
            wasInsertedOrUpdated = true;
        }
        return wasInsertedOrUpdated;
    }

    public <OBJECT_TYPE extends LocallySavableCMObject> OBJECT_TYPE loadObjectById(String objectId) {
        if(Strings.isEmpty(objectId)) return null;

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(CM_OBJECT_TABLE, new String[]{JSON_COLUMN}, OBJECT_ID_WHERE, new String[]{objectId}, null, null, null);
        return fromCursor(cursor);
    }


    private static <OBJECT_TYPE extends LocallySavableCMObject> OBJECT_TYPE fromCursor(Cursor cursor) {
        if(!cursor.moveToNext()) return null;
        int jsonIndex = cursor.getColumnIndex(JSON_COLUMN);
        String json = cursor.getString(jsonIndex);
        if(Strings.isEmpty(json)) return null;
        Map<String,CMObject> stringCMObjectMap = JsonUtilities.jsonToClassMap(json);
        if(stringCMObjectMap.isEmpty()) return null;
        return (OBJECT_TYPE) stringCMObjectMap.values().iterator().next();
    }
}
