package com.cloudmine.api.rest;

import com.android.cloudmine.NetworkResponse;
import com.android.cloudmine.Response;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.CMObjectResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class BaseProfileLoadRequest extends CloudMineRequest<CMObjectResponse> {
    public static final int REQUEST_TYPE = 404;

    @Expand
    public BaseProfileLoadRequest(CMSessionToken sessionToken, @Optional CMServerFunction serverFunction, Response.Listener<CMObjectResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.GET, "/account/mine", null, sessionToken, serverFunction, successListener,  errorListener);
    }

    @Override
    protected Response<CMObjectResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new CMObjectResponse(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry());
    }

    @Override
    public int getRequestType() {
        return REQUEST_TYPE;
    }
}
