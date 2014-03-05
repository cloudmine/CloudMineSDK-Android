package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMFile;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.FileLoadResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

import java.util.Map;

/**
 * A Request for loading a CMFile, based on its id
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class BaseFileLoadRequest extends CloudMineRequest<FileLoadResponse> {
    public static final int REQUEST_TYPE = 401;
    static final String BASE_ENDPOINT = "/binary";
    static final CMURLBuilder BASE_URL = new CMURLBuilder(BASE_ENDPOINT, true);

    private final String fileId;

    /**
     * Create a new BaseFileLoadRequest for loading a file based on its id
     * @param fileId the id of the file to load
     * @param sessionToken optional; if specified, it is assumed the file is user level
     * @param serverFunction
     * @param successListener
     * @param errorListener
     */
    @Expand
    public BaseFileLoadRequest(String fileId, @Optional CMSessionToken sessionToken, @Optional CMServerFunction serverFunction, Response.Listener<FileLoadResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.GET, BASE_URL.copy().user(sessionToken).addKey(fileId), sessionToken, serverFunction, successListener, errorListener);
        this.fileId = fileId;
    }

    @Override
    protected Response<FileLoadResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        Map<String, String> headers = networkResponse.headers;
        String fileType = (headers == null) ?
                null :
                headers.get("Content-Type");
        CMFile file = new CMFile(networkResponse.data, fileId, fileType);

        return Response.success(
                new FileLoadResponse(
                        file, networkResponse.statusCode), getCacheEntry());
    }

    @Override
    public int getRequestType() {
        return REQUEST_TYPE;
    }
}
