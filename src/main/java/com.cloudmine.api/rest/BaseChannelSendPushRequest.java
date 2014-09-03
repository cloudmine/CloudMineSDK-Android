package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.JavaCMPushNotification;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.CMResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

/**
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class BaseChannelSendPushRequest extends CloudMineRequest<CMResponse> {

    public static final int REQUEST_TYPE = 428;

    @Expand
    public BaseChannelSendPushRequest(JavaCMPushNotification notification, @Optional CMApiCredentials credentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMResponse> successListener, Response.ErrorListener errorListener) {
        super(Method.POST, URLStrings.PUSH, notification.transportableRepresentation(), null, credentials, serverFunction, successListener, errorListener);
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
