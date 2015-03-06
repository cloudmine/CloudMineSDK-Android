package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.JavaCMUser;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.CreationResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

/**
 * A Request for creating a new user
 * <br>
 * Copyright CloudMine, Inc. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class BaseUserCreationRequest extends CloudMineRequest<CreationResponse> {
    public static final int REQUEST_TYPE = 406;
    public static final String CREATE_URL = "/account/create";

    /**
     * Create a new UserCreationRequest for creating a new user
     * @param user A user with a username and/or an email set, as well as a password
     * @param serverFunction
     * @param successListener
     * @param errorListener
     */
    @Expand
    public BaseUserCreationRequest(JavaCMUser user, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CreationResponse> successListener, @Optional Response.ErrorListener errorListener) {
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
