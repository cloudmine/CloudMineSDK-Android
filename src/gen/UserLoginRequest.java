package com.cloudmine.api.rest;

public class UserLoginRequest extends com.cloudmine.api.rest.BaseUserLoginRequest {

    public UserLoginRequest(java.lang.String userIdentifier, java.lang.String password, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.LoginResponse> successListener) {
        super(userIdentifier, password, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public UserLoginRequest(java.lang.String userIdentifier, java.lang.String password, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.LoginResponse> successListener) {
        super(userIdentifier, password, serverFunction, successListener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public UserLoginRequest(java.lang.String userIdentifier, java.lang.String password, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.LoginResponse> successListener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(userIdentifier, password, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public UserLoginRequest(java.lang.String userIdentifier, java.lang.String password, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.cloudmine.Response.Listener<com.cloudmine.api.rest.response.LoginResponse> successListener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(userIdentifier, password, serverFunction, successListener, errorListener);
    }

}