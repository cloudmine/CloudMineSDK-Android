package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.CMObjectResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;
import me.cloudmine.annotations.Single;

import java.util.Collection;

/**
 * A Request for loading CloudMine objects
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class BaseObjectLoadRequest extends CloudMineRequest<CMObjectResponse> {

    public static final int REQUEST_TYPE = 402;
    static final String BASE_ENDPOINT = "/text";
    static final CMURLBuilder BASE_URL = new CMURLBuilder(BASE_ENDPOINT, true);


    public BaseObjectLoadRequest(@Single @Optional Collection<String> objectIds, @Optional CMSessionToken sessionToken, @Optional CMServerFunction serverFunction, Response.Listener<CMObjectResponse> successListener, @Optional Response.ErrorListener errorListener) {
        this(objectIds, sessionToken, CMApiCredentials.getCredentials(), serverFunction, successListener, errorListener);
    }
    /**
     * Create a new BaseObjectLoadRequest that loads the objects specified by the ids
     * @param objectIds The objectIds to load. If null, all objects will be loaded
     * @param sessionToken an optional sessionToken. If present, the objects will be loaded at the user level
     * @param serverFunction
     * @param successListener
     * @param errorListener
     */
    @Expand
    public BaseObjectLoadRequest(@Single @Optional Collection<String> objectIds, @Optional CMSessionToken sessionToken, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, Response.Listener<CMObjectResponse> successListener, @Optional Response.ErrorListener errorListener) {
        this(BASE_URL.copy().objectIds(objectIds), sessionToken, apiCredentials, serverFunction, successListener, errorListener);
    }

    BaseObjectLoadRequest(CMURLBuilder url, CMSessionToken sessionToken, CMApiCredentials apiCredentials, CMServerFunction serverFunction, Response.Listener<CMObjectResponse> successListener, Response.ErrorListener errorListener) {
        super(Method.GET, url.user(sessionToken).serverFunction(serverFunction).asUrlString(), null, sessionToken, apiCredentials,successListener, errorListener);
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
