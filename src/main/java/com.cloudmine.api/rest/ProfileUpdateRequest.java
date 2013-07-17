package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.response.CreationResponse;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class ProfileUpdateRequest extends CloudMineRequest<CreationResponse> {
    public static final int REQUEST_TYPE = 405;
    public ProfileUpdateRequest(String userProfile, CMSessionToken sessionToken, Response.Listener<CreationResponse> successListener, Response.ErrorListener errorListener) {
        super(Method.POST, "/account", userProfile, sessionToken, successListener,  errorListener);
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
