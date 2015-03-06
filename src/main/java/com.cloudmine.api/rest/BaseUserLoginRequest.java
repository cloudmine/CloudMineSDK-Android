package com.cloudmine.api.rest;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.JavaCMUser;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.LoginResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

import java.util.Map;

/**
 * A Request for logging a user in
 * <br>
 * Copyright CloudMine, Inc. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class BaseUserLoginRequest extends CloudMineRequest<LoginResponse> {
    public static final int REQUEST_TYPE = 407;
    private static final String ENDPOINT = "/account/login";

    private final String credentials;

    /**
     * Create a new request for logging a user in
     * @param user the user to log in. Must be non null
     * @param serverFunction
     * @param successListener
     * @param errorListener
     */
    @Expand
    public BaseUserLoginRequest(JavaCMUser user,  @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, Response.Listener<LoginResponse> successListener, @Optional Response.ErrorListener errorListener) {
        this(user.getUserIdentifier(), user.getPassword(),apiCredentials, serverFunction, successListener, errorListener);
    }
    /**
     * Create a new request for logging a user in
     * @param userIdentifier either a username or email address of a registered user
     * @param password the user's password
     * @param serverFunction
     * @param successListener
     * @param errorListener
     */
    @Expand
    public BaseUserLoginRequest(String userIdentifier, String password, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, Response.Listener<LoginResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.POST, ENDPOINT, null, null, apiCredentials, serverFunction, successListener, errorListener);
        credentials = "Basic " + JavaCMUser.encode(userIdentifier, password);
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
