package com.cloudmine.api.rest;

public class ProfileLoadRequest extends com.cloudmine.api.rest.BaseProfileLoadRequest {

    public ProfileLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(sessionToken, successListener, errorListener);
    }

    public ProfileLoadRequest(com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CMObjectResponse> successListener) {
        super(sessionToken, successListener, (com.android.volley.Response.ErrorListener)null);
    }

}