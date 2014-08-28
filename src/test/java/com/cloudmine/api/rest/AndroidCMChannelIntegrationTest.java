package com.cloudmine.api.rest;

import android.content.Context;
import com.android.volley.Response;
import com.cloudmine.EnvironmentVariables;
import com.cloudmine.api.BaseCMChannel;
import com.cloudmine.api.BaseCMUser;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMChannel;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.JavaCMChannel;
import com.cloudmine.api.JavaCMUser;
import com.cloudmine.api.UserRepresentation;
import com.cloudmine.api.integration.CMChannelIntegrationTest;
import com.cloudmine.api.rest.callbacks.CMResponseCallback;
import com.cloudmine.api.rest.callbacks.ListOfStringsCallback;
import com.cloudmine.api.rest.callbacks.PushChannelResponseCallback;
import com.cloudmine.api.rest.response.CMResponse;
import com.cloudmine.api.rest.response.ListOfValuesResponse;
import com.cloudmine.api.rest.response.LoginResponse;
import com.cloudmine.api.rest.response.PushChannelResponse;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ResponseCallbackTuple;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static com.cloudmine.test.AsyncTestResultsCoordinator.waitThenAssertTestResults;
import static com.cloudmine.test.ResponseCallbackTuple.defaultFailureListener;
import static com.cloudmine.test.ResponseCallbackTuple.testCallback;
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

    private Context applicationContext;

    @Before
    public void setUp() {
        applicationContext = Robolectric.application.getApplicationContext();

        CMApiCredentials.initialize(EnvironmentVariables.getCredentials().getIdentifier(), EnvironmentVariables.getCredentials().getApiKey(), applicationContext);
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
        channel.create(applicationContext, null, null, testCallback(new Response.Listener<PushChannelResponse>() {
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

    @Test
    public void testSubscribeDevices() {
        final String name = randomString();
        BaseCMChannel channel = new CMChannel(name);
        channel.create(applicationContext, null,null,ResponseCallbackTuple.<PushChannelResponse>hasSuccess(), defaultFailureListener);
        waitThenAssertTestResults();

        BaseCMChannel.subscribeDeviceId(applicationContext, name, null, null, testCallback(new Response.Listener<PushChannelResponse>() {
            @Override
            public void onResponse(PushChannelResponse pushChannelResponse) {
                assertTrue(pushChannelResponse.wasSuccess());
                assertTrue(pushChannelResponse.getDeviceIds().contains(DeviceIdentifier.getUniqueId()));
            }
        }), defaultFailureListener);
        waitThenAssertTestResults();

        BaseCMChannel.subscribeDeviceIds(applicationContext, name, Arrays.asList("a", "b", "c"), null, null,  testCallback(new Response.Listener<PushChannelResponse>() {
            @Override
            public void onResponse(PushChannelResponse pushChannelResponse) {
                assertTrue(pushChannelResponse.wasSuccess());
                assertTrue(pushChannelResponse.getDeviceIds().contains("a"));
                assertTrue(pushChannelResponse.getDeviceIds().contains("b"));
                assertTrue(pushChannelResponse.getDeviceIds().contains("c"));
            }
        }), defaultFailureListener);
        waitThenAssertTestResults();
    }

    @Test
    public void testSubscribeUsers() {

        final String name = randomString();
        BaseCMChannel channel = new CMChannel(name);
        channel.create(applicationContext, null,null,ResponseCallbackTuple.<PushChannelResponse>hasSuccess(), defaultFailureListener);
        waitThenAssertTestResults();


        final BaseCMUser user = new BaseCMUser(randomEmail(),randomString());
        service.insert(user);
        LoginResponse response = service.login(user);
        user.setSessionToken(response.getSessionToken());
        user.subscribeToChannel(applicationContext, name, true, null, null, testCallback(new Response.Listener<PushChannelResponse>() {
            @Override
            public void onResponse(PushChannelResponse pushChannelResponse) {
                assertTrue(pushChannelResponse.wasSuccess());
            }
        }), defaultFailureListener);
        waitThenAssertTestResults();

        final JavaCMUser[] users = {randomLoggedInUser(), randomLoggedInUser(), randomLoggedInUser()};

        BaseCMChannel.subscribeUsers(applicationContext, name, Arrays.asList(UserRepresentation.emailRepresentation(users[0].getEmail()),
                UserRepresentation.usernameRepresentation(users[1].getUserName()),
                UserRepresentation.useridRepresentation(users[2].getObjectId())),
                null, null, testCallback(new Response.Listener<PushChannelResponse>() {

            @Override
            public void onResponse(PushChannelResponse pushChannelResponse) {
                assertTrue(pushChannelResponse.wasSuccess());
                for (JavaCMUser user : users) {
                    assertTrue(pushChannelResponse.getUserIds().contains(user.getObjectId()));
                }
            }
        }), defaultFailureListener);
        waitThenAssertTestResults();


    }
    
    @Test
    public void testGetChannels() {

        final String name = randomString();
        BaseCMChannel channel = new CMChannel(name);
        JavaCMUser user = randomLoggedInUser();
        final String deviceId = randomString();
        channel.addUser(user);
        channel.addDeviceId(deviceId);
        channel.create(applicationContext, null,null,ResponseCallbackTuple.<PushChannelResponse>hasSuccess(), defaultFailureListener);
        waitThenAssertTestResults();

        BaseCMChannel.loadChannelNamesByDeviceId(applicationContext, deviceId, null, null, testCallback(new Response.Listener<ListOfValuesResponse<String>>() {
            @Override
            public void onResponse(ListOfValuesResponse<String> stringListOfValuesResponse) {
                assertTrue(stringListOfValuesResponse.getValues().contains(name));
            }
        }), defaultFailureListener);
        waitThenAssertTestResults();

        BaseCMChannel.loadChannelNamesBySessionToken(applicationContext, user.getSessionToken(), null, null,  testCallback(new Response.Listener<ListOfValuesResponse<String>>() {
            @Override
            public void onResponse(ListOfValuesResponse<String> stringListOfValuesResponse) {
                assertTrue(stringListOfValuesResponse.getValues().contains(name));
            }
        }), defaultFailureListener);

    }

    @Test
    public void testBulkUnsubscribe() {

        final String name = randomString();
        BaseCMChannel channel = new CMChannel(name);
        final JavaCMUser[] users = {randomLoggedInUser(), randomLoggedInUser(), randomLoggedInUser()};
        final String[] deviceIds = {randomString(), randomString(), randomString()};
        for(JavaCMUser user : users) {
            channel.addUser(user);
        }
        for(String deviceId : deviceIds) {
            channel.addDeviceId(deviceId);
        }
        channel.create(applicationContext, null,null,ResponseCallbackTuple.<PushChannelResponse>hasSuccess(), defaultFailureListener);
        waitThenAssertTestResults();

        BaseCMChannel.unsubscribeDeviceIds(applicationContext, name, Arrays.asList(deviceIds[0], deviceIds[2]), null, null, testCallback(new Response.Listener<PushChannelResponse>() {
            @Override
            public void onResponse(PushChannelResponse pushChannelResponse) {
                assertEquals(name, pushChannelResponse.getChannelName());
                List<String> remainingDeviceIds = pushChannelResponse.getDeviceIds();
                assertTrue(remainingDeviceIds.contains(deviceIds[1]));
                assertEquals(1, remainingDeviceIds.size());
            }
        }), defaultFailureListener);
        waitThenAssertTestResults();

        BaseCMChannel.unsubscribeUsersById(applicationContext, name, Arrays.asList(users[1].getObjectId(), users[2].getObjectId()), null, null, testCallback(new Response.Listener<PushChannelResponse>() {
            @Override
            public void onResponse(PushChannelResponse pushChannelResponse) {
                assertEquals(name, pushChannelResponse.getChannelName());
                List<String> remainingUserIds = pushChannelResponse.getUserIds();
                assertTrue(remainingUserIds.contains(users[0].getObjectId()));
                assertEquals(1, remainingUserIds.size());

            }
        }), defaultFailureListener);
        waitThenAssertTestResults();
    }

    @Test
    public void testUnsubscribeSelf() {
        final String name = randomString();
        BaseCMChannel channel = new CMChannel(name);
        BaseCMUser user = new BaseCMUser(randomEmail(),randomString());
        service.insert(user);
        LoginResponse response = service.login(user);
        user.setSessionToken(response.getSessionToken());
        final String deviceId = randomString();
        channel.addUser(user);
        channel.addDeviceId(deviceId);
        channel.create(applicationContext, null,null,ResponseCallbackTuple.<PushChannelResponse>hasSuccess(), defaultFailureListener);
        waitThenAssertTestResults();

        user.unsubscribeFromChannel();
    }
}
