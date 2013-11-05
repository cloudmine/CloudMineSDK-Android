package com.cloudmine.api.rest;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.LibrarySpecificClassCreator;
import com.cloudmine.api.Strings;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.CMResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

import java.util.Map;

/**
 *
 */
public class BaseChangeUserIdentifierRequest extends CloudMineRequest<CMResponse> {

    public static final int REQUEST_TYPE = 412;

    @Expand(isStatic = true)
    public static BaseChangeUserIdentifierRequest changeUserEmail(String userIdentifier, String password, String newEmail, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMResponse> successListener, @Optional Response.ErrorListener errorListener){
        return new BaseChangeUserIdentifierRequest(userIdentifier, password, newEmail, null, serverFunction, successListener, errorListener);
    }

    @Expand(isStatic = true)
    public static BaseChangeUserIdentifierRequest changeUserName(String userIdentifier, String password, String newUserName, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMResponse> successListener, @Optional Response.ErrorListener errorListener){
        return new BaseChangeUserIdentifierRequest(userIdentifier, password, null, newUserName, serverFunction, successListener, errorListener);
    }

    private static String getJsonBody(String newEmail, String newUserName) {
        StringBuilder jsonBuilder = new StringBuilder("{");
        String separator = "";
        if(Strings.isNotEmpty(newEmail)) {
            jsonBuilder.append("\"email\": \"").append(newEmail).append("\"");
            separator = ", ";
        }
        if(Strings.isNotEmpty(newUserName)) {
            jsonBuilder.append(separator).append("\"username\": \"").append(newUserName).append("\"");
        }
        return jsonBuilder.append("}").toString();
    }

    private final String userIdentifier;
    private final String password;

    @Expand
    public BaseChangeUserIdentifierRequest(String userIdentifier, String password, String newEmail, String newUserName, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.POST, "/account/credentials", getJsonBody(newEmail, newUserName), null, serverFunction, successListener, errorListener);
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
