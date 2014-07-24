package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.rest.response.CMSocialLoginResponse;

/**
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class CompleteSocialLoginRequest extends CloudMineRequest<CMSocialLoginResponse>{

    public static final int REQUEST_TYPE = 422;
    private static final String BASE_URL = "/account/social/login/status/";
    public CompleteSocialLoginRequest(String challenge, Response.Listener<CMSocialLoginResponse> successListener, Response.ErrorListener errorListener) {
        super(Method.GET, BASE_URL + challenge, null, null, successListener, errorListener);
    }

    @Override
    protected Response<CMSocialLoginResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new CMSocialLoginResponse(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry(networkResponse));
    }

    @Override
    public int getRequestType() {
        return REQUEST_TYPE;
    }
}
