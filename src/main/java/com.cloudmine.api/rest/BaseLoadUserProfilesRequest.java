package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.CMObjectResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

/**
 *
 */
public class BaseLoadUserProfilesRequest extends CloudMineRequest<CMObjectResponse> {

    public static final int REQUEST_TYPE = 410;

    @Expand
    public BaseLoadUserProfilesRequest(@Optional CMServerFunction serverFunction, @Optional Response.Listener<CMObjectResponse> successListener, @Optional Response.ErrorListener errorListener){
        super(Method.GET, "/account", null, null, serverFunction, successListener, errorListener);
    }

    @Expand
    public BaseLoadUserProfilesRequest(String searchString, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMObjectResponse> successListener, @Optional Response.ErrorListener errorListener){
        super(Method.GET, "/account/search?p=" + CMURLBuilder.encode(searchString), null, null, serverFunction, successListener, errorListener);
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
