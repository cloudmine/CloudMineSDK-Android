package com.cloudmine.api.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.cloudmine.api.CMObject;
import com.cloudmine.api.Strings;
import com.cloudmine.api.persistance.ClassNameRegistry;
import com.cloudmine.api.rest.JsonUtilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
    public static final String CLASS_NAME_COLUMN = "CLASS_NAME";
    public static final String SAVED_DATE_COLUMN = "SAVED_DATE";
    public static final String SYNCED_DATE_COLUMN = "SYNCED_DATE";
    public static final int DATABASE_VERSION = 2;

    private static final String CMOBJECT_DATABASE_CREATE = "create table " + CM_OBJECT_TABLE +
            " (" +
            OBJECT_ID_COLUMN + " text not null primary key, " +
            CLASS_NAME_COLUMN + " text not null, " +
            JSON_COLUMN + " text not null, " +
            SAVED_DATE_COLUMN + " integer not null, " +
            SYNCED_DATE_COLUMN + " integer" +
            ")";
    private static final String OBJECT_ID_WHERE = OBJECT_ID_COLUMN + "=?";
    private static final String MULTI_OBJECT_ID_WHERE = OBJECT_ID_COLUMN + " in (?)";
    private static final String UPDATE_OBJECT_WHERE = OBJECT_ID_WHERE + " AND " + SAVED_DATE_COLUMN + "<?";
    private static final String CLASS_SELECT_WHERE = CLASS_NAME_COLUMN + "=?";
    private static final String[] COLUMNS = {OBJECT_ID_COLUMN, CLASS_NAME_COLUMN, JSON_COLUMN, SAVED_DATE_COLUMN, SYNCED_DATE_COLUMN};

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
        try {
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
        } finally {
            db.close();
        }
    }

    public <OBJECT_TYPE extends LocallySavableCMObject> OBJECT_TYPE loadObjectById(String objectId) {
        if(Strings.isEmpty(objectId)) return null;

        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.query(CM_OBJECT_TABLE, new String[]{JSON_COLUMN}, OBJECT_ID_WHERE, new String[]{objectId}, null, null, null);
            if(!cursor.moveToNext()) return null;
            return fromCursor(cursor);
        }finally {
            db.close();
        }
    }

    public int deleteObjectById(String objectId) {
        if(Strings.isEmpty(objectId)) return 0;

        SQLiteDatabase db = getWritableDatabase();
        try {
            return db.delete(CM_OBJECT_TABLE, OBJECT_ID_WHERE, new String[]{objectId});
        }finally {
            db.close();
        }
    }

    public Map<String, String> loadObjectJsonById(Collection <String> objectIds) {
        Map<String, String> objectIdsToJson = new HashMap<String, String>();

        SQLiteDatabase db = getReadableDatabase();
        try {
            StringBuilder queryBuilder = new StringBuilder(OBJECT_ID_COLUMN).append(" IN (").append(collectionToCsv(objectIds)).append(")");
            Cursor cursor = db.query(CM_OBJECT_TABLE, new String[] {JSON_COLUMN, OBJECT_ID_COLUMN}, queryBuilder.toString(), null, null, null, null);

            int jsonIndex = cursor.getColumnIndex(JSON_COLUMN);
            int objectIdIndex = cursor.getColumnIndex(OBJECT_ID_COLUMN);
            while (cursor.moveToNext()) {
                String json = cursor.getString(jsonIndex);
                String objectId = cursor.getString(objectIdIndex);
                objectIdsToJson.put(objectId, json);
            }
        }finally {
            db.close();
        }

        return objectIdsToJson;
    }

    public <TYPE extends LocallySavableCMObject> List<TYPE> loadObjectsByClass(Class <TYPE> klass) {
        String[] args = {ClassNameRegistry.forClass(klass)};
        SQLiteDatabase readableDatabase = getReadableDatabase();
        try {
            Cursor results = readableDatabase.query(CM_OBJECT_TABLE, COLUMNS, CLASS_SELECT_WHERE, args, null, null, null, null);
            List<TYPE> resultList = new ArrayList<TYPE>();
            while (results.moveToNext()) {
                resultList.add((TYPE)fromCursor(results));
            }
            return resultList;
        }finally{
            readableDatabase.close();
        }
    }


    private String collectionToCsv(Collection<? extends Object> collection) {
        if(collection == null || collection.isEmpty())
            return "";
        StringBuilder csvBuilder = new StringBuilder();
        String separator = "";

        for(Object element : collection) {
            csvBuilder.append(separator).append('"').append(element).append('"');
            separator = ", ";
        }
        return csvBuilder.toString();
    }

    private static <OBJECT_TYPE extends LocallySavableCMObject> OBJECT_TYPE fromCursor(Cursor cursor) {

        int jsonIndex = cursor.getColumnIndex(JSON_COLUMN);
        String json = cursor.getString(jsonIndex);
        if(Strings.isEmpty(json)) return null;
        Map<String,CMObject> stringCMObjectMap = JsonUtilities.jsonToClassMap(json);
        if(stringCMObjectMap.isEmpty()) return null;
        return (OBJECT_TYPE) stringCMObjectMap.values().iterator().next();
    }
}
