package com.cloudmine.api.rest;

public class ProfileLoadRequest extends com.cloudmine.api.rest.BaseProfileLoadRequest {

    public ProfileLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(sessionToken, serverFunction, successListener, errorListener);
    }

    public ProfileLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public ProfileLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(sessionToken, serverFunction, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ProfileLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

}