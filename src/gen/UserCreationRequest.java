package com.cloudmine.api.rest;

public class UserCreationRequest extends com.cloudmine.api.rest.BaseUserCreationRequest {

    public UserCreationRequest(com.cloudmine.api.CMUser user, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CreationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(user, serverFunction, successListener, errorListener);
    }

    public UserCreationRequest(com.cloudmine.api.CMUser user, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CreationResponse> successListener, com.android.volley.Response.ErrorListener errorListener) {
        super(user, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, errorListener);
    }

    public UserCreationRequest(com.cloudmine.api.CMUser user, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.ErrorListener errorListener) {
        super(user, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CreationResponse>)null, errorListener);
    }

    public UserCreationRequest(com.cloudmine.api.CMUser user, com.android.volley.Response.ErrorListener errorListener) {
        super(user, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CreationResponse>)null, errorListener);
    }

    public UserCreationRequest(com.cloudmine.api.CMUser user, com.cloudmine.api.rest.options.CMServerFunction serverFunction, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CreationResponse> successListener) {
        super(user, serverFunction, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public UserCreationRequest(com.cloudmine.api.CMUser user, com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CreationResponse> successListener) {
        super(user, (com.cloudmine.api.rest.options.CMServerFunction)null, successListener, (com.android.volley.Response.ErrorListener)null);
    }

    public UserCreationRequest(com.cloudmine.api.CMUser user, com.cloudmine.api.rest.options.CMServerFunction serverFunction) {
        super(user, serverFunction, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CreationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

    public UserCreationRequest(com.cloudmine.api.CMUser user) {
        super(user, (com.cloudmine.api.rest.options.CMServerFunction)null, (com.android.volley.Response.Listener<com.cloudmine.api.rest.response.CreationResponse>)null, (com.android.volley.Response.ErrorListener)null);
    }

}