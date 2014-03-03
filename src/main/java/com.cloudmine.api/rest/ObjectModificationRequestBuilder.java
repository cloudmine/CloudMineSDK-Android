package com.cloudmine.api.rest;

import com.android.volley.Request;
import com.android.volley.Response;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.JavaCMUser;
import com.cloudmine.api.exceptions.CreationException;
import com.cloudmine.api.rest.response.ObjectModificationResponse;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class ObjectModificationRequestBuilder extends RequestBuilder<ObjectModificationRequestBuilder, BaseObjectModificationRequest, ObjectModificationResponse>{

    public enum Operation {
        UPDATE(Request.Method.POST), REPLACE(Request.Method.PUT);
        private int requestMethod;
        private Operation(int requestMethod) {
            this.requestMethod = requestMethod;
        }
    }

    private Transportable transportable;
    private Operation operation;


    public ObjectModificationRequestBuilder(Response.Listener<ObjectModificationResponse> responseListener, Response.ErrorListener errorListener) {
        super(responseListener, errorListener);
    }

    public ObjectModificationRequestBuilder setSavable(Transportable transportable) {
        this.transportable = transportable;
        return this;
    }

    public ObjectModificationRequestBuilder saveWith(JavaCMUser user) {
        if(user == null) return this;
        CMSessionToken token = user.getSessionToken();
        boolean isSessionTokenValid = !(token == null || CMSessionToken.FAILED.equals(token));
        if(isSessionTokenValid) {
            sessionToken = token;
        }
        return this;
    }

    public BaseObjectModificationRequest build() throws CreationException{
        if(transportable == null) throw new CreationException("You must set a non null Transportable in setSavable before calling build");
        int operationCode = operation == null ? Request.Method.POST : operation.requestMethod;
        String url = sessionToken == null ?
                BaseObjectModificationRequest.ENDPOINT :
                BaseObjectModificationRequest.user(BaseObjectModificationRequest.ENDPOINT);
        return new BaseObjectModificationRequest(operationCode, url, transportable.transportableRepresentation(), sessionToken, serverFunction, successListener, errorListener);
    }
}
