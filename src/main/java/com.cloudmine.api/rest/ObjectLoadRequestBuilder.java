package com.cloudmine.api.rest;

import com.android.volley.Response;
import com.cloudmine.api.CMObject;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.response.CMObjectResponse;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class ObjectLoadRequestBuilder extends RequestBuilder<ObjectLoadRequestBuilder, ObjectLoadRequest, CMObjectResponse>{

    private CMURLBuilder urlBuilder = ObjectLoadRequest.BASE_URL.copy();

    public ObjectLoadRequestBuilder(CMSessionToken sessionToken, Response.Listener<CMObjectResponse> success) {
        this(sessionToken, success, null);
    }

    public ObjectLoadRequestBuilder(CMSessionToken sessionToken, Response.Listener<CMObjectResponse> success, Response.ErrorListener error) {
        super(sessionToken, success, error);
    }

    public ObjectLoadRequestBuilder(Response.Listener< CMObjectResponse > success) {
        this(success, null);
    }

    public ObjectLoadRequestBuilder(Response.Listener< CMObjectResponse > success, Response.ErrorListener error) {
        super(success, error);
    }

    @Override
    public ObjectLoadRequest build() {
        return new ObjectLoadRequest(urlBuilder, sessionToken, successListener, errorListener);
    }

    public ObjectLoadRequestBuilder search(String searchQuery) {
        urlBuilder.removeAction(ObjectLoadRequest.BASE_ENDPOINT);
        urlBuilder.search(searchQuery);
        return this;
    }

    public ObjectLoadRequestBuilder ofClass(Class<? extends CMObject> cls) {
        //TODO this will be easier with the search query helper
        return this;
    }
}
