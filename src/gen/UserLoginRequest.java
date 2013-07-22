package com.cloudmine.api.rest;

public class UserLoginRequest extends com.cloudmine.api.rest.BaseUserLoginRequest {

    public UserLoginRequest(java.lang.String userIdentifier, java.lang.String password, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.LoginResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(userIdentifier, password, successListener, errorListener);
    }

    public UserLoginRequest(java.lang.String userIdentifier, java.lang.String password, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.LoginResponse> successListener) {
        super(userIdentifier, password, successListener, (com.android.volley.Response.ErrorListener)null);
    }

}