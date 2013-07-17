package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.callbacks.Callback;
import com.cloudmine.api.rest.response.ObjectModificationResponse;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class ObjectModificationRequest extends CloudMineRequest<ObjectModificationResponse> {
    public static final int REQUEST_TYPE = 403;
    protected static final String ENDPOINT = "/text";

    public ObjectModificationRequest(Transportable savable, final Callback<ObjectModificationResponse> callback) {
        this(savable, null, callback);
    }

    public ObjectModificationRequest(Transportable savable, CMSessionToken userSession, final Callback<ObjectModificationResponse> callback) {
        this(savable, userSession, successFromCallback(callback), errorFromCallback(callback));
    }

    public ObjectModificationRequest(Transportable savable, Response.Listener< ObjectModificationResponse > successListener) {
        this(savable, successListener, null);
    }

    public ObjectModificationRequest(Transportable savable, Response.Listener< ObjectModificationResponse > successListener, Response.ErrorListener errorListener) {
        this(savable, null, successListener, errorListener);
    }

    public ObjectModificationRequest(Transportable savable, CMSessionToken sessionToken, Response.Listener< ObjectModificationResponse > successListener) {
        this(savable, sessionToken, successListener, null);
    }

    public ObjectModificationRequest(Transportable savable, CMSessionToken token, Response.Listener< ObjectModificationResponse > successListener, Response.ErrorListener errorListener) {
        this(Method.POST, ENDPOINT, savable.transportableRepresentation(), token, successListener, errorListener);
    }

    public ObjectModificationRequest(int method, String url, String body, CMSessionToken token, Response.Listener<ObjectModificationResponse> successListener, Response.ErrorListener errorListener) {
        super(method,
                token == null ?
                url :
                user(url),
                body, token, successListener, errorListener);
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
