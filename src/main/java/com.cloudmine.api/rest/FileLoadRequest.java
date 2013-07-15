package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMFile;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.response.FileLoadResponse;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class FileLoadRequest extends CloudMineRequest<FileLoadResponse> {

    static final String BASE_ENDPOINT = "/binary";
    static final CMURLBuilder BASE_URL = new CMURLBuilder(BASE_ENDPOINT, true);

    private final String fileName;
    private final String fileType;

    public FileLoadRequest(String fileName, String fileType, CMSessionToken sessionToken, Response.Listener<FileLoadResponse> successListener, Response.ErrorListener errorListener) {
        super(Method.GET, BASE_URL.copy().user(sessionToken).addKey(fileName).asUrlString(), sessionToken, successListener, errorListener);
        this.fileName = fileName;
        this.fileType = fileType;
    }

    @Override
    protected Response<FileLoadResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(
                new FileLoadResponse(
                        new CMFile(networkResponse.data, fileName, fileType), networkResponse.statusCode), getCacheEntry());
    }
}
