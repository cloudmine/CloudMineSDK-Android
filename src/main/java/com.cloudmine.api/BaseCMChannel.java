package com.cloudmine.api;

import android.content.Context;
import com.android.volley.Response;
import com.cloudmine.api.rest.BaseChannelAddSubscribersRequest;
import com.cloudmine.api.rest.BaseChannelCreationRequest;
import com.cloudmine.api.rest.BaseChannelDeleteSubscribersRequest;
import com.cloudmine.api.rest.BaseChannelListRequest;
import com.cloudmine.api.rest.CloudMineRequest;
import com.cloudmine.api.rest.JsonUtilities;
import com.cloudmine.api.rest.SharedRequestQueueHolders;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.ListOfValuesResponse;
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


    public enum SubscriberType {
        NAME("users"), DEVICE_ID("device_ids");

        public final String url;
        private SubscriberType(String url) {
            this.url = url;
        }
    }


    @Expand(isStatic = true)
    public static CloudMineRequest<ListOfValuesResponse<String>> loadChannelNamesByDeviceId(Context context, String deviceId, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<ListOfValuesResponse<String>> successListener, @Optional Response.ErrorListener errorListener) {
        BaseChannelListRequest request = new BaseChannelListRequest(deviceId, apiCredentials, serverFunction, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        return request;
    }

    @Expand(isStatic = true)
    public static CloudMineRequest<ListOfValuesResponse<String>> loadChannelNamesBySessionToken(Context context, CMSessionToken sessionToken, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<ListOfValuesResponse<String>> successListener, @Optional Response.ErrorListener errorListener) {
        BaseChannelListRequest request = new BaseChannelListRequest(sessionToken, apiCredentials, serverFunction, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        return request;
    }

    @Expand(isStatic = true)
    public static CloudMineRequest<PushChannelResponse> subscribeUsers(Context context, String channelName, @Single Collection<UserRepresentation> users, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PushChannelResponse> successListener, @Optional Response.ErrorListener errorListener) {
        BaseChannelAddSubscribersRequest request = new BaseChannelAddSubscribersRequest(channelName, SubscriberType.NAME, UserRepresentation.toBodyJson(users), apiCredentials, serverFunction, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        return request;
    }


    @Expand(isStatic = true)
    public static CloudMineRequest<PushChannelResponse> subscribeUsers(Context context, String channelName, UserRepresentation user, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PushChannelResponse> successListener, @Optional Response.ErrorListener errorListener) {
        return subscribeUsers(context, channelName, Arrays.asList(user), apiCredentials, serverFunction, successListener, errorListener);
    }

    @Expand(isStatic = true)
    public static CloudMineRequest<PushChannelResponse> subscribeDeviceIds(Context context, String channelName, Collection<String> deviceIds, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PushChannelResponse> successListener, @Optional Response.ErrorListener errorListener) {
        BaseChannelAddSubscribersRequest request = new BaseChannelAddSubscribersRequest(channelName, SubscriberType.DEVICE_ID, JsonUtilities.objectToJson(deviceIds), apiCredentials, serverFunction, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        return request;
    }

    @Expand(isStatic = true)
    public static CloudMineRequest<PushChannelResponse> subscribeDeviceId(Context context, String channelName, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PushChannelResponse> successListener, @Optional Response.ErrorListener errorListener) {
        return subscribeDeviceIds(context, channelName, Arrays.asList(DeviceIdentifier.getUniqueId()), apiCredentials, serverFunction, successListener, errorListener);
    }

    @Expand(isStatic = true)
    public static CloudMineRequest<PushChannelResponse> unsubscribeDeviceIds(Context context, String channelName, Collection<String> deviceIds, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PushChannelResponse> successListener, @Optional Response.ErrorListener errorListener) {
        BaseChannelDeleteSubscribersRequest request = new BaseChannelDeleteSubscribersRequest(channelName, SubscriberType.DEVICE_ID, deviceIds,  apiCredentials, serverFunction, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        return request;
    }

    @Expand(isStatic = true)
    public static CloudMineRequest<PushChannelResponse> unsubscribeDeviceId(Context context, String channelName, String deviceId, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PushChannelResponse> successListener, @Optional Response.ErrorListener errorListener) {
        return unsubscribeDeviceIds(context, channelName, Arrays.asList(deviceId), apiCredentials, serverFunction, successListener, errorListener);
    }

        @Expand
    public static CloudMineRequest<PushChannelResponse> unsubscribeCurrentDevice(Context context, String channelName,  @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PushChannelResponse> successListener, @Optional Response.ErrorListener errorListener) {
        return unsubscribeDeviceIds(context, channelName, Arrays.asList(DeviceIdentifier.getUniqueId()), apiCredentials, serverFunction, successListener, errorListener);
    }

    @Expand(isStatic = true)
    public static CloudMineRequest<PushChannelResponse> unsubscribeUsersById(Context context, String channelName, Collection<String> userIds, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PushChannelResponse> successListener, @Optional Response.ErrorListener errorListener) {
        BaseChannelDeleteSubscribersRequest request = new BaseChannelDeleteSubscribersRequest(channelName, SubscriberType.NAME, userIds,  apiCredentials, serverFunction, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        return request;
    }

    @Expand(isStatic = true)
    public static CloudMineRequest<PushChannelResponse> unsubscribeUserById(Context context, String channelName, String userId, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PushChannelResponse> successListener, @Optional Response.ErrorListener errorListener) {
        return unsubscribeUsersById(context, channelName, Arrays.asList(userId), apiCredentials, serverFunction, successListener, errorListener);
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
