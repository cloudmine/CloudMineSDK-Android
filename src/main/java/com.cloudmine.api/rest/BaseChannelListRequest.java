package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.ListOfValuesResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

import static com.cloudmine.api.rest.URLStrings.CHANNELS;
import static com.cloudmine.api.rest.URLStrings.DEVICE;

/**
 * <br>Copyright CloudMine, Inc. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class BaseChannelListRequest extends CloudMineRequest<ListOfValuesResponse<String>> {

    public static final int REQUEST_TYPE = 426;

    @Expand
    public BaseChannelListRequest(String deviceId, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<ListOfValuesResponse<String>> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.GET, DEVICE + "/" + deviceId + CHANNELS, null, null, apiCredentials, serverFunction, successListener, errorListener);
    }

    @Expand
    public BaseChannelListRequest(CMSessionToken sessionToken, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<ListOfValuesResponse<String>> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.GET, URLStrings.ACCOUNT_CHANNELS, null, sessionToken, apiCredentials, serverFunction, successListener, errorListener);
    }

    @Override
    protected Response<ListOfValuesResponse<String>> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new ListOfValuesResponse<String>(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry(networkResponse));
    }

    @Override
    public int getRequestType() {
        return REQUEST_TYPE;
    }
}
