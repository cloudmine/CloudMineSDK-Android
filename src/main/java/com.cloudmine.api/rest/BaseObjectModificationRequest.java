package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.callbacks.Callback;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.ObjectModificationResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class BaseObjectModificationRequest extends CloudMineRequest<ObjectModificationResponse> {
    public static final int REQUEST_TYPE = 403;
    protected static final String ENDPOINT = "/text";

    @Expand
    public BaseObjectModificationRequest(Transportable savable, @Optional CMSessionToken userSession, @Optional CMServerFunction serverFunction, final Callback<ObjectModificationResponse> callback) {
        this(savable, userSession, serverFunction, successFromCallback(callback), errorFromCallback(callback));
    }

    @Expand
    public BaseObjectModificationRequest(Transportable savable, @Optional CMSessionToken token, @Optional CMServerFunction serverFunction, @Optional Response.Listener<ObjectModificationResponse> successListener, @Optional Response.ErrorListener errorListener) {
        this(Method.POST, ENDPOINT, savable.transportableRepresentation(), token, serverFunction, successListener, errorListener);
    }

    public BaseObjectModificationRequest(int method, String url, String body, CMSessionToken token, CMServerFunction serverFunction, Response.Listener<ObjectModificationResponse> successListener, Response.ErrorListener errorListener) {
        super(method,
                token == null ?
                url :
                user(url),
                body, token, serverFunction, successListener, errorListener);
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
