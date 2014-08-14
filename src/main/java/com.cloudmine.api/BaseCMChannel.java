package com.cloudmine.api;

import android.content.Context;
import com.android.volley.Response;
import com.cloudmine.api.rest.BaseChannelAddSubscribersRequest;
import com.cloudmine.api.rest.BaseChannelCreationRequest;
import com.cloudmine.api.rest.CloudMineRequest;
import com.cloudmine.api.rest.JsonUtilities;
import com.cloudmine.api.rest.SharedRequestQueueHolders;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.PushChannelResponse;
import me.cloudmine.annotations.EmptyConstructor;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;
import me.cloudmine.annotations.Single;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
@EmptyConstructor
public class BaseCMChannel extends JavaCMChannel {

    @Expand
    public static CloudMineRequest<PushChannelResponse> subscribeUsers(Context context, String channelName, @Single Collection<UserRepresentation> users, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PushChannelResponse> successListener, @Optional Response.ErrorListener errorListener) {
        BaseChannelAddSubscribersRequest request = new BaseChannelAddSubscribersRequest(channelName, BaseChannelAddSubscribersRequest.SubscriberType.NAME, UserRepresentation.toBodyJson(users), apiCredentials, serverFunction, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        return request;
    }

    @Expand
    public static CloudMineRequest<PushChannelResponse> subscribeDeviceIds(Context context, String channelName, @Single Collection<String> deviceIds, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PushChannelResponse> successListener, @Optional Response.ErrorListener errorListener) {
        BaseChannelAddSubscribersRequest request = new BaseChannelAddSubscribersRequest(channelName, BaseChannelAddSubscribersRequest.SubscriberType.DEVICE_ID, JsonUtilities.objectToJson(deviceIds), apiCredentials, serverFunction, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        return request;
    }

    @Expand
    public static CloudMineRequest<PushChannelResponse> subscribeDeviceId(Context context, String channelName, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PushChannelResponse> successListener, @Optional Response.ErrorListener errorListener) {
        return subscribeDeviceIds(context, channelName, Arrays.asList(DeviceIdentifier.getUniqueId()), apiCredentials, serverFunction, successListener, errorListener);
    }

    public BaseCMChannel(){
        super();
    }

    @Expand
    public BaseCMChannel(String name) {
        super(name, new ArrayList<String>(), new ArrayList<String>());
    }

    /**
     *
     * @param name The name of the channel
     * @param users the userids to be added to the channel.
     * @param deviceIds the device ids to be added to the channel.
     */
    @Expand
    public BaseCMChannel(String name, List<String> users, List<String> deviceIds) {
        super(name, users, deviceIds);
    }

    public CloudMineRequest create(Context context, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PushChannelResponse> successListener, @Optional Response.ErrorListener errorListener) {
        BaseChannelCreationRequest request = new BaseChannelCreationRequest(this, apiCredentials, serverFunction, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        return request;
    }
}
