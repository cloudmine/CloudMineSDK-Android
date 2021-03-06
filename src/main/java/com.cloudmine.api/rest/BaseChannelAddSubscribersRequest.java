package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.BaseCMChannel;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.PushChannelResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

import static com.cloudmine.api.rest.URLStrings.PUSH_CHANNEL;

/**
 * <br>Copyright CloudMine, Inc. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class BaseChannelAddSubscribersRequest extends CloudMineRequest<PushChannelResponse> {
    public static final int REQUEST_TYPE = 425;


    @Expand
    public BaseChannelAddSubscribersRequest(String channel, BaseCMChannel.SubscriberType type, String body, @Optional CMApiCredentials credentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PushChannelResponse> successListener, Response.ErrorListener errorListener) {
        super(Method.POST, PUSH_CHANNEL + "/" + channel + "/" + type.url, body, null, credentials, serverFunction, successListener, errorListener);
    }

    @Expand
    public BaseChannelAddSubscribersRequest(String channel, CMSessionToken sessionToken, boolean allDevices, @Optional CMApiCredentials credentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PushChannelResponse> successListener, Response.ErrorListener errorListener) {
        super(Method.POST, PUSH_CHANNEL + "/" + channel + "/" + URLStrings.SUBSCRIBE, allDevices ? "{\"user\":true}" : null, sessionToken, credentials, serverFunction, successListener, errorListener);
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
