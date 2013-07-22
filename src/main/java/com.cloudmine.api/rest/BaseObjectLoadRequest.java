package com.cloudmine.api.rest;

import android.os.Handler;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.response.CMObjectResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;
import me.cloudmine.annotations.Single;

import java.util.Collection;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class BaseObjectLoadRequest extends CloudMineRequest<CMObjectResponse> {

    public static final int REQUEST_TYPE = 402;
    static final String BASE_ENDPOINT = "/text";
    static final CMURLBuilder BASE_URL = new CMURLBuilder(BASE_ENDPOINT, true);

    @Expand
    public BaseObjectLoadRequest(@Single @Optional Collection<String> objectIds, @Optional CMSessionToken sessionToken, Response.Listener<CMObjectResponse> successListener, @Optional Response.ErrorListener errorListener) {
        this(BASE_URL.copy().objectIds(objectIds), sessionToken, successListener, errorListener);
    }

    @Expand
    public BaseObjectLoadRequest(@Single @Optional Collection<String> objectIds, CMSessionToken sessionToken, Handler handler) {
        this(BASE_URL.copy().objectIds(objectIds), sessionToken, null, null);
        setHandler(handler);
    }

    BaseObjectLoadRequest(CMURLBuilder url, CMSessionToken sessionToken, Response.Listener<CMObjectResponse> successListener, Response.ErrorListener errorListener) {
        super(Method.GET, url.user(sessionToken).asUrlString(), sessionToken, successListener, errorListener);
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
