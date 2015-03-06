package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.JavaCMChannel;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.PushChannelResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

/**
 * <br>Copyright CloudMine, Inc. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class BaseChannelCreationRequest extends CloudMineRequest<PushChannelResponse>{
    public static final int REQUEST_TYPE = 424;

    @Expand
    public BaseChannelCreationRequest(JavaCMChannel channel, @Optional CMApiCredentials credentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PushChannelResponse> successListener, Response.ErrorListener errorListener) {
        super(Method.POST, URLStrings.PUSH_CHANNEL, channel.transportableRepresentation(), null, credentials, serverFunction, successListener, errorListener);
    }

    @Override
    protected Response<PushChannelResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new PushChannelResponse(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry(networkResponse));
    }

    @Override
    public int getRequestType() {
        return REQUEST_TYPE;
    }
}
