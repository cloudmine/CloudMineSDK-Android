package com.cloudmine.api.rest;

import com.android.cloudmine.NetworkResponse;
import com.android.cloudmine.Response;
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

    public BaseFileDeleteRequest(@Single Collection<String> fileIds, @Optional CMSessionToken sessionToken, @Optional CMServerFunction serverFunction) {
        super(Method.DELETE, null, null, null, null);
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
