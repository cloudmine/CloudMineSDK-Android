package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMFile;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.FileCreationResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class BaseFileCreationRequest extends CloudMineRequest<FileCreationResponse> {

    public static final int REQUEST_TYPE = 400;
    static final String BASE_ENDPOINT = "";
    static final CMURLBuilder BASE_URL = new CMURLBuilder(BASE_ENDPOINT, true);

    private final byte[] body;
    private final String contentType;

    @Expand
    public BaseFileCreationRequest(CMFile file, @Optional CMSessionToken sessionToken, @Optional CMServerFunction serverFunction, @Optional Response.Listener<FileCreationResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.PUT, BASE_URL.copy().user(sessionToken).binary(file.getFileId()), sessionToken, serverFunction, successListener, errorListener);
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

    @Override
    public int getRequestType() {
        return REQUEST_TYPE;
    }

    public byte[] getBody() {
        return body;
    }
}
