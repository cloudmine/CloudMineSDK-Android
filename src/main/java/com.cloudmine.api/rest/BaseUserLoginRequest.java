package com.cloudmine.api.rest;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMUser;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.LoginResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

import java.util.Map;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class BaseUserLoginRequest extends CloudMineRequest<LoginResponse> {
    public static final int REQUEST_TYPE = 407;
    private static final String ENDPOINT = "/account/login";

    private final String credentials;

    @Expand
    public BaseUserLoginRequest(String userIdentifier, String password, @Optional CMServerFunction serverFunction, Response.Listener<LoginResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.POST, ENDPOINT, null, null, serverFunction, successListener, errorListener);
        credentials = "Basic " + CMUser.encode(userIdentifier, password);
    }

    @Override
    protected Response<LoginResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new LoginResponse(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry());
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();
        headers.put(CMWebService.AUTHORIZATION_KEY, credentials);
        return headers;
    }

    @Override
    public int getRequestType() {
        return REQUEST_TYPE;
    }
}
