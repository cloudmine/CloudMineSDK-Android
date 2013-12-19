package com.cloudmine.api.rest;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.LibrarySpecificClassCreator;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.CMResponse;
import me.cloudmine.annotations.Optional;

import java.util.Map;

/**
 *
 */
public class BaseChangeUserPasswordRequest extends CloudMineRequest<CMResponse> {

    public static final int REQUEST_TYPE = 411;

    private final String emailOrUsername;
    private final String oldPassword;

    public BaseChangeUserPasswordRequest(String emailOrUsername, String oldPassword, String newPassword, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.POST, "/account/password/change", "{\"password\":\"" + newPassword + "\"}", null, serverFunction, successListener, errorListener);
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
