package com.cloudmine.api;

import android.content.Context;
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

import java.io.InputStream;

import static com.cloudmine.api.rest.SharedRequestQueueHolders.getRequestQueue;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class CacheableCMFile extends CMFile {

    public static CloudMineRequest loadFile(Context context, String fileName, Response.Listener<FileLoadResponse> successListener, Response.ErrorListener errorListener) {
        return loadFile(context, fileName, null, successListener, errorListener);
    }

    public static CloudMineRequest loadFile(Context context, String fileName, CMSessionToken sessionToken, Response.Listener<FileLoadResponse> successListener, Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        BaseFileLoadRequest request = new BaseFileLoadRequest(fileName, sessionToken, null, successListener, errorListener); //TODO support running server functions from here
        queue.add(request);
        return request;
    }

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
}
