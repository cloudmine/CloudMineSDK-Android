package com.cloudmine.api;

import android.content.Context;
import com.android.volley.Response;
import com.cloudmine.api.rest.BaseChannelSendPushRequest;
import com.cloudmine.api.rest.CloudMineRequest;
import com.cloudmine.api.rest.SharedRequestQueueHolders;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.CMResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

import java.util.List;

/**
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class BaseCMPushNotification extends JavaCMPushNotification {

    public BaseCMPushNotification() {
        super();
    }

    /**
     * Create a JavaCMPushNotification to be sent to a list of users or devices
     * @param message the push message
     * @param messageRecipients a List of Targets that identify users and/or devices
     */
    public BaseCMPushNotification(String message, List<Target> messageRecipients) {
        super(message, messageRecipients);
    }

    /**
     * Create a JavaCMPushNotification to be sent to a channel
     * @param message the push message
     * @param channelName the name of the channel to send the push to
     */
    public BaseCMPushNotification(String message, String channelName) {
        super(message, channelName);
    }

    /**
     * This constructor should probably be avoided, as if both the channel name and messageRecipients are specified
     * the message recipients are ignored
     * @param message
     * @param messageRecipients
     * @param channelName
     */
    public BaseCMPushNotification(String message, List<Target> messageRecipients, String channelName) {
        super(message, messageRecipients, channelName);
    }

    @Expand
    public CloudMineRequest send(Context context, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CMResponse> successListener, @Optional Response.ErrorListener errorListener) {
        CloudMineRequest request = new BaseChannelSendPushRequest(this, apiCredentials, serverFunction, successListener, errorListener);
        SharedRequestQueueHolders.getRequestQueue(context).add(request);
        return request;
    }
}
