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
 * A Request for invalidating a session token. Note that if a user has multiple valid
 * session tokens, invalidating a single token will not invalidate
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class BaseUserLogoutRequest extends CloudMineRequest<CMResponse> {
    public static final int REQUEST_TYPE =  413;

    /**
     * Create a BaseUserLogoutRequest which will invalidate the specified sessionToken
     * @param sessionToken a String representation of a sessionToken, obtained by calling {@link com.cloudmine.api.CMSessionToken#getSessionToken()}
     * @param serverFunction
     * @param successListener
     * @param errorListener
     */
    @Expand
    public BaseUserLogoutRequest(String sessionToken, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMResponse> successListener, @Optional Response.ErrorListener errorListener) {
        this(new CMSessionToken(sessionToken, new Date()), serverFunction, successListener, errorListener);
    }

    /**
     * Create a BaseUserLogoutRequest which will invalidate the specified sessionToken
     * @param sessionToken
     * @param serverFunction
     * @param successListener
     * @param errorListener
     */
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
