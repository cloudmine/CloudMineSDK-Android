package com.cloudmine.api.rest;

public class ProfileUpdateRequest extends com.cloudmine.api.rest.BaseProfileUpdateRequest {

    public ProfileUpdateRequest(java.lang.String userProfile, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CreationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(userProfile, sessionToken, successListener, errorListener);
    }

    public ProfileUpdateRequest(java.lang.String userProfile, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.ErrorListener errorListener) {
        super(userProfile, sessionToken, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CreationResponse>)null, errorListener);
    }

    public ProfileUpdateRequest(java.lang.String userProfile, com.cloudmine.api.CMSessionToken sessionToken, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CreationResponse> successListener) {
        super(userProfile, sessionToken, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public ProfileUpdateRequest(java.lang.String userProfile, com.cloudmine.api.CMSessionToken sessionToken) {
        super(userProfile, sessionToken, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CreationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

}