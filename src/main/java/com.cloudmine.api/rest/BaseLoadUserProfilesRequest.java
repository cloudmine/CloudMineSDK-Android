package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.CMObjectResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

/**
 * A Request for loading user profiles
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class BaseLoadUserProfilesRequest extends CloudMineRequest<CMObjectResponse> {

    public static final int REQUEST_TYPE = 410;

    /**
     * Create a new Request for loading all user profiles
     * @param apiCredentials nullable API credentials to use instead of the default
     * @param serverFunction
     * @param successListener
     * @param errorListener
     */
    @Expand
    public BaseLoadUserProfilesRequest(@Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMObjectResponse> successListener, @Optional Response.ErrorListener errorListener){
        super(Method.GET, "/account", null, null, apiCredentials, serverFunction, successListener, errorListener);
    }

    /**
     * Create a new request for loading all user profiles that match the specified search string
     * @param searchString A search string. It is recommend that {@link com.cloudmine.api.SearchQuery} is used to construct this string
     * @param serverFunction
     * @param successListener
     * @param errorListener
     */
    @Expand
    public BaseLoadUserProfilesRequest(String searchString, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMObjectResponse> successListener, @Optional Response.ErrorListener errorListener){
        super(Method.GET, "/account/search?p=" + CMURLBuilder.encode(searchString), null, null, apiCredentials, serverFunction, successListener, errorListener);
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
