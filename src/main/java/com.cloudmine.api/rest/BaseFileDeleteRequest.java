package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.ObjectModificationResponse;
import me.cloudmine.annotations.Optional;
import me.cloudmine.annotations.Single;

import java.util.Collection;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class BaseFileDeleteRequest extends CloudMineRequest<ObjectModificationResponse> {
    public static final int REQUEST_TYPE = 409;
    static final String BASE_ENDPOINT = "/data";
    static final CMURLBuilder BASE_URL = new CMURLBuilder(BASE_ENDPOINT, true);

    public BaseFileDeleteRequest(@Single Collection<String> fileIds, @Optional CMSessionToken sessionToken, @Optional CMServerFunction serverFunction, Response.Listener<ObjectModificationResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.DELETE, BASE_URL.copy().user(sessionToken).objectIds(fileIds), serverFunction, successListener, errorListener);
    }

    @Override
    protected Response<ObjectModificationResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new ObjectModificationResponse(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry(networkResponse));
    }

    @Override
    public int getRequestType() {
        return REQUEST_TYPE;
    }
}
