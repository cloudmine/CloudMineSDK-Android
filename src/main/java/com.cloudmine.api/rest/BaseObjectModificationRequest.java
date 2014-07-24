package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMObject;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.ObjectModificationResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;
import me.cloudmine.annotations.Single;

import java.util.Collection;

/**
 * A Request for creating or updating CloudMine objects
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class BaseObjectModificationRequest extends CloudMineRequest<ObjectModificationResponse> {
    public static final int REQUEST_TYPE = 403;
    protected static final String ENDPOINT = "/text";

    /**
     * Create or update the specified objects
     * @param objects
     * @param sessionToken if specified, the objects will be created or updated at the user level
     * @param serverFunction
     * @param successListener
     * @param errorListener
     */
    @Expand
    public BaseObjectModificationRequest(@Single Collection<CMObject> objects,  @Optional CMSessionToken sessionToken, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<ObjectModificationResponse> successListener, @Optional Response.ErrorListener errorListener) {
        this(JsonUtilities.keyedJsonCollection(objects), sessionToken, apiCredentials, serverFunction, successListener, errorListener);
    }

    /**
     * Create or update the objects specified by this transportable representation
     * @param savable the representation of the data to save
     * @param token if specified, the objects will be created or updated at the user level
     * @param serverFunction
     * @param successListener
     * @param errorListener
     */
    @Expand
    public BaseObjectModificationRequest(Transportable savable, @Optional CMSessionToken token, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<ObjectModificationResponse> successListener, @Optional Response.ErrorListener errorListener) {
        this(Method.POST, ENDPOINT, savable.transportableRepresentation(), token, apiCredentials, serverFunction, successListener, errorListener);
    }

    public BaseObjectModificationRequest(int method, String url, String body, CMSessionToken token, CMApiCredentials apiCredentials, CMServerFunction serverFunction, Response.Listener<ObjectModificationResponse> successListener, Response.ErrorListener errorListener) {
        super(method,
                token == null ?
                url :
                user(url),
                body, token, apiCredentials, serverFunction, successListener, errorListener);
    }

    @Override
    protected Response<ObjectModificationResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new ObjectModificationResponse(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry());
    }

    @Override
    public int getRequestType() {
        return REQUEST_TYPE;
    }
}
