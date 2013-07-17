package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMUser;
import com.cloudmine.api.rest.response.CreationResponse;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class UserCreationRequest extends CloudMineRequest<CreationResponse> {
    public static final int REQUEST_TYPE = 406;
    public static final String CREATE_URL = "/account/create";

    public UserCreationRequest(CMUser user, Response.Listener<CreationResponse> successListener, Response.ErrorListener errorListener) {
        super(Method.PUT, CREATE_URL, user.transportableRepresentation(), null, successListener, errorListener);
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
