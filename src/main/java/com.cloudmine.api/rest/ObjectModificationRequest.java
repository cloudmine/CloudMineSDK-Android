package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.response.ObjectModificationResponse;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class ObjectModificationRequest extends CloudMineRequest<ObjectModificationResponse> {


    public ObjectModificationRequest(int method, String url, String body, CMSessionToken token, Response.ErrorListener errorListener, Response.Listener<ObjectModificationResponse> successListener) {
        super(method, url, body, token, errorListener, successListener);

    }

    @Override
    protected Response<ObjectModificationResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new ObjectModificationResponse(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry());
    }
}
