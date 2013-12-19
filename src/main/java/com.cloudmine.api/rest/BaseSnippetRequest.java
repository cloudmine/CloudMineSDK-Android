package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.response.CMResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

import java.util.Map;

/**
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class BaseSnippetRequest extends CloudMineRequest<CMResponse> {
    public static final int REQUEST_TYPE = 414;

    private static final String BASE_ENDPOINT = "/run";

    private static String getSnippetUrl(String snippetName, Map<String, String> extraParams) {
        StringBuilder urlBuilder = new StringBuilder(BASE_ENDPOINT).append("/").append(CMURLBuilder.encode(snippetName));
        if(extraParams != null && !extraParams.isEmpty()) {
            String separator ="?";
            for(Map.Entry<String, String> extraParam : extraParams.entrySet()) {
                urlBuilder.append(separator).append(CMURLBuilder.encode(extraParam.getKey())).append("=").append(CMURLBuilder.encode(extraParam.getValue()));
                separator = "&";
            }
        }
        return urlBuilder.toString();
    }

    @Expand
    public BaseSnippetRequest(String snippetName, @Optional Map< String, String> extraParams, @Optional CMSessionToken sessionToken, @Optional Response.Listener<CMResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.GET, getSnippetUrl(snippetName, extraParams), null, sessionToken, null, successListener, errorListener);
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
