package com.cloudmine.api.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.cloudmine.api.CacheableCMFile;
import com.cloudmine.api.Strings;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Open Helper for requests that must eventually be synced. Requests can either have their JSON set explicitly, or set
 * to an object id that will be loaded when the request is run.
 *
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class RequestDBOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "requests.db";
    public static final String REQUEST_DATABASE_TABLE = "RequestTable";
    public static final String HEADER_DATABASE_TABLE = "HeaderTable";

    private static final int DATABASE_VERSION = 3;

    public static final String KEY_REQUEST_ID = "_id";
    public static final String KEY_REQUEST_JSON_BODY = "JSON_BODY_COLUMN";
    public static final String KEY_REQUEST_TARGET_URL = "TARGET_URL_COLUMN";
    public static final String KEY_REQUEST_VERB = "REQUEST_VERB_COLUMN";
    public static final String KEY_REQUEST_SYNCHRONIZED = "SYNCHRONIZED_COLUMN"; //0 for unsent, 1 for in progress, 2 for sent
    public static final String KEY_REQUEST_OBJECT_ID = "REQUEST_OBJECT_ID";
    public static final String KEY_REQUEST_FILE_ID = "REQUEST_FILE_ID";

    public static final String KEY_HEADER_ID = "_id";
    public static final String KEY_HEADER_NAME = "HEADER_NAME";
    public static final String KEY_HEADER_VALUE = "HEADER_VALUE";
    public static final String KEY_HEADER_REQUEST_FK = "REQUEST_ID";

    public static final Integer UNSYCHRONIZED = Integer.valueOf(0);
    public static final Integer IN_PROGRESS = Integer.valueOf(1);
    public static final Integer SYNCHRONIZED = Integer.valueOf(2);
    public static final int PERMANENTLY_FAILED = 3;
    public static final List<String> REQUEST_COLUMN_NAMES = new ArrayList<String>();
    public static final List<String> HEADER_COLUMN_NAMES = new ArrayList<String>();
    static {
        REQUEST_COLUMN_NAMES.add(KEY_REQUEST_ID);
        REQUEST_COLUMN_NAMES.add(KEY_REQUEST_JSON_BODY);
        REQUEST_COLUMN_NAMES.add(KEY_REQUEST_TARGET_URL);
        REQUEST_COLUMN_NAMES.add(KEY_REQUEST_VERB);
        REQUEST_COLUMN_NAMES.add(KEY_REQUEST_SYNCHRONIZED);
        REQUEST_COLUMN_NAMES.add(KEY_REQUEST_OBJECT_ID);
        REQUEST_COLUMN_NAMES.add(KEY_REQUEST_FILE_ID);

        HEADER_COLUMN_NAMES.add(KEY_HEADER_ID);
        HEADER_COLUMN_NAMES.add(KEY_HEADER_NAME);
        HEADER_COLUMN_NAMES.add(KEY_HEADER_VALUE);
        HEADER_COLUMN_NAMES.add(KEY_HEADER_REQUEST_FK);
    }

    private static final String[] RESULTS_COLUMNS = {
            requestColumn(KEY_REQUEST_ID),
            requestColumn(KEY_REQUEST_JSON_BODY),
            requestColumn(KEY_REQUEST_TARGET_URL),
            requestColumn(KEY_REQUEST_VERB),
            requestColumn(KEY_REQUEST_SYNCHRONIZED),
            requestColumn(KEY_REQUEST_OBJECT_ID),
            requestColumn(KEY_REQUEST_FILE_ID),
            headerColumn(KEY_HEADER_NAME),
            headerColumn(KEY_HEADER_VALUE)
    };

    private static String requestColumn(String columnName) {
        return REQUEST_DATABASE_TABLE + "." + columnName;
    }

    private static String headerColumn(String columnName) {
        return HEADER_DATABASE_TABLE + "." + columnName;
    }

    private static final String HEADER_DATABASE_CREATE = "create table " + HEADER_DATABASE_TABLE +
            " (" +
            KEY_HEADER_ID + " integer primary key autoincrement, " +
            KEY_HEADER_NAME + " text not null, " +
            KEY_HEADER_VALUE + " text not null, " +
            KEY_HEADER_REQUEST_FK + " INTEGER, " +
            "FOREIGN KEY(" + KEY_HEADER_REQUEST_FK + ") REFERENCES " + REQUEST_DATABASE_TABLE + "(" + KEY_REQUEST_ID + ")" +
            ");";

    private static final String REQUEST_DATABASE_CREATE = "create table " + REQUEST_DATABASE_TABLE +
            " (" +
            KEY_REQUEST_ID + " integer primary key autoincrement, " +
            KEY_REQUEST_JSON_BODY + " text, " +
            KEY_REQUEST_TARGET_URL + " text not null, " +
            KEY_REQUEST_VERB + " text not null, " +
            KEY_REQUEST_SYNCHRONIZED + " integer not null, " +
            KEY_REQUEST_OBJECT_ID + " text, " +
            KEY_REQUEST_FILE_ID + " text" +
            ");";
    private static final String KEY_WHERE = KEY_REQUEST_ID + "=?";
    private static final String SYNCHRONIZED_VALUE_WHERE = requestColumn(KEY_REQUEST_SYNCHRONIZED) + "=?";
    private static final String SYNCHRONIZED_VALUE_WITH_IDS_WHERE = requestColumn(KEY_REQUEST_SYNCHRONIZED) + "=? AND " + KEY_REQUEST_ID + " IN (?)";
    private static final String BOTH_DATABASE_TABLE_JOIN = REQUEST_DATABASE_TABLE + " LEFT OUTER JOIN " + HEADER_DATABASE_TABLE + " ON (" +
            REQUEST_DATABASE_TABLE + "." + KEY_REQUEST_ID + "=" + HEADER_DATABASE_TABLE + "." + KEY_HEADER_REQUEST_FK + ")";

    public static final long FAILED_REQUEST = -1;

    //TODO issue that we are holding onto the context here? Don't think so because it is the application context
    private static RequestDBOpenHelper requestDBOpenHelper;
    public static synchronized RequestDBOpenHelper getRequestDBOpenHelper(Context context) {
        if(requestDBOpenHelper == null) {
            requestDBOpenHelper = new RequestDBOpenHelper(context.getApplicationContext());
        }
        return requestDBOpenHelper;
    }

    public RequestDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createDatabase(sqLiteDatabase);
    }

    private void createDatabase(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(REQUEST_DATABASE_CREATE);
        sqLiteDatabase.execSQL(HEADER_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        dropDatabase(sqLiteDatabase);
        onCreate(sqLiteDatabase);
    }

    private void dropDatabase(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HEADER_DATABASE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + REQUEST_DATABASE_TABLE);
    }
    /**
     * Inserts a request into the database. If the insertion fails, an exception will be thrown. Uses transactions
     * so only the entire request or none of it will be inserted.
     * @param request to insert. Do not pass a null request in here
     */
    public void insertRequest(RequestDBObject request) {
        if(request == null) return;
        insertRequest(request.toRequestContentValues(), request.toHeaderContentValues());
    }


    public void insertRequest(ContentValues requestValues, ContentValues[] headerValues) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            long requestId = db.insertOrThrow(REQUEST_DATABASE_TABLE, null, requestValues);
            throwIfFailed(requestId);
            for(ContentValues headerValue : headerValues) {
                headerValue.put(KEY_HEADER_REQUEST_FK, requestId);
                long result = db.insertOrThrow(HEADER_DATABASE_TABLE, null, headerValue);
                throwIfFailed(result);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    private void throwIfFailed(long result) {
        if(result == FAILED_REQUEST)
            throw new RuntimeException("Request failed");
    }

    /**
     * Set the specified request, identified by its row id, as synchronized
     * @param rowId
     */
    public void setSynchronized(Integer rowId) {
        setSynchronizedStatus(rowId, SYNCHRONIZED);
    }

    /**
     * Set the specified request, identified by its row id, as unsynchronized
     * @param rowId
     */
    public void setUnsychronized(Integer rowId) {
        setSynchronizedStatus(rowId, UNSYCHRONIZED);
    }

    public void setPermanentlyFailed(Integer rowId) {
        setSynchronizedStatus(rowId, PERMANENTLY_FAILED);
    }

    private void setSynchronizedStatus(Integer rowId, Integer synchronizedStatus) {
        String[] whereArgs = {rowId.toString()};
        ContentValues updateSynchronizedContentValues = getUpdateSynchronizedContentValues(synchronizedStatus);
        int updateCount = getWritableDatabase().update(REQUEST_DATABASE_TABLE, updateSynchronizedContentValues, KEY_WHERE, whereArgs);
    }

    /**
     * Sets all of the currently in progress requests to unsynchronized. Should not be called while the
     * RequestPerformerService OR A THREAD SPAWNED BY IT is currently running. Used to reset 'stuck' requests
     */
    public void setInProgressToUnsynchronized() {
        String[] whereArgs = {IN_PROGRESS.toString()};
        ContentValues updateSynchronizedContentValues = getUpdateSynchronizedContentValues(UNSYCHRONIZED);
        int numUpdated = getWritableDatabase().update(REQUEST_DATABASE_TABLE, updateSynchronizedContentValues, SYNCHRONIZED_VALUE_WHERE, whereArgs);
    }

    public void setInProgressToUnsynchronized(Collection<Integer> withIds) {
        String idCsv = collectionToCsv(withIds);
        String[] whereArgs = {IN_PROGRESS.toString(), idCsv};
        ContentValues updateSynchronizedContentValues = getUpdateSynchronizedContentValues(UNSYCHRONIZED);
        int numUpdated = getWritableDatabase().update(REQUEST_DATABASE_TABLE,
                updateSynchronizedContentValues,
                SYNCHRONIZED_VALUE_WITH_IDS_WHERE, whereArgs);
    }

    private String collectionToCsv(Collection<? extends Object> collection) {
        if(collection == null || collection.isEmpty())
            return "";
        StringBuilder csvBuilder = new StringBuilder();
        String separator = "";
        for(Object element : collection) {
            csvBuilder.append(separator).append(element);
            separator = ", ";
        }
        return csvBuilder.toString();
    }

    /**
     * Get all of the requests that are currently unsynced. Sets their status to in progress
     * @return
     */
    public LinkedHashMap<Integer, RequestDBObject> retrieveRequestsForSending(Context context) {
        Cursor requestCursor = loadRequestTableContentsForUpdating();
        LinkedHashMap<Integer, RequestDBObject> requestMapping = createRequestMapping(requestCursor);

        Map<String, RequestDBObject> objectIdsToRequests = new HashMap<String, RequestDBObject>();
        Map<String, RequestDBObject> fileIdsToRequests = new HashMap<String, RequestDBObject>();
        for(RequestDBObject request : requestMapping.values()) {
            String objectId = request.getObjectId();
            if(Strings.isNotEmpty(objectId)) {
                objectIdsToRequests.put(objectId, request);
            }

            String fileId = request.getFileId();
            if(Strings.isNotEmpty(fileId)) {
                fileIdsToRequests.put(fileId, request);
            }
        }
        if(!objectIdsToRequests.isEmpty()) {
            Map<String, String> objectIdToJson = CMObjectDBOpenHelper.getCMObjectDBHelper(context).loadObjectJsonById(objectIdsToRequests.keySet());
            for(Map.Entry<String, String> objectIdAndJsonEntry : objectIdToJson.entrySet()) {
                objectIdsToRequests.get(objectIdAndJsonEntry.getKey()).setJsonBody(objectIdAndJsonEntry.getValue());
            }
        }
        if(!fileIdsToRequests.isEmpty()) {
            Map<String, CacheableCMFile> fileIdToFiles = CacheableCMFile.loadLocalFiles(context, fileIdsToRequests.keySet());
            for(Map.Entry<String, RequestDBObject> fileIdAndRequest : fileIdsToRequests.entrySet()) {
                CacheableCMFile cacheableCMFile = fileIdToFiles.get(fileIdAndRequest.getKey());
                if(cacheableCMFile != null) fileIdAndRequest.getValue().setBody(cacheableCMFile.getFileContents());
                else Log.e("CloudMine", "Had a null file " + fileIdAndRequest.getKey());
            }
        }
        return requestMapping;
    }

    public LinkedHashMap<Integer, RequestDBObject> retrieveAllRequests() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(BOTH_DATABASE_TABLE_JOIN, RESULTS_COLUMNS, null, null, null, null, requestColumn(KEY_REQUEST_ID));
        return createRequestMapping(cursor);
    }

    /**
     * Convert Cursor contents to a map from the request id to the request. The Map will have the same iteration order
     * as the Cursor
     * @param cursor
     * @return
     */
    private LinkedHashMap<Integer, RequestDBObject> createRequestMapping(Cursor cursor) {
        int idIndex = cursor.getColumnIndexOrThrow(KEY_REQUEST_ID);
        int jsonIndex = cursor.getColumnIndexOrThrow(KEY_REQUEST_JSON_BODY);
        int urlIndex = cursor.getColumnIndexOrThrow(KEY_REQUEST_TARGET_URL);
        int verbIndex = cursor.getColumnIndexOrThrow(KEY_REQUEST_VERB);
        int syncedIndex = cursor.getColumnIndexOrThrow(KEY_REQUEST_SYNCHRONIZED);
        int headerNameIndex = cursor.getColumnIndexOrThrow(KEY_HEADER_NAME);
        int headerValueIndex = cursor.getColumnIndexOrThrow(KEY_HEADER_VALUE);
        int objectIdIndex = cursor.getColumnIndexOrThrow(KEY_REQUEST_OBJECT_ID);
        int fileIdIndex = cursor.getColumnIndexOrThrow(KEY_REQUEST_FILE_ID);
        LinkedHashMap<Integer, RequestDBObject> requestMapping = new LinkedHashMap<Integer, RequestDBObject>();
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(idIndex);

            RequestDBObject request = requestMapping.get(id);
            if(request == null) {
                String json = cursor.getString(jsonIndex);
                String url = cursor.getString(urlIndex);
                String verb = cursor.getString(verbIndex);
                String objectId = cursor.getString(objectIdIndex);
                String fileId = cursor.getString(fileIdIndex);
                int syncOrdinal = cursor.getInt(syncedIndex);
                RequestDBObject.SyncStatus status = RequestDBObject.SyncStatus.getSyncStatus(syncOrdinal);
                request = new RequestDBObject(url, RequestDBObject.Verb.getVerb(verb), json, objectId, fileId, id, status, new ArrayList<Header>());
                requestMapping.put(id, request);
            }
            String headerName = cursor.getString(headerNameIndex);
            String headerValue = cursor.getString(headerValueIndex);
            request.addHeader(new BasicHeader(headerName, headerValue));
        }
        return requestMapping;
    }

    /**
     * Load all of the unsynced requests, and set their status to in progress.
     * @return
     */
    private Cursor loadRequestTableContentsForUpdating() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            String[] unsychronizedSelectionArgs = {UNSYCHRONIZED.toString()};
            Cursor cursor =
                    db.query(BOTH_DATABASE_TABLE_JOIN, RESULTS_COLUMNS, SYNCHRONIZED_VALUE_WHERE, unsychronizedSelectionArgs,
                            null, null, requestColumn(KEY_REQUEST_ID));
            cursor.getCount(); //For some reason, accessing the cursor count before performing the update is required for the load to work. Doesn't make much sense unless it is ignoring order.
            ContentValues updatedValues = getUpdateSynchronizedContentValues(IN_PROGRESS);
            db.update(REQUEST_DATABASE_TABLE, updatedValues, SYNCHRONIZED_VALUE_WHERE, unsychronizedSelectionArgs);

            db.setTransactionSuccessful();

            return cursor;
        }catch(Throwable t) {
            throw new RuntimeException(t);
        }
        finally {
            db.endTransaction();
        }
    }

    private ContentValues getUpdateSynchronizedContentValues(Integer newValue) {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(KEY_REQUEST_SYNCHRONIZED, newValue);
        return updatedValues;
    }

    private void resetDatabase() {
        dropDatabase(getWritableDatabase());
        createDatabase(getWritableDatabase());
    }
}
