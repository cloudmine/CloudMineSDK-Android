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
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.FileCreationResponse;
import com.cloudmine.api.rest.response.FileLoadResponse;
import com.cloudmine.api.rest.response.ObjectModificationResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;
import me.cloudmine.annotations.Single;

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
 * A CMFile that can be cached locally.
 * <br>
 * Copyright CloudMine, Inc. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class BaseCacheableCMFile extends CMFile implements LocallySavable{


    /**
     * Load the file with the given fileId
     * @param context
     * @param fileId
     * @param sessionToken if specified, will load a user level file with the given id
     * @param apiCredentials
     * @param serverFunction
     * @param successListener
     * @param errorListener
     * @return
     */
    @Expand(isStatic = true)
    public static CloudMineRequest loadFile(Context context, String fileId, @Optional CMSessionToken sessionToken, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<FileLoadResponse> successListener, @Optional Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        BaseFileLoadRequest request = new BaseFileLoadRequest(fileId, sessionToken, apiCredentials, serverFunction, successListener, errorListener);
        queue.add(request);
        return request;
    }

    /**
     * Delete the file(s) with the given fileIds
     * @param context
     * @param ids all the ids of the files to delete
     * @param sessionToken if specified, will delete user level files with the given ids
     * @param apiCredentials
     * @param serverFunction
     * @param successListener
     * @param errorListener
     * @return
     */
    @Expand(isStatic = true)
    public static CloudMineRequest delete(Context context, @Single Collection<String> ids, @Optional CMSessionToken sessionToken, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<ObjectModificationResponse> successListener, @Optional Response.ErrorListener errorListener) {
        CloudMineRequest request = new BaseFileDeleteRequest(ids, sessionToken, apiCredentials, serverFunction, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        return request;
    }

    /**
     * Convert the given file into a Bitmap.
     * @param file
     * @return null if the file was null, a Bitmap otherwise
     */
    public static Bitmap asBitmap(CMFile file) {
        if(file == null) return null;
        byte[] fileContents = file.getFileContents();
        return BitmapFactory.decodeByteArray(fileContents, 0, fileContents.length);
    }

    @Expand(isStatic = true)
    public static void populateImageViewFromLocalOrNetwork(final Context context, final ImageView imageView, @Optional final int errorDisplay, final String fileId, @Optional CMSessionToken sessionToken, @Optional CMApiCredentials apiCredentials,  @Optional CMServerFunction serverFunction) {
        populateImageViewFromLocalOrNetwork(context, imageView, errorDisplay, fileId, sessionToken, apiCredentials, serverFunction, shouldUseExternalStorage(context));
    }

    /**
     * Populates an imageview with a CacheableCMFile.
     * @param context
     * @param imageView
     * @param errorResourceId
     * @param fileId
     * @param sessionToken
     * @param fromExternalStorage
     */
    @Expand(isStatic = true)
    public static void populateImageViewFromLocalOrNetwork(final Context context, final ImageView imageView, @Optional final int errorResourceId, final String fileId, @Optional CMSessionToken sessionToken, @Optional CMApiCredentials apiCredentials, CMServerFunction serverFunction, final boolean fromExternalStorage) {
        BaseCacheableCMFile file = fromExternalStorage ?
                loadLocalFileFromExternalStorage(fileId) :
                loadLocalFileFromInternalStorage(context, fileId);
        if(file == null) {
            loadFile(context, fileId, sessionToken, apiCredentials, serverFunction,
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

    public static BaseCacheableCMFile loadLocalFile(Context context, String fileId) {
        return loadLocalFile(context, fileId, shouldUseExternalStorage(context));
    }

    public static Map<String, BaseCacheableCMFile> loadLocalFiles(Context context, Collection<String> fileIds) {
        return loadLocalFiles(context, fileIds, shouldUseExternalStorage(context));
    }

    public static Map<String, BaseCacheableCMFile> loadLocalFiles(Context context, Collection<String> fileIds, boolean fromExternalStorage) {
        Map<String, BaseCacheableCMFile> localFiles = new HashMap<String, BaseCacheableCMFile>();
        if(context == null || fileIds == null) return localFiles;
        for(String fileId : fileIds) {
            localFiles.put(fileId, loadLocalFile(context, fileId, fromExternalStorage));
        }
        return localFiles;
    }

    public static BaseCacheableCMFile loadLocalFile(Context context, String fileId, boolean fromExternalStorage) {
        if(fromExternalStorage) return loadLocalFileFromExternalStorage(fileId);
        else                    return loadLocalFileFromInternalStorage(context, fileId);
    }

    public static BaseCacheableCMFile loadLocalFileFromInternalStorage(Context context, String fileId) {
        try {
            return new BaseCacheableCMFile(context.openFileInput(fileId), fileId, null);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static BaseCacheableCMFile loadLocalFileFromExternalStorage(String fileId) {
        File file = new File(Environment.getExternalStorageDirectory(), fileId);
        return getCmFile(fileId, file);
    }

    private static BaseCacheableCMFile getCmFile(String fileId, File file) {
        if(file == null || !file.canRead()) {
            return null;
        }
        try {
            return new BaseCacheableCMFile(new FileInputStream(file), fileId, null);
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

    private Date lastSaveDate;

    @Expand
    public BaseCacheableCMFile(byte[] fileContents, String fileId, @Optional String contentType) throws CreationException {
        super(fileContents, fileId, contentType);
    }

    @Expand
    public BaseCacheableCMFile(InputStream contents) throws CreationException {
        super(contents);
    }

    @Expand
    public BaseCacheableCMFile(InputStream contents, String fileId, @Optional String contentType) throws CreationException {
        super(contents, fileId, contentType);
    }

    @Expand
    public CloudMineRequest delete(Context context, @Optional CMSessionToken token, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<ObjectModificationResponse> successListener, @Optional Response.ErrorListener errorListener) {
        RequestQueue queue = SharedRequestQueueHolders.getRequestQueue(context);
        BaseFileDeleteRequest deleteRequest = new BaseFileDeleteRequest(Arrays.asList(getObjectId()), token, apiCredentials, serverFunction, successListener, errorListener);
        queue.add(deleteRequest);
        return deleteRequest;
    }

    @Expand
    public CloudMineRequest save(Context context, @Optional CMSessionToken token,  @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<FileCreationResponse> successListener, @Optional Response.ErrorListener errorListener) {
        RequestQueue queue = SharedRequestQueueHolders.getRequestQueue(context);
        BaseFileCreationRequest request = new BaseFileCreationRequest(this, token, apiCredentials, serverFunction, successListener, errorListener);
        queue.add(request);
        return request;
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

    public boolean saveEventually(Context context) {
        return saveEventually(context, null);
    }

    public boolean saveEventually(Context context, CMSessionToken sessionToken) {
        boolean wasCreated = saveLocally(context);
        if(wasCreated) {
            RequestDBObject request;
            if(sessionToken == null) {
                request = RequestDBObject.createApplicationFileRequest(getFileId());
            } else {
                request = RequestDBObject.createUserFileRequest(getFileId(), sessionToken);
            }
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
    public int getLastLocalSavedDateAsSeconds() {
        return 0;
    }

    @Override
    @Deprecated
    /**
     * Not supported for CMFiles
     */
    public Date getLastLocalSaveDate() {
        return null;
    }

    public Bitmap asBitmap() {
        return asBitmap(this);
    }
}
