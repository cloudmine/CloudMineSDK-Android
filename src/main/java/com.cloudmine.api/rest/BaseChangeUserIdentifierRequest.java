package com.cloudmine.api.rest;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.LibrarySpecificClassCreator;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.CMResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

import java.util.Map;

/**
 * A Request for changing a user's identifier - their e-mail or username that they use to
 * log in. Changing a user identifier invalidates all old sessions.
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class BaseChangeUserIdentifierRequest extends CloudMineRequest<CMResponse> {

    public static final int REQUEST_TYPE = 412;

    /**
     * Create a new BaseChangeUserIdentifierRequest for changing a user's e-mail address
     * @param currentEmail
     * @param password
     * @param newEmail
     * @param serverFunction
     * @param successListener
     * @param errorListener
     * @return
     */
    @Expand(isStatic = true)
    public static CloudMineRequest changeUserEmail(String currentEmail, String password, String newEmail, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMResponse> successListener, @Optional Response.ErrorListener errorListener){
        return new BaseChangeUserIdentifierRequest(currentEmail, password, newEmail, null, serverFunction, successListener, errorListener);
    }

    /**
     * Create a new BaseChangeUserIdentifierRequest for changing a user's username
     * @param userName
     * @param password
     * @param newUserName
     * @param serverFunction
     * @param successListener
     * @param errorListener
     * @return
     */
    @Expand(isStatic = true)
    public static CloudMineRequest changeUserName(String userName, String password, String newUserName, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMResponse> successListener, @Optional Response.ErrorListener errorListener){
        return new BaseChangeUserIdentifierRequest(userName, password, null, newUserName, serverFunction, successListener, errorListener);
    }

    private final String userIdentifier;
    private final String password;

    /**
     * Create a new BaseChangeUserIdentifierRequest for updating a user's email or username
     * @param userIdentifier the user's current username or e-mail
     * @param password the user's current password
     * @param newEmail the new e-mail address. May be null
     * @param newUserName the new username. May be null
     * @param serverFunction
     * @param successListener
     * @param errorListener
     */
    @Expand
    public BaseChangeUserIdentifierRequest(String userIdentifier, String password, String newEmail, String newUserName, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.POST, "/account/credentials", JsonUtilities.getIdentifierBody(newEmail, newUserName), null, serverFunction, successListener, errorListener);
        this.userIdentifier = userIdentifier;
        this.password = password;
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();
        headers.put("Authorization", "Basic " + LibrarySpecificClassCreator.getCreator().getEncoder().encode(userIdentifier + ":" + password));
        return headers;
    }

    @Override
    protected Response<CMResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new CMResponse(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry(networkResponse));
    }

    @Override
    public int getRequestType() {
        return 412;
    }
}
