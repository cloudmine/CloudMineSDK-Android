package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.JavaCMUser;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.CreationResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

/**
 * A Request for updating a logged in user's profile
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class BaseProfileUpdateRequest extends CloudMineRequest<CreationResponse> {
    public static final int REQUEST_TYPE = 405;

    /**
     * Create a new Request for updating a user's profile. The user must be logged in
     * @param user a logged in user
     * @param serverFunction
     * @param successListener
     * @param errorListener
     */
    @Expand
    public BaseProfileUpdateRequest(JavaCMUser user, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CreationResponse> successListener, @Optional Response.ErrorListener errorListener) {
        this(user.profileTransportRepresentation(), user.getSessionToken(), serverFunction, successListener,  errorListener);
    }

    /**
     * Create a new Request for updating a user's profile. The user must be logged in
     * @param userProfile obtained by calling {@link com.cloudmine.api.CMUser#profileTransportRepresentation()}
     * @param sessionToken a valid session token for the user who is to be updated
     * @param serverFunction
     * @param successListener
     * @param errorListener
     */
    @Expand
    public BaseProfileUpdateRequest(String userProfile, CMSessionToken sessionToken, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CreationResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.POST, "/account", userProfile, sessionToken, serverFunction, successListener,  errorListener);
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
