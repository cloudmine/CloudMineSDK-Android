package com.cloudmine.api.rest;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.LibrarySpecificClassCreator;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.CMResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

import java.util.Map;

/**
 * A Request for changing a user's password.
 * <br>Copyright CloudMine, Inc. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class BaseChangeUserPasswordRequest extends CloudMineRequest<CMResponse> {

    public static final int REQUEST_TYPE = 411;

    private final String emailOrUsername;
    private final String oldPassword;

    /**
     * Create a new BaseChangeUserPasswordRequest
     * @param emailOrUsername the username or password the user uses to log in
     * @param oldPassword the user's current password
     * @param newPassword the user's desired password
     * @param serverFunction
     * @param successListener
     * @param errorListener
     */
    @Expand
    public BaseChangeUserPasswordRequest(String emailOrUsername, String oldPassword, String newPassword, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.POST, "/account/password/change", "{\"password\":\"" + newPassword + "\"}", null, apiCredentials, serverFunction, successListener, errorListener);
        this.emailOrUsername = emailOrUsername;
        this.oldPassword = oldPassword;

    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headerMap = super.getHeaders();
        headerMap.put("Authorization", LibrarySpecificClassCreator.getCreator().getEncoder().encode(emailOrUsername + ":" + oldPassword));
        return headerMap;
    }

    @Override
    protected Response<CMResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new CMResponse(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry(networkResponse));
    }

    @Override
    public int getRequestType() {
        return REQUEST_TYPE;
    }
}
