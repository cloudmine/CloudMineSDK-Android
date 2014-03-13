package com.cloudmine.api.rest;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.cloudmine.api.JavaAccessListController;
import com.cloudmine.api.CMObject;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.JavaCMUser;
import com.cloudmine.api.SimpleCMObject;
import com.cloudmine.api.integration.CMAccessListIntegrationTest;
import com.cloudmine.api.rest.callbacks.CMObjectResponseCallback;
import com.cloudmine.api.rest.options.CMRequestOptions;
import com.cloudmine.api.rest.options.CMSharedDataOptions;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.CreationResponse;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ResponseCallbackTuple;
import com.xtremelabs.robolectric.Robolectric;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.cloudmine.test.AsyncTestResultsCoordinator.waitThenAssertTestResults;
import static com.cloudmine.test.ResponseCallbackTuple.defaultFailureListener;
import static com.cloudmine.test.TestServiceCallback.testCallback;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
@RunWith(CloudMineTestRunner.class)
public class AndroidCMAccessListIntegrationTest extends CMAccessListIntegrationTest {


    @Before
    public void setUp() {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        DeviceIdentifier.initialize(Robolectric.application.getApplicationContext());
        super.setUp();
        CloudMineRequest.setCachingEnabled(false);
    }

    @Test
    public void testCreateRequest() {
        final JavaCMUser anotherUser = createOtherUser();
        final JavaCMUser user = createMainUser();

        JavaAccessListController list = getCmAccessList(anotherUser, user);

        BaseAccessListCreateRequest request = new BaseAccessListCreateRequest(list, user.getSessionToken(), null, ResponseCallbackTuple.testCallback(new Response.Listener<CreationResponse>() {
            @Override
            public void onResponse(CreationResponse response) {
                assertTrue(response.wasSuccess());
            }
        }), defaultFailureListener);
        SharedRequestQueueHolders.getRequestQueue(Robolectric.application).add(request);
        waitThenAssertTestResults();
        final SimpleCMObject anObject = insertAnObject(user, list);
        anotherUser.login(hasSuccess);
        waitThenAssertTestResults();
        CMSessionToken token = anotherUser.getSessionToken();

        UserCMWebService userWebService = service.getUserWebService(token);
        userWebService.asyncLoadObject(anObject.getObjectId(), testCallback(new CMObjectResponseCallback() {
            @Override
            public void onCompletion(CMObjectResponse response) {
                Assert.assertTrue(response.hasSuccess());
                assertEquals(1, response.getObjects().size());
                CMObject loadedObject = response.getCMObject(anObject.getObjectId());
                assertEquals(anObject, loadedObject);
            }
        }), CMRequestOptions.CMRequestOptions(CMSharedDataOptions.SHARED_OPTIONS));
        waitThenAssertTestResults();
    }

    @Test
    public void testLoadRequest() {
        final JavaCMUser anotherUser = createOtherUser();
        final JavaCMUser user = createMainUser();

        final JavaAccessListController list = getCmAccessList(anotherUser, user);

        BaseAccessListCreateRequest request = new BaseAccessListCreateRequest(list, user.getSessionToken(), null, ResponseCallbackTuple.testCallback(new Response.Listener<CreationResponse>() {
            @Override
            public void onResponse(CreationResponse response) {
                assertTrue(response.wasSuccess());
            }
        }), defaultFailureListener);
        RequestQueue requestQueue = SharedRequestQueueHolders.getRequestQueue(Robolectric.application);
        requestQueue.add(request);
        waitThenAssertTestResults();

        BaseAccessListLoadRequest loadRequest = new BaseAccessListLoadRequest(user.getSessionToken(), null, ResponseCallbackTuple.testCallback(new Response.Listener<CMObjectResponse>() {
            @Override
            public void onResponse(CMObjectResponse response) {
                Assert.assertTrue(response.wasSuccess());
                CMObject loadedList = response.getCMObject(list.getObjectId());
                assertEquals(list, loadedList);
            }
        }), defaultFailureListener);
        requestQueue.add(loadRequest);
        waitThenAssertTestResults();
    }
}
