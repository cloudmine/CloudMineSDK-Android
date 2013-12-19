package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.CMResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

import java.util.Date;

/**
 *
 */
public class BaseUserLogoutRequest extends CloudMineRequest<CMResponse> {
    public static final int REQUEST_TYPE =  413;

    @Expand
    public BaseUserLogoutRequest(String sessionToken, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMResponse> successListener, @Optional Response.ErrorListener errorListener) {
        this(new CMSessionToken(sessionToken, new Date()), serverFunction, successListener, errorListener);
    }

    @Expand
    public BaseUserLogoutRequest(CMSessionToken sessionToken, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.POST, "/account/logout", (String)null, sessionToken, serverFunction, successListener, errorListener);
    }

    @Override
    protected Response<CMResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new CMResponse(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry(networkResponse));
    }

    @Override
    public int getRequestType() {
        return REQUEST_TYPE;
    }
}
