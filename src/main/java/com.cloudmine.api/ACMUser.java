package com.cloudmine.api;

import android.content.Context;
import android.os.Handler;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.cloudmine.api.rest.BaseAddPaymentMethodRequest;
import com.cloudmine.api.rest.BaseChangeUserIdentifierRequest;
import com.cloudmine.api.rest.BaseChangeUserPasswordRequest;
import com.cloudmine.api.rest.BaseLoadUserProfilesRequest;
import com.cloudmine.api.rest.BaseProfileLoadRequest;
import com.cloudmine.api.rest.BaseProfileUpdateRequest;
import com.cloudmine.api.rest.BaseUserCreationRequest;
import com.cloudmine.api.rest.BaseUserLoginRequest;
import com.cloudmine.api.rest.BaseUserLogoutRequest;
import com.cloudmine.api.rest.CloudMineRequest;
import com.cloudmine.api.rest.SharedRequestQueueHolders;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.CMResponse;
import com.cloudmine.api.rest.response.CreationResponse;
import com.cloudmine.api.rest.response.LoginResponse;
import com.cloudmine.api.rest.response.PaymentResponse;

import java.util.Collection;
import java.util.Collections;

import static com.cloudmine.api.rest.SharedRequestQueueHolders.getRequestQueue;

/**
 * Android specific CMUser. Should be used over CMUser unless running server side code
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class ACMUser extends CMUser {

    public static CloudMineRequest loadAllUserProfiles(Context context, Response.Listener<CMObjectResponse> successListener, Response.ErrorListener errorListener) {
        BaseLoadUserProfilesRequest loadUserProfilesRequest = new BaseLoadUserProfilesRequest(null, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(loadUserProfilesRequest);
        return loadUserProfilesRequest;
    }

    public static CloudMineRequest searchUserProfiles(Context context, String searchString, Response.Listener<CMObjectResponse> successListener, Response.ErrorListener errorListener) {
        BaseLoadUserProfilesRequest loadUserProfilesRequest = new BaseLoadUserProfilesRequest(searchString, null, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(loadUserProfilesRequest);
        return loadUserProfilesRequest;
    }

    public static ACMUser ACMUserWithUserName(String userName, String password) {
        return new ACMUser(null, userName, password);
    }

    public static ACMUser ACMUserWithUserName(String userName) {
        return new ACMUser(null, userName, "");
    }


    public static ACMUser ACMUserWithEmail(String email, String password) {
        return new ACMUser(email, password);
    }

    public static ACMUser ACMUserWithEmail(String email) {
        return new ACMUser(email, "");
    }

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

    public CloudMineRequest logout(Context context, final Response.Listener<CMResponse> successListener, Response.ErrorListener errorListener) {
        BaseUserLogoutRequest request = new BaseUserLogoutRequest(getSessionToken(), null, new Response.Listener<CMResponse>() {
            @Override
            public void onResponse(CMResponse response) {
                try {
                    if(response.wasSuccess()) {
                        setSessionToken(null);
                    }
                }finally {
                    successListener.onResponse(response);
                }
            }
        }, errorListener);
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

    public CloudMineRequest changePassword(Context context, String currentPassword, String newPassword, Response.Listener<CMResponse> successListener, Response.ErrorListener errorListener) {
        CloudMineRequest request = new BaseChangeUserPasswordRequest(getUserIdentifier(), currentPassword, newPassword, null, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        setPassword(newPassword);
        return request;
    }

    public CloudMineRequest changeUserName(Context context, String newUserName, String currentPassword, Response.Listener<CMResponse> successListener, Response.ErrorListener errorListener) {
        CloudMineRequest request = new BaseChangeUserIdentifierRequest(getUserIdentifier(), currentPassword, null, newUserName, null, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        setUserName(newUserName);
        return request;
    }


    public CloudMineRequest changeEmail(Context context, String newEmail, String currentPassword, Response.Listener<CMResponse> successListener, Response.ErrorListener errorListener) {
        CloudMineRequest request = new BaseChangeUserIdentifierRequest(getUserIdentifier(), currentPassword, newEmail, null, null, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        setEmail(newEmail);
        return request;
    }

    public CloudMineRequest addPaymentMethod(Context context, CMCreditCard creditCard, Response.Listener<PaymentResponse> successListener, Response.ErrorListener errorListener) {
        return addPaymentMethod(context, Collections.singleton(creditCard), successListener, errorListener);
    }

    public CloudMineRequest addPaymentMethod(Context context, Collection<CMCreditCard> creditCards, Response.Listener<PaymentResponse> successListener, Response.ErrorListener errorListener) {
        CloudMineRequest request = new BaseAddPaymentMethodRequest(creditCards, getSessionToken(), null, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        return request;
    }
}
