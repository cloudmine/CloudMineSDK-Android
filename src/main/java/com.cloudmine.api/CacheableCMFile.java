package com.cloudmine.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.widget.ImageView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.cloudmine.api.exceptions.CreationException;
import com.cloudmine.api.rest.BaseFileCreationRequest;
import com.cloudmine.api.rest.BaseFileLoadRequest;
import com.cloudmine.api.rest.CloudMineRequest;
import com.cloudmine.api.rest.SharedRequestQueueHolders;
import com.cloudmine.api.rest.response.FileCreationResponse;
import com.cloudmine.api.rest.response.FileLoadResponse;
import org.apache.http.HttpResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import static com.cloudmine.api.rest.SharedRequestQueueHolders.getRequestQueue;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class CacheableCMFile extends CMFile implements LocallySavable{

    public static void populateImageViewFromLocalOrNetwork(Context context, ImageView imageView, String fileId) {
        populateImageViewFromLocalOrNetwork(imageView, fileId, shouldUseExternalStorage(context));
    }

    public static void populateImageViewFromLocalOrNetwork(ImageView imageView, String fileId, boolean fromExternalStorage) {

    }

    public static CMFile loadLocalFile(Context context, String fileId) {
        return loadLocalFile(context, fileId, shouldUseExternalStorage(context));
    }

    public static CacheableCMFile loadLocalFile(Context context, String fileId, boolean fromExternalStorage) {
        if(fromExternalStorage) return loadLocalFileFromExternalStorage(context, fileId);
        else                    return loadLocalFileFromInternalStorage(context, fileId);
    }

    public static CacheableCMFile loadLocalFileFromInternalStorage(Context context, String fileId) {
        try {
            return new CacheableCMFile(context.openFileInput(fileId), fileId);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static CacheableCMFile loadLocalFileFromExternalStorage(Context context, String fileId) {
        File file = new File(Environment.getExternalStorageDirectory(), fileId);
        return getCmFile(fileId, file);
    }

    private static CacheableCMFile getCmFile(String fileId, File file) {
        if(file == null || !file.canRead()) {
            return null;
        }
        try {
            return new CacheableCMFile(new FileInputStream(file), fileId, null);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static CloudMineRequest loadFile(Context context, String fileName, Response.Listener<FileLoadResponse> successListener, Response.ErrorListener errorListener) {
        return loadFile(context, fileName, null, successListener, errorListener);
    }

    public static CloudMineRequest loadFile(Context context, String fileName, CMSessionToken sessionToken, Response.Listener<FileLoadResponse> successListener, Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        BaseFileLoadRequest request = new BaseFileLoadRequest(fileName, sessionToken, null, successListener, errorListener); //TODO support running server functions from here
        queue.add(request);
        return request;
    }

    private Date lastSaveDate;

    public CacheableCMFile(byte[] fileContents, String fileId, String contentType) throws CreationException {
        super(fileContents, fileId, contentType);
    }

    public CacheableCMFile(InputStream contents) throws CreationException {
        super(contents);
    }

    public CacheableCMFile(InputStream contents, String contentType) throws CreationException {
        super(contents, contentType);
    }

    public CacheableCMFile(HttpResponse response, String fileId) throws CreationException {
        super(response, fileId);
    }

    public CacheableCMFile(InputStream contents, String fileId, String contentType) throws CreationException {
        super(contents, fileId, contentType);
    }

    public CloudMineRequest save(Context context, CMSessionToken token, Response.Listener<FileCreationResponse> successListener, Response.ErrorListener errorListener) {
        RequestQueue queue = SharedRequestQueueHolders.getRequestQueue(context);
        BaseFileCreationRequest request = new BaseFileCreationRequest(this, token, null, successListener, errorListener);
        queue.add(request);
        return request;
    }

    public CloudMineRequest save(Context context, Response.Listener< FileCreationResponse > successListener, Response.ErrorListener errorListener) {
        CMSessionToken token = getUser() == null ? null : getUser().getSessionToken();
        return save(context, token, successListener, errorListener);
    }


    @Override
    public boolean saveLocally(Context context) {
        return saveLocally(context, shouldUseExternalStorage(context));
    }

    public static boolean shouldUseExternalStorage(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(CMSharedPreferencesConstants.PREFERENCES_KEY, Context.MODE_PRIVATE);
        return preferences.getBoolean(CMSharedPreferencesConstants.USE_EXTERNAL_STORAGE_KEY, CMSharedPreferencesConstants.SHOULD_USE_EXTERNAL_STORAGE_DEFAULT);
    }

    public static void setShouldUseExternalStorage(Context context, boolean shouldUseExternalStorage) {
        SharedPreferences preferences = context.getSharedPreferences(CMSharedPreferencesConstants.PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(CMSharedPreferencesConstants.USE_EXTERNAL_STORAGE_KEY, shouldUseExternalStorage);
        editor.apply();
    }

    public boolean saveLocally(Context context, boolean useInternalStorage) {
        if(useInternalStorage) {
            return saveToInternalStorage(context);
        } else {
            return saveToExternalStorage();
        }
    }

    public boolean saveToExternalStorage() {
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            byte[] fileContents = getFileContents();
            File output = new File(Environment.getExternalStorageDirectory(), getFileId());
            try {
                new FileOutputStream(output).write(fileContents);
            } catch (IOException e) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean saveToInternalStorage(Context context) {
        try {
            byte[] fileContents = getFileContents();
            context.openFileOutput(getFileId(), Context.MODE_PRIVATE).write(fileContents);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean saveEventually(Context context) {
        return false;
    }

    @Override
    public int getLastSavedDateAsSeconds() {
        return 0;
    }

    @Override
    public Date getLastSaveDate() {
        return null;
    }
}
