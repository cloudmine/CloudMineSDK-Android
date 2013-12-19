package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMUser;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.CreationResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class BaseUserCreationRequest extends CloudMineRequest<CreationResponse> {
    public static final int REQUEST_TYPE = 406;
    public static final String CREATE_URL = "/account/create";

    @Expand
    public BaseUserCreationRequest(CMUser user, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CreationResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.PUT, CREATE_URL, user.transportableRepresentation(), null, serverFunction, successListener, errorListener);
    }

    @Override
    protected Response<CreationResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new CreationResponse(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry());
    }

    @Override
    public int getRequestType() {
        return REQUEST_TYPE;
    }
}
