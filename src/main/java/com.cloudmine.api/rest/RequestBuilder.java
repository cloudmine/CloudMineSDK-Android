package com.cloudmine.api.rest;

import com.android.volley.Request;
import com.android.volley.Response;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.options.CMServerFunction;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public abstract class RequestBuilder<BUILDER_TYPE extends RequestBuilder, REQUEST_TYPE extends Request, RESPONSE_TYPE> {

    protected Response.Listener<RESPONSE_TYPE> successListener;
    protected Response.ErrorListener errorListener;
    protected CMSessionToken sessionToken;
    protected CMServerFunction serverFunction;

    public RequestBuilder() {}

    public RequestBuilder(Response.Listener<RESPONSE_TYPE> successListener, Response.ErrorListener errorListener) {
        this(null, successListener, errorListener);
    }

    public RequestBuilder(CMSessionToken sessionToken, Response.Listener<RESPONSE_TYPE> successListener, Response.ErrorListener errorListener) {
        this.successListener = successListener;
        this.errorListener = errorListener;
        this.sessionToken = sessionToken;
    }

    public BUILDER_TYPE successListener(Response.Listener<RESPONSE_TYPE> responseListener) {
        this.successListener = responseListener;
        return (BUILDER_TYPE) this;
    }

    public BUILDER_TYPE errorListener(Response.ErrorListener errorListener) {
        this.errorListener = errorListener;
        return (BUILDER_TYPE) this;
    }

    public BUILDER_TYPE sessionToken(CMSessionToken sessionToken) {
        this.sessionToken = sessionToken;
        return (BUILDER_TYPE) this;
    }


    public BUILDER_TYPE runFunction(CMServerFunction function) {
        serverFunction = function;
        return (BUILDER_TYPE) this;
    }

    public abstract REQUEST_TYPE build();
}
