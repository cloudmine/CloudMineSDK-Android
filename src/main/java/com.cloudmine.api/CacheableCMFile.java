package com.cloudmine.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cloudmine.api.db.RequestDBObject;
import com.cloudmine.api.db.RequestDBOpenHelper;
import com.cloudmine.api.db.RequestPerformerService;
import com.cloudmine.api.exceptions.CreationException;
import com.cloudmine.api.rest.BaseFileCreationRequest;
import com.cloudmine.api.rest.BaseFileDeleteRequest;
import com.cloudmine.api.rest.BaseFileLoadRequest;
import com.cloudmine.api.rest.CloudMineRequest;
import com.cloudmine.api.rest.SharedRequestQueueHolders;
import com.cloudmine.api.rest.response.FileCreationResponse;
import com.cloudmine.api.rest.response.FileLoadResponse;
import com.cloudmine.api.rest.response.ObjectModificationResponse;
import me.cloudmine.annotations.Optional;
import org.apache.http.HttpResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.cloudmine.api.rest.SharedRequestQueueHolders.getRequestQueue;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class CacheableCMFile extends CMFile implements LocallySavable{

    public static Bitmap asBitmap(CMFile file) {
        if(file == null) return null;
        byte[] fileContents = file.getFileContents();
        return BitmapFactory.decodeByteArray(fileContents, 0, fileContents.length);
    }

    public static void populateImageViewFromLocalOrNetwork(final Context context, final ImageView imageView, @Optional final int errorDisplay, final String fileId, @Optional CMSessionToken sessionToken) {
        populateImageViewFromLocalOrNetwork(context, imageView, errorDisplay, fileId, sessionToken, shouldUseExternalStorage(context));
    }

    public static void populateImageViewFromLocalOrNetwork(final Context context, final ImageView imageView, @Optional final int errorResourceId, final String fileId, @Optional CMSessionToken sessionToken, final boolean fromExternalStorage) {
        CacheableCMFile file = fromExternalStorage ?
                loadLocalFileFromExternalStorage(fileId) :
                loadLocalFileFromInternalStorage(context, fileId);
        if(file == null) {
            loadFile(context, fileId, sessionToken,
                    new Response.Listener<FileLoadResponse>() {
                        @Override
                        public void onResponse(FileLoadResponse response) {
                            CMFile file = response.getFile();
                            Bitmap bitmap = asBitmap(file);
                            if (bitmap != null) {
                                imageView.setImageBitmap(bitmap);
                                boolean saved = saveLocally(context, fromExternalStorage, file);
                            } else if (errorResourceId != 0) {
                                imageView.setImageResource(errorResourceId);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (errorResourceId != 0) {
                                imageView.setImageResource(errorResourceId);
                            }
                        }
                    }
            );
        } else {
            imageView.setImageBitmap(file.asBitmap());
        }
    }

    public static CacheableCMFile loadLocalFile(Context context, String fileId) {
        return loadLocalFile(context, fileId, shouldUseExternalStorage(context));
    }

    public static Map<String, CacheableCMFile> loadLocalFiles(Context context, Collection<String> fileIds) {
        return loadLocalFiles(context, fileIds, shouldUseExternalStorage(context));
    }

    public static Map<String, CacheableCMFile> loadLocalFiles(Context context, Collection<String> fileIds, boolean fromExternalStorage) {
        Map<String, CacheableCMFile> localFiles = new HashMap<String, CacheableCMFile>();
        if(context == null || fileIds == null) return localFiles;
        for(String fileId : fileIds) {
            localFiles.put(fileId, loadLocalFile(context, fileId, fromExternalStorage));
        }
        return localFiles;
    }

    public static CacheableCMFile loadLocalFile(Context context, String fileId, boolean fromExternalStorage) {
        if(fromExternalStorage) return loadLocalFileFromExternalStorage(fileId);
        else                    return loadLocalFileFromInternalStorage(context, fileId);
    }

    public static CacheableCMFile loadLocalFileFromInternalStorage(Context context, String fileId) {
        try {
            return new CacheableCMFile(context.openFileInput(fileId), fileId);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static CacheableCMFile loadLocalFileFromExternalStorage(String fileId) {
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

    public static boolean deleteLocalFile(Context context, String fileId) {
        return deleteLocalFile(context, fileId, shouldUseExternalStorage(context));
    }

    public static boolean deleteLocalFile(Context context, String fileId, boolean fromExternalStorage) {
        if(fromExternalStorage) {
            return deleteLocalFileFromExternalStorage(fileId);
        } else {
            return deleteLocalFileFromInternalStorage(context, fileId);
        }
    }

    public static boolean deleteLocalFileFromInternalStorage(Context context, String fileId) {
        return context.deleteFile(fileId);
    }

    public static boolean deleteLocalFileFromExternalStorage(String fileId) {
        File file = new File(Environment.getExternalStorageDirectory(), fileId);
        if(file != null) return file.delete();
        else             return false;
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

    public CloudMineRequest delete(Context context, CMSessionToken token, Response.Listener<ObjectModificationResponse> successListener, Response.ErrorListener errorListener) {
        RequestQueue queue = SharedRequestQueueHolders.getRequestQueue(context);
        BaseFileDeleteRequest deleteRequest = new BaseFileDeleteRequest(Arrays.asList(getObjectId()), token, null, successListener, errorListener);
        queue.add(deleteRequest);
        return deleteRequest;
    }

    public CloudMineRequest delete(Context context, Response.Listener<ObjectModificationResponse> successListener, Response.ErrorListener errorListener) {
        return delete(context, null, successListener, errorListener);
    }

    public CloudMineRequest save(Context context, CMSessionToken token, Response.Listener<FileCreationResponse> successListener, Response.ErrorListener errorListener) {
        RequestQueue queue = SharedRequestQueueHolders.getRequestQueue(context);
        BaseFileCreationRequest request = new BaseFileCreationRequest(this, token, null, successListener, errorListener);
        queue.add(request);
        return request;
    }

    public CloudMineRequest save(Context context, Response.Listener<FileCreationResponse> successListener, Response.ErrorListener errorListener) {
        CMSessionToken token = getUser() == null ? null : getUser().getSessionToken();
        return save(context, token, successListener, errorListener);
    }

    public static boolean saveLocally(Context context, CMFile file) {
        return saveLocally(context, shouldUseExternalStorage(context), file);
    }

    @Override
    public boolean saveLocally(Context context) {
        return saveLocally(context, this);
    }

    public static boolean shouldUseExternalStorage(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(CMSharedPreferencesConstants.PREFERENCES_KEY, Context.MODE_PRIVATE);
        boolean useExternalStorage = preferences.getBoolean(CMSharedPreferencesConstants.USE_EXTERNAL_STORAGE_KEY, CMSharedPreferencesConstants.SHOULD_USE_EXTERNAL_STORAGE_DEFAULT);
        return useExternalStorage;
    }

    public static void setShouldUseExternalStorage(Context context, boolean shouldUseExternalStorage) {
        SharedPreferences preferences = context.getSharedPreferences(CMSharedPreferencesConstants.PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(CMSharedPreferencesConstants.USE_EXTERNAL_STORAGE_KEY, shouldUseExternalStorage);
        editor.apply();
    }

    public boolean saveLocally(Context context, boolean useExternalStorage) {
        return saveLocally(context, useExternalStorage, this);
    }

    public static boolean saveLocally(Context context, boolean useExternalStorage, CMFile file) {
        if(file == null) return false;
        if(useExternalStorage) {
            return saveToExternalStorage(file);
        } else {
            return saveToInternalStorage(context, file);
        }
    }

    public boolean saveToExternalStorage() {
        return saveToExternalStorage(this);
    }

    public static boolean saveToExternalStorage(CMFile file) {
        if(file == null) return false;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            byte[] fileContents = file.getFileContents();
            File output = new File(Environment.getExternalStorageDirectory(), file.getFileId());
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

    public static boolean saveToInternalStorage(Context context, CMFile file) {
        try {
            byte[] fileContents = file.getFileContents();
            context.openFileOutput(file.getFileId(), Context.MODE_PRIVATE).write(fileContents);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean saveToInternalStorage(Context context) {
        return saveToInternalStorage(context, this);
    }

    @Override
    public boolean saveEventually(Context context) {
        boolean wasCreated = saveLocally(context);
        if(wasCreated) {
            RequestDBObject request = RequestDBObject.createApplicationFileRequest(getFileId());
            try {
            RequestDBOpenHelper.getRequestDBOpenHelper(context).insertRequest(request);
            wasCreated = true;
            }catch (Exception e) {
                wasCreated = false;
            }
        }
        if(wasCreated) {
            context.startService(new Intent(context, RequestPerformerService.class));
        }
        return false;
    }

    @Override
    @Deprecated
    /**
     * Not supported for cmfiles
     */
    public int getLastSavedDateAsSeconds() {
        return 0;
    }

    @Override
    @Deprecated
    /**
     * Not supported for CMFiles
     */
    public Date getLastSaveDate() {
        return null;
    }

    public Bitmap asBitmap() {
        return asBitmap(this);
    }
}
