package com.cloudmine.api;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.cloudmine.api.rest.CloudMineRequest;
import com.cloudmine.api.rest.ProfileLoadRequest;
import com.cloudmine.api.rest.ProfileUpdateRequest;
import com.cloudmine.api.rest.SharedRequestQueueHolders;
import com.cloudmine.api.rest.UserCreationRequest;
import com.cloudmine.api.rest.UserLoginRequest;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.CreationResponse;
import com.cloudmine.api.rest.response.LoginResponse;

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
        RequestQueue queue = SharedRequestQueueHolders.getRequestQueue(context);
        CloudMineRequest request = new UserCreationRequest(this, successListener,  errorListener);
        queue.add(request);
        return request;
    }



    public CloudMineRequest login(Context context, Response.Listener<LoginResponse> successListener, Response.ErrorListener errorListener) {
        return login(context, getPassword(), successListener, errorListener);
    }

    public CloudMineRequest login(Context context, String password, final Response.Listener<LoginResponse> successListener, Response.ErrorListener errorListener) {
        RequestQueue queue = SharedRequestQueueHolders.getRequestQueue(context);

        CloudMineRequest request = new UserLoginRequest(getUserIdentifier(), password, new Response.Listener<LoginResponse>() {
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

    public CloudMineRequest saveProfile(Context context, Response.Listener<CreationResponse> successListener, Response.ErrorListener errorListener) {
        RequestQueue queue = SharedRequestQueueHolders.getRequestQueue(context);
        CloudMineRequest request = new ProfileUpdateRequest(profileTransportRepresentation(), getSessionToken(), successListener, errorListener);
        queue.add(request);
        return request;
    }

    public CloudMineRequest loadProfile(Context context, Response.Listener<CMObjectResponse> successListener, Response.ErrorListener errorListener) {
        RequestQueue queue = SharedRequestQueueHolders.getRequestQueue(context);
        CloudMineRequest request = new ProfileLoadRequest(getSessionToken(), successListener, errorListener);
        queue.add(request);
        return request;
    }
}
