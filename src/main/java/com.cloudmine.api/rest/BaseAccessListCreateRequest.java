package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMAccessList;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.CreationResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

/**
 * Create or update an AccessList. Requires a valid CMSessionToken
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class BaseAccessListCreateRequest extends CloudMineRequest<CreationResponse> {
    public static final int REQUEST_TYPE = 420;
    private static final String BASE_URL = "/user/access";

    /**
     * Create a new AccessListCreateRequest for a specific access list
     * @param accessList The access list to create
     * @param sessionToken a valid session token for the user associated with this access list
     * @param serverFunction
     * @param successListener
     * @param errorListener
     */
    @Expand
    public BaseAccessListCreateRequest(CMAccessList accessList, CMSessionToken sessionToken, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CreationResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.POST, BASE_URL, accessList.transportableRepresentation(), sessionToken, serverFunction, successListener, errorListener);
    }

    @Override
    protected Response<CreationResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new CreationResponse(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry(networkResponse));
    }

    @Override
    public int getRequestType() {
        return REQUEST_TYPE;
    }
}
