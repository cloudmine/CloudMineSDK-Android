package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.response.CMObjectResponse;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class ProfileLoadRequest extends CloudMineRequest<CMObjectResponse> {

    public ProfileLoadRequest(CMSessionToken sessionToken, Response.Listener<CMObjectResponse> successListener, Response.ErrorListener errorListener) {
        super(Method.GET, "/account/mine", sessionToken, successListener,  errorListener);
    }

    @Override
    protected Response<CMObjectResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new CMObjectResponse(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry());
    }
}
