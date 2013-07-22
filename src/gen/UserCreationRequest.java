package com.cloudmine.api.rest;

public class UserCreationRequest extends com.cloudmine.api.rest.BaseUserCreationRequest {

    public UserCreationRequest(com.cloudmine.api.CMUser user, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CreationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(user, successListener, errorListener);
    }

    public UserCreationRequest(com.cloudmine.api.CMUser user, com.android.volley.Response.ErrorListener errorListener) {
        super(user, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CreationResponse>)null, errorListener);
    }

    public UserCreationRequest(com.cloudmine.api.CMUser user, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CreationResponse> successListener) {
        super(user, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public UserCreationRequest(com.cloudmine.api.CMUser user) {
        super(user, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CreationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

}