package com.cloudmine.api;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.cloudmine.api.rest.BaseAddPaymentMethodRequest;
import com.cloudmine.api.rest.BaseChangeUserIdentifierRequest;
import com.cloudmine.api.rest.BaseChangeUserPasswordRequest;
import com.cloudmine.api.rest.BaseLoadPaymentMethodsRequest;
import com.cloudmine.api.rest.BaseLoadUserProfilesRequest;
import com.cloudmine.api.rest.BaseProfileLoadRequest;
import com.cloudmine.api.rest.BaseProfileUpdateRequest;
import com.cloudmine.api.rest.BaseRemovePaymentMethodRequest;
import com.cloudmine.api.rest.BaseUserCreationRequest;
import com.cloudmine.api.rest.BaseUserLoginRequest;
import com.cloudmine.api.rest.BaseUserLogoutRequest;
import com.cloudmine.api.rest.CloudMineRequest;
import com.cloudmine.api.rest.SharedRequestQueueHolders;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.CMResponse;
import com.cloudmine.api.rest.response.CreationResponse;
import com.cloudmine.api.rest.response.LoginResponse;
import com.cloudmine.api.rest.response.PaymentResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;
import me.cloudmine.annotations.Single;

import java.util.Collection;

import static com.cloudmine.api.rest.SharedRequestQueueHolders.getRequestQueue;

