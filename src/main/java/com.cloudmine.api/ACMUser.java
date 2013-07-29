package com.cloudmine.api;

import android.content.Context;
import android.os.Handler;
import com.android.cloudmine.RequestQueue;
import com.android.cloudmine.Response;
import com.cloudmine.api.rest.BaseProfileLoadRequest;
import com.cloudmine.api.rest.BaseProfileUpdateRequest;
import com.cloudmine.api.rest.BaseUserCreationRequest;
import com.cloudmine.api.rest.BaseUserLoginRequest;
import com.cloudmine.api.rest.CloudMineRequest;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.CreationResponse;
import com.cloudmine.api.rest.response.LoginResponse;

import static com.cloudmine.api.rest.SharedRequestQueueHolders.getRequestQueue;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class ACMUser extends CMUser {

    protected ACMUser() {}

    public ACMUser(String email, String userName, String password) {
        super(email, userName, password);
    }

    public ACMUser(String email, String password) {
        super(email, password);
    }

    public CloudMineRequest create(Context context, Response.Listener<CreationResponse> successListener, Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        CloudMineRequest request = new BaseUserCreationRequest(this, null, successListener,  errorListener);
        queue.add(request);
        return request;
    }

    public CloudMineRequest login(Context context, Response.Listener<LoginResponse> successListener, Response.ErrorListener errorListener) {
        return login(context, getPassword(), successListener, errorListener);
    }

    public CloudMineRequest login(Context context, String password, final Response.Listener<LoginResponse> successListener, Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);

        CloudMineRequest request = new BaseUserLoginRequest(getUserIdentifier(), password, null, new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {
                try {
                    setLoggedInUser(response);
                } finally {
                    successListener.onResponse(response);
                }
            }
        }, errorListener);
        queue.add(request);
        return request;
    }

    public CloudMineRequest login(Context context, String password, Handler handler) {
        BaseUserLoginRequest request = new BaseUserLoginRequest(getUserIdentifier(), password, null, null, null);
        request.setHandler(handler);
        getRequestQueue(context).add(request);
        return request;
    }

    public CloudMineRequest saveProfile(Context context, Response.Listener<CreationResponse> successListener, Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        CloudMineRequest request = new BaseProfileUpdateRequest(profileTransportRepresentation(), getSessionToken(), null, successListener, errorListener);
        queue.add(request);
        return request;
    }

    public CloudMineRequest loadProfile(Context context, Response.Listener<CMObjectResponse> successListener, Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        CloudMineRequest request = new BaseProfileLoadRequest(getSessionToken(), null, successListener, errorListener);
        queue.add(request);
        return request;
    }
}
