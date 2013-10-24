package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.ObjectModificationResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;
import me.cloudmine.annotations.Single;

import java.util.Collection;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class BaseObjectDeleteRequest extends CloudMineRequest<ObjectModificationResponse> {
    public static final int REQUEST_TYPE = 408;
    private static final CMURLBuilder BASE_URL = new CMURLBuilder("/data", true);

    @Expand
    public BaseObjectDeleteRequest(@Single Collection<String> objectIds, @Optional CMSessionToken sessionToken, @Optional CMServerFunction serverFunction, @Optional Response.Listener<ObjectModificationResponse> responseListener, @Optional Response.ErrorListener errorListener) {
        super(Method.DELETE, new CMURLBuilder("", true).delete(objectIds).user(sessionToken), sessionToken, serverFunction, responseListener, errorListener);
    }

    @Expand
    public BaseObjectDeleteRequest(boolean deleteAll, @Optional CMSessionToken sessionToken, @Optional CMServerFunction serverFunction, @Optional Response.Listener<ObjectModificationResponse> responseListener, @Optional Response.ErrorListener errorListener) {
        super(Method.DELETE,
                deleteAll ? BASE_URL.copy().deleteAll().user(sessionToken) : BASE_URL.copy().user(sessionToken),
                sessionToken, serverFunction, responseListener, errorListener);
    }

    @Override
    protected Response<ObjectModificationResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new ObjectModificationResponse(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry());
    }

    @Override
    public int getRequestType() {
        return REQUEST_TYPE;
    }
}
