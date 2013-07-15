package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMFile;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.response.FileCreationResponse;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class FileCreationRequest extends CloudMineRequest<FileCreationResponse> {

    static final String BASE_ENDPOINT = "";
    static final CMURLBuilder BASE_URL = new CMURLBuilder(BASE_ENDPOINT, true);

    private final byte[] body;
    private final String contentType;

    public FileCreationRequest(CMFile file, Response.Listener<FileCreationResponse> successListener, Response.ErrorListener errorListener) {
        this(file, null, successListener, errorListener);
    }

    public FileCreationRequest(CMFile file, CMSessionToken sessionToken, Response.Listener<FileCreationResponse> successListener, Response.ErrorListener errorListener) {
        super(Method.PUT, BASE_URL.copy().user(sessionToken).binary(file.getFileId()).asUrlString(), sessionToken, successListener, errorListener);
        body = file.getFileContents();
        contentType = file.getMimeType();
    }

    @Override
    protected Response<FileCreationResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new FileCreationResponse(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry());
    }

    public String getBodyContentType() {
        return contentType;
    }

    public byte[] getBody() {
        return body;
    }
}
