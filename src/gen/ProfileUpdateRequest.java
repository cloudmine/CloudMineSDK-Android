package com.cloudmine.api.rest;

public class ProfileUpdateRequest extends com.cloudmine.api.rest.BaseProfileUpdateRequest {

    public ProfileUpdateRequest(java.lang.String userProfile, com.cloudmine.api.CMSessionToken sessionToken) {
        super(userProfile, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CreationResponse>)null, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ProfileUpdateRequest(java.lang.String userProfile, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        super(userProfile, sessionToken, serverFunction, (com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CreationResponse>)null, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ProfileUpdateRequest(java.lang.String userProfile, com.cloudmine.api.CMSessionToken sessionToken, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CreationResponse> successListener) {
        super(userProfile, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ProfileUpdateRequest(java.lang.String userProfile, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CreationResponse> successListener) {
        super(userProfile, sessionToken, serverFunction, successListener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ProfileUpdateRequest(java.lang.String userProfile, com.cloudmine.api.CMSessionToken sessionToken, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(userProfile, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CreationResponse>)null, errorListener);
    }

    public ProfileUpdateRequest(java.lang.String userProfile, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(userProfile, sessionToken, serverFunction, (com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CreationResponse>)null, errorListener);
    }

    public ProfileUpdateRequest(java.lang.String userProfile, com.cloudmine.api.CMSessionToken sessionToken, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CreationResponse> successListener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(userProfile, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public ProfileUpdateRequest(java.lang.String userProfile, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.CreationResponse> successListener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(userProfile, sessionToken, serverFunction, successListener, errorListener);
    }

}