/**
 * Android specific BaseCMUser. Should be used over JavaCMUser unless running server side code
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class BaseCMUser extends JavaCMUser {

    /**
     * Load all of the user profiles. Requires the use of your master key
     * @param context
     * @param successListener
     * @param errorListener
     * @return
     */
    @Expand(isStatic = true)
    public static CloudMineRequest loadAllUserProfiles(Context context, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMObjectResponse> successListener, @Optional Response.ErrorListener errorListener) {
        BaseLoadUserProfilesRequest loadUserProfilesRequest = new BaseLoadUserProfilesRequest(apiCredentials, serverFunction, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(loadUserProfilesRequest);
        return loadUserProfilesRequest;
    }

    /**
     * Search for user profiles matching a given searchString
     * @param context
     * @param searchString Use {@link com.cloudmine.api.SearchQuery} to construct this search string
     * @param successListener
     * @param errorListener
     * @return
     */
    @Expand(isStatic = true)
    public static CloudMineRequest searchUserProfiles(Context context, String searchString, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMObjectResponse> successListener, @Optional Response.ErrorListener errorListener) {
        BaseLoadUserProfilesRequest loadUserProfilesRequest = new BaseLoadUserProfilesRequest(searchString, apiCredentials, serverFunction, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(loadUserProfilesRequest);
        return loadUserProfilesRequest;
    }

    /**
     * Create a new BaseCMUser with the given username and password
     * @param userName
     * @param password
     * @return
     */
    public static com.cloudmine.api.CMUser CMUserWithUserName(String userName, String password) {
        return new com.cloudmine.api.CMUser(null, userName, password);
    }

    /**
     * Create a new BaseCMUser with the given username and blank password
     * @param userName
     * @return
     */
    public static com.cloudmine.api.CMUser CMUserWithUserName(String userName) {
        return new com.cloudmine.api.CMUser(null, userName, "");
    }

    /**
     * Create a new BaseCMUser with the given email and password
     * @param email
     * @param password
     * @return
     */
    public static com.cloudmine.api.CMUser CMUserWithEmail(String email, String password) {
        return new com.cloudmine.api.CMUser(email, password);
    }

    /**
     * Create a new BaseCMUser with the given email and blank password
     * @param email
     * @return
     */
    public static com.cloudmine.api.CMUser CMUserWithEmail(String email) {
        return new com.cloudmine.api.CMUser(email, "");
    }

    @Expand
    protected BaseCMUser() {}

    @Expand
    public BaseCMUser(String email, String userName, String password) {
        super(email, userName, password);
    }

    @Expand
    public BaseCMUser(String email, String password) {
        super(email, password);
    }

    /**
     * Create a user asynchronously
     * @param context
     * @param successListener
     * @param errorListener
     * @return
     */
    @Expand
    public CloudMineRequest create(Context context, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CreationResponse> successListener, @Optional Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        CloudMineRequest request = new BaseUserCreationRequest(this, null, successListener,  errorListener);
        queue.add(request);
        return request;
    }

    /**
     * Login a user asynchronously, set their session token and profile information on this object, and clears their password
     * @param context
     * @param successListener
     * @param errorListener
     * @return
     */
    @Expand
    public CloudMineRequest login(Context context, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<LoginResponse> successListener, @Optional Response.ErrorListener errorListener) {
        return login(context, getPassword(), apiCredentials, serverFunction, successListener, errorListener);
    }

    /**
     * Login a user asynchronously, set their session token and profile information on this object, and clears their password
     * @param context
     * @param password the password to use to login
     * @param successListener
     * @param errorListener
     * @return
     */
    @Expand
    public CloudMineRequest login(Context context, String password, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional final Response.Listener<LoginResponse> successListener, @Optional Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);

        CloudMineRequest request = new BaseUserLoginRequest(getUserIdentifier(), password, apiCredentials, serverFunction, new Response.Listener<LoginResponse>() {
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

    /**
     * Logs the user out, invalidating their session token. If successful, clears the session token on this object
     * @param context
     * @param successListener
     * @param errorListener
     * @return
     */
    @Expand
    public CloudMineRequest logout(Context context, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional final Response.Listener<CMResponse> successListener, @Optional Response.ErrorListener errorListener) {
        BaseUserLogoutRequest request = new BaseUserLogoutRequest(getSessionToken(), apiCredentials, serverFunction, new Response.Listener<CMResponse>() {
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

    /**
     * Save this user's profile. A user must be logged in to save their profile.
     * @param context
     * @param successListener
     * @param errorListener
     * @return
     */
    @Expand
    public CloudMineRequest saveProfile(Context context, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CreationResponse> successListener, @Optional Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        CloudMineRequest request = new BaseProfileUpdateRequest(profileTransportRepresentation(), getSessionToken(), apiCredentials, serverFunction, successListener, errorListener);
        queue.add(request);
        return request;
    }

    /**
     * Load this user's profile. A user must be logged in to load their profile
     * @param context
     * @param successListener
     * @param errorListener
     * @return
     */
    @Expand
    public CloudMineRequest loadProfile(Context context, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMObjectResponse> successListener, @Optional Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);
        CloudMineRequest request = new BaseProfileLoadRequest(getSessionToken(), apiCredentials, serverFunction, successListener, errorListener);
        queue.add(request);
        return request;
    }

    /**
     * Change this user's password
     * @param context
     * @param currentPassword
     * @param newPassword
     * @param successListener
     * @param errorListener
     * @return
     */
    @Expand
    public CloudMineRequest changePassword(Context context, String currentPassword, String newPassword,  @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMResponse> successListener, @Optional Response.ErrorListener errorListener) {
        CloudMineRequest request = new BaseChangeUserPasswordRequest(getUserIdentifier(), currentPassword, newPassword, apiCredentials, serverFunction, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        setPassword(newPassword);
        return request;
    }

    /**
     * Change this user's userName
     * @param context
     * @param newUserName
     * @param currentPassword
     * @param successListener
     * @param errorListener
     * @return
     */
    @Expand
    public CloudMineRequest changeUserName(Context context, String newUserName, String currentPassword,  @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMResponse> successListener, @Optional Response.ErrorListener errorListener) {
        CloudMineRequest request = new BaseChangeUserIdentifierRequest(getUserIdentifier(), currentPassword, null, newUserName, apiCredentials, serverFunction, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        setUserName(newUserName);
        return request;
    }


    @Expand
    public CloudMineRequest changeEmail(Context context, String newEmail, String currentPassword,  @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMResponse> successListener, @Optional Response.ErrorListener errorListener) {
        CloudMineRequest request = new BaseChangeUserIdentifierRequest(getUserIdentifier(), currentPassword, newEmail, null, apiCredentials, serverFunction, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        setEmail(newEmail);
        return request;
    }

    /**
     * See {@link com.cloudmine.api.rest.BaseRemovePaymentMethodRequest}. User must be logged in
     * @param context
     * @param index
     * @param successListener
     * @param errorListener
     * @return The request that was added to the queue
     */
    @Expand
    public CloudMineRequest removePaymentMethodAtIndex(Context context, int index,  @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PaymentResponse> successListener, @Optional Response.ErrorListener errorListener) {
        CloudMineRequest request = new BaseRemovePaymentMethodRequest(index, getSessionToken(), apiCredentials, serverFunction, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        return request;
    }

    /**
     * See {@link com.cloudmine.api.rest.BaseAddPaymentMethodRequest}. User must be logged in
     * @param context
     * @param creditCards
     * @param successListener
     * @param errorListener
     * @return The request that was added to the queue
     */
    @Expand
    public CloudMineRequest addPaymentMethod(Context context, @Single Collection<CMCreditCard> creditCards, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PaymentResponse> successListener, @Optional Response.ErrorListener errorListener) {
        CloudMineRequest request = new BaseAddPaymentMethodRequest(creditCards, getSessionToken(), apiCredentials, serverFunction, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        return request;
    }

    /**
     * See {@link com.cloudmine.api.rest.BaseLoadPaymentMethodsRequest}. User must be logged in
     * @param context
     * @param successListener
     * @param errorListener
     * @return The request that was added to the queue
     */
    @Expand
    public CloudMineRequest loadPaymentMethods(Context context,  @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PaymentResponse> successListener, @Optional Response.ErrorListener errorListener) {
        CloudMineRequest request = new BaseLoadPaymentMethodsRequest(getSessionToken(), apiCredentials, serverFunction, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        return request;
    }
}
