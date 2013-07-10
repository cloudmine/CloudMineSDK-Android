package com.cloudmine.api.rest;

import com.android.volley.Request;
import com.android.volley.Response;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.CMUser;
import com.cloudmine.api.exceptions.CreationException;
import com.cloudmine.api.rest.response.ObjectModificationResponse;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class ObjectModificationRequestBuilder {

    public enum Operation {
        UPDATE(Request.Method.POST), REPLACE(Request.Method.PUT);
        private int requestMethod;
        private Operation(int requestMethod) {
            this.requestMethod = requestMethod;
        }
    }

    private Transportable transportable;
    private Operation operation;
    private CMSessionToken sessionToken;
    private Response.Listener<ObjectModificationResponse> responseListener;
    private Response.ErrorListener errorListener;

    public ObjectModificationRequestBuilder setSavable(Transportable transportable) {
        this.transportable = transportable;
        return this;
    }

    public ObjectModificationRequestBuilder saveWith(CMUser user) {
        if(user == null) return this;
        CMSessionToken token = user.getSessionToken();
        boolean isSessionTokenValid = !(token == null || CMSessionToken.FAILED.equals(token));
        if(isSessionTokenValid) {
            sessionToken = token;
        }
        return this;
    }

    public ObjectModificationRequest build() throws CreationException{
        if(transportable == null) throw new CreationException("You must set a non null Transportable in setSavable before calling build");
        int operationCode = operation == null ? Request.Method.POST : operation.requestMethod;
        String url = sessionToken == null ?
                ObjectModificationRequest.ENDPOINT :
                ObjectModificationRequest.user(ObjectModificationRequest.ENDPOINT);
        return new ObjectModificationRequest(operationCode, url, transportable.transportableRepresentation(), sessionToken, responseListener, errorListener);
    }
}
