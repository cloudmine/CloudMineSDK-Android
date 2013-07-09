package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.rest.response.CMObjectResponse;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class ObjectLoadRequest extends CloudMineRequest<CMObjectResponse> {
    public ObjectLoadRequest(Response.ErrorListener errorListener, Response.Listener<CMObjectResponse> successListener) {
        super(Method.GET, "/text", errorListener, successListener);
    }

    @Override
    protected Response<CMObjectResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new CMObjectResponse(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry());
    }
}
