package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.response.CMObjectResponse;

import java.util.Collection;
import java.util.Collections;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class ObjectLoadRequest extends CloudMineRequest<CMObjectResponse> {
    public static final int REQUEST_TYPE = 402;
    static final String BASE_ENDPOINT = "/text";
    static final CMURLBuilder BASE_URL = new CMURLBuilder(BASE_ENDPOINT, true);

    public ObjectLoadRequest(Response.Listener<CMObjectResponse> successListener) {
        this(successListener, null);
    }

    public ObjectLoadRequest(Response.Listener< CMObjectResponse > successListener, Response.ErrorListener errorListener) {
        this((Collection)null, successListener, errorListener);
    }

    public ObjectLoadRequest(String objectId, Response.Listener< CMObjectResponse > successListener, Response.ErrorListener errorListener) {
        this(objectId, null, successListener, errorListener);
    }

    public ObjectLoadRequest(String objectId, CMSessionToken sessionToken, Response.Listener< CMObjectResponse > successListener, Response.ErrorListener errorListener) {
        this(Collections.singleton(objectId), sessionToken, successListener, errorListener);
    }

    public ObjectLoadRequest(Collection <String> objectIds, Response.Listener< CMObjectResponse > successListener, Response.ErrorListener errorListener) {
        this(objectIds, null, successListener, errorListener);
    }

    public ObjectLoadRequest(Collection<String> objectIds, CMSessionToken sessionToken, Response.Listener< CMObjectResponse > successListener, Response.ErrorListener errorListener) {
        this(BASE_URL.copy().objectIds(objectIds), sessionToken, successListener, errorListener);
    }

    public ObjectLoadRequest(CMSessionToken sessionToken, Response.Listener < CMObjectResponse > successListener, Response.ErrorListener errorListener) {
        this(BASE_URL.copy(), sessionToken, successListener, errorListener);
    }

    ObjectLoadRequest(CMURLBuilder url, CMSessionToken sessionToken, Response.Listener<CMObjectResponse> successListener, Response.ErrorListener errorListener) {
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
