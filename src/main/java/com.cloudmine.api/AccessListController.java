package com.cloudmine.api;

import android.content.Context;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cloudmine.api.rest.BaseAccessListCreateRequest;
import com.cloudmine.api.rest.BaseAccessListLoadRequest;
import com.cloudmine.api.rest.CloudMineRequest;
import com.cloudmine.api.rest.SharedRequestQueueHolders;
import com.cloudmine.api.rest.callbacks.LoginResponseCallback;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.CreationResponse;
import com.cloudmine.api.rest.response.LoginResponse;
import org.apache.http.auth.AuthenticationException;

/**
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class AccessListController extends JavaAccessListController {

    /**
     * Load the ACLs belonging to the user who is associated with the given sessionToken
     * @param context the activity context that will be used to cancel this request
     * @param sessionToken A valid sessionToken for the user who is going to log in
     * @param successListener
     * @param errorListener
     * @return
     */
    public static CloudMineRequest load(Context context, CMSessionToken sessionToken, Response.Listener<CMObjectResponse> successListener, Response.ErrorListener errorListener) {
        BaseAccessListLoadRequest request = new BaseAccessListLoadRequest(sessionToken, null, null, successListener,  errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        return request;
    }


    /**
     * Create a new JavaAccessListController that grants no privileges and contains no users. It grants permissions to
     * objects owned by the given user
     */
    public AccessListController(JavaCMUser owner) {
        super(owner);
    }

    /**
     * Instantiate a new JavaAccessListController owned by the specified user that grants the specified permissions
     * @param owner
     * @param permissions permissions
     */
    public AccessListController(JavaCMUser owner, CMAccessPermission... permissions) {
        super(owner, permissions);
    }

    protected AccessListController() {
        super();
    }

    /**
     * WARNING: If the user associated with this ACL is not logged in, you will NOT receive the ACL create
     * request as a return value; instead, you will return {@link com.cloudmine.api.rest.CloudMineRequest#FAKE_REQUEST}
     * as the system needs to log the user in first. If you want to guarantee you will receive the actual request,
     * you should make use of the {@link #save(android.content.Context, CMSessionToken, com.android.volley.Response.Listener, com.android.volley.Response.ErrorListener)} method
     * @param context the activity context that will be used to cancel this request
     * @param successListener
     * @param errorListener
     * @return the submitted request IFF the associated user is logged in; {@link com.cloudmine.api.rest.CloudMineRequest#FAKE_REQUEST} otherwise
     */
    public CloudMineRequest save(final Context context, final Response.Listener<CreationResponse> successListener, final Response.ErrorListener errorListener) {
        JavaCMUser user = getUser();
        if(user == null) {
            errorListener.onErrorResponse(new VolleyError(new IllegalStateException("Cannot save an ACL without an owner")));
            return CloudMineRequest.FAKE_REQUEST;
        }
        CMSessionToken sessionToken = user.getSessionToken();
        if(sessionToken == null || sessionToken.getSessionToken().equals(CMSessionToken.INVALID_TOKEN)) {
            if(user instanceof BaseCMUser) {
                ((BaseCMUser)user).login(context, new Response.Listener<LoginResponse>() {
                    @Override
                    public void onResponse(LoginResponse loginResponse) {
                        save(context, loginResponse.getSessionToken(), successListener, errorListener);
                    }
                }, errorListener);
            } else {
                user.login(new LoginResponseCallback() {
                    public void onCompletion(LoginResponse loginResponse) {
                        if(loginResponse.wasSuccess()) {
                            save(context, loginResponse.getSessionToken(), successListener, errorListener);
                        } else {
                            errorListener.onErrorResponse(new VolleyError(new AuthenticationException("Unable to log user in to save ACL")));
                        }
                    }

                    public void onFailure(Throwable throwable,  String msg) {
                        errorListener.onErrorResponse(new VolleyError(throwable));
                    }
                });
            }
        } else {
            return save(context, sessionToken, successListener, errorListener);
        }
        return CloudMineRequest.FAKE_REQUEST;
    }

    /**
     * Save this ACL with the user associated with the given CMSessionToken as the owner.
     * Note: this overrides the user specified in the constructor
     * @param context the activity context that will be used to cancel this request
     * @param sessionToken a sessionToken belonging to the user who will own this ACL
     * @param successListener
     * @param errorListener
     * @return the submitted request
     */
    public CloudMineRequest save(Context context, CMSessionToken sessionToken, Response.Listener<CreationResponse> successListener, Response.ErrorListener errorListener) {
        CloudMineRequest request = new BaseAccessListCreateRequest(this, sessionToken, null, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        return request;
    }
}
