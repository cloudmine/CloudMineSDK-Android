package com.cloudmine.api.rest;

import android.content.Context;
import com.android.volley.Response;
import com.cloudmine.api.BaseCMChannel;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.JavaCMChannel;
import com.cloudmine.api.integration.CMChannelIntegrationTest;
import com.cloudmine.api.rest.callbacks.CMResponseCallback;
import com.cloudmine.api.rest.callbacks.ListOfStringsCallback;
import com.cloudmine.api.rest.callbacks.PushChannelResponseCallback;
import com.cloudmine.api.rest.response.CMResponse;
import com.cloudmine.api.rest.response.ListOfValuesResponse;
import com.cloudmine.api.rest.response.PushChannelResponse;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ResponseCallbackTuple;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static com.cloudmine.test.AsyncTestResultsCoordinator.waitThenAssertTestResults;
import static com.cloudmine.test.ResponseCallbackTuple.defaultFailureListener;
import static com.cloudmine.test.TestServiceCallback.testCallback;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
@RunWith(CloudMineTestRunner.class)
public class AndroidCMChannelIntegrationTest extends CMChannelIntegrationTest {

    private Context applicationController;

    @Before
    public void setUp() {
        applicationController = Robolectric.application.getApplicationContext();
        CMApiCredentials.initialize(APP_ID, API_KEY, applicationController);
        CloudMineRequest.setCachingEnabled(false);
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);

        super.setUp();
    }


    @Test
    public void testDeviceSubscribers() {
        JavaCMChannel channel = new JavaCMChannel();
        final String channelName = randomString();
        channel.setName(channelName);
        channel.create(hasSuccess);
        waitThenAssertTestResults();

        CMWebService.getService().asyncSubscribeThisDeviceToChannel(channelName, testCallback(new PushChannelResponseCallback() {
            public void onCompletion(PushChannelResponse response) {
                assertTrue(response.wasSuccess());
            }
        }));
        waitThenAssertTestResults();

        CMWebService.getService().asyncLoadSubscribedChannelsForDevice(DeviceIdentifier.getUniqueId(), testCallback(new ListOfStringsCallback() {
            public void onCompletion(ListOfValuesResponse<String> response) {
                assertTrue(response.getValues().contains(channelName));
            }
        }));
        waitThenAssertTestResults();

        channel.delete(testCallback(new CMResponseCallback() {
            public void onCompletion(CMResponse cmResponse) {
                assertTrue(cmResponse.wasSuccess());
            }
        }));
        waitThenAssertTestResults();
    }

    @Test
    public void testCreateChannel() {
        final String objectId = "userId";
        final String deviceId = "devId";
        final String name = randomString();
        BaseCMChannel channel = new BaseCMChannel(name, Arrays.asList(objectId), Arrays.asList(deviceId));
        channel.create(applicationController, null, null, ResponseCallbackTuple.testCallback(new Response.Listener<PushChannelResponse>() {
            @Override
            public void onResponse(PushChannelResponse response) {
                assertTrue(response.wasSuccess());
                assertEquals(name, response.getChannelName());
                assertTrue(response.getUserIds().contains(objectId));
                assertTrue(response.getDeviceIds().contains(deviceId));
            }
        }), defaultFailureListener);
        waitThenAssertTestResults();
    }
}
