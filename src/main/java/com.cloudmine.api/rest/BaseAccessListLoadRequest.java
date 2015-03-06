package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.CMObjectResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

/**
 * Load ACL information for a specific, logged in user
 * <br>Copyright CloudMine, Inc. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class BaseAccessListLoadRequest extends CloudMineRequest<CMObjectResponse>{
    public static final int REQUEST_TYPE = 421;

    private static final String BASE_URL = "/user/access";

    /**
     * Create a BaseAccessListLoadRequest that will load the access lists of the user whose
     * sessionToken is passed in
     * @param sessionToken a valid sessionToken for the user whose ACL information is to be fetched
     * @param serverFunction
     * @param apiCredentials optional credentials; if unspecified, defaults from CMApiCredentials.getCredentials are used
     * @param successListener
     * @param errorListener
     */
    @Expand
    public BaseAccessListLoadRequest(CMSessionToken sessionToken, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMObjectResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.GET, BASE_URL, (String)null, sessionToken, apiCredentials, serverFunction, successListener, errorListener);
    }

    @Override
    protected Response<CMObjectResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new CMObjectResponse(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry(networkResponse));
    }

    @Override
    public int getRequestType() {
        return REQUEST_TYPE;
    }
}
