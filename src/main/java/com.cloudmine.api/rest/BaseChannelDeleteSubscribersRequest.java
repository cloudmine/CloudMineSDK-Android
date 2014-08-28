package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.BaseCMChannel;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.PushChannelResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

import java.util.Collection;

/**
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class BaseChannelDeleteSubscribersRequest extends CloudMineRequest<PushChannelResponse> {
    public static final int REQUEST_TYPE = 427;
    private static final String idsUrl(Collection<String> ids) {
        if(ids == null || ids.isEmpty()) return "";
        StringBuilder listBuilder = new StringBuilder("ids=");
        String comma = "";
        for(String id : ids) {
            listBuilder.append(comma).append(id);
            comma = ",";
        }
        return listBuilder.toString();
    }

    private static String typeUrl(BaseCMChannel.SubscriberType type) {
        return type == BaseCMChannel.SubscriberType.DEVICE_ID ? "device_ids" : "user_ids";
    }

    @Expand
    public BaseChannelDeleteSubscribersRequest(String channel, BaseCMChannel.SubscriberType type, Collection<String> ids, @Optional CMApiCredentials credentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PushChannelResponse> successListener, Response.ErrorListener errorListener) {
        super(Method.DELETE, "/push/channel/" + channel + "/" + typeUrl(type) + "?" + idsUrl(ids), null, null, credentials, serverFunction, successListener, errorListener);
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
