package com.cloudmine.api.rest;

/**
 * <br>Copyright CloudMine, Inc. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public interface URLStrings {

    public static final String SUBSCRIBE = "/subscribe";
    public static final String UNSUBSCRIBE = "/unsubscribe";
    public static final String CHANNEL = "/channel";
    public static final String CHANNELS = "/channels";
    public static final String ACCOUNT = "/account";
    public static final String ACCOUNT_CHANNELS = ACCOUNT + CHANNELS;
    public static final String DEVICE = "/device";
    public static final String PUSH = "/push";
    public static final String PUSH_CHANNEL = PUSH + CHANNEL;

}
