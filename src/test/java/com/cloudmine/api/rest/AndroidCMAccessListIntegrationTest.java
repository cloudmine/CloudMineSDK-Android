package com.cloudmine.api.rest;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.cloudmine.api.AccessListController;
import com.cloudmine.api.CMAccessPermission;
import com.cloudmine.api.CMObject;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.JavaCMUser;
import com.cloudmine.api.SearchQuery;
import com.cloudmine.api.SimpleCMObject;
import com.cloudmine.api.db.BaseLocallySavableCMAccessList;
import com.cloudmine.api.db.LocallySavableCMObject;
import com.cloudmine.api.integration.CMAccessListIntegrationTest;
import com.cloudmine.api.rest.callbacks.CMObjectResponseCallback;
import com.cloudmine.api.rest.options.CMRequestOptions;
import com.cloudmine.api.rest.options.CMSharedDataOptions;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.CreationResponse;
import com.cloudmine.api.rest.response.LoginResponse;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ExtendedLocallySavableCMObject;
import com.cloudmine.test.ResponseCallbackTuple;
import com.xtremelabs.robolectric.Robolectric;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static com.cloudmine.test.AsyncTestResultsCoordinator.waitThenAssertTestResults;
import static com.cloudmine.test.ResponseCallbackTuple.*;
import static com.cloudmine.test.TestServiceCallback.testCallback;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * <br>
 * Copyright CloudMine, Inc. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
@RunWith(CloudMineTestRunner.class)
public class AndroidCMAccessListIntegrationTest extends CMAccessListIntegrationTest {


    RequestQueue queue;
    Context applicationContext;

    @Before
    public void setUp() {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        super.setUp();
        CloudMineRequest.setCachingEnabled(false);
        applicationContext = Robolectric.application.getApplicationContext();
        DeviceIdentifier.initialize(applicationContext);
        queue = SharedRequestQueueHolders.getRequestQueue(applicationContext);

    }

    @Test
    public void testSegments() {
        JavaCMUser user = loggedInUser();
        LoginResponse loginResponse = service.login(user);
        assertTrue(loginResponse.wasSuccess());

        final BaseLocallySavableCMAccessList cmAccessList = new BaseLocallySavableCMAccessList(user, CMAccessPermission.READ);
        cmAccessList.setIsPublic(false);
        cmAccessList.setLoggedIn(true);
        cmAccessList.save(applicationContext, ResponseCallbackTuple.testCallback(new Response.Listener<CreationResponse>() {
            @Override
            public void onResponse(CreationResponse response) {
                assertTrue(response.wasSuccess());
            }
        }), defaultFailureListener);
        waitThenAssertTestResults();

        ExtendedLocallySavableCMObject object = new ExtendedLocallySavableCMObject("euth", false, 3);
        queue.add(new ObjectModificationRequest(object, user.getSessionToken(), wasCreated(object.getObjectId()), defaultFailureListener));
        waitThenAssertTestResults();

        JavaCMUser anotherUser = randomLoggedInUser();

        LocallySavableCMObject.loadObject(applicationContext, object.getObjectId(), anotherUser.getSessionToken(), ResponseCallbackTuple.testCallback(new Response.Listener<CMObjectResponse>() {
            @Override
            public void onResponse(CMObjectResponse response) {
                assertTrue(response.wasSuccess());
                assertEquals(0, response.getObjects().size());
            }
        }), defaultFailureListener);
        waitThenAssertTestResults();

        object.grantAccess(cmAccessList);
        queue.add(new ObjectModificationRequest(object, user.getSessionToken(), wasCreatedOrUpdated(object.getObjectId()), defaultFailureListener));
        waitThenAssertTestResults();
        queue.add(new ObjectLoadRequestBuilder(user.getSessionToken(), wasLoaded(object), defaultFailureListener).search(SearchQuery.filter(ExtendedLocallySavableCMObject.class).searchQuery()).getShared().build());

        waitThenAssertTestResults();
    }

    @Test
    public void testCreateRequest() {
        final JavaCMUser anotherUser = createOtherUser();
        final JavaCMUser owner = createOtherUser();

        AccessListController list = getCmAccessList(anotherUser, owner);
        list.save(Robolectric.application, ResponseCallbackTuple.testCallback(new Response.Listener<CreationResponse>() {
            @Override
            public void onResponse(CreationResponse response) {
                assertTrue(response.wasSuccess());
            }
        }), defaultFailureListener);
        waitThenAssertTestResults();
        final SimpleCMObject anObject = insertAnObject(owner, list);
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

        final AccessListController list = getCmAccessList(anotherUser, user);

        BaseAccessListCreateRequest request = new BaseAccessListCreateRequest(list, user.getSessionToken(), null, ResponseCallbackTuple.testCallback(new Response.Listener<CreationResponse>() {
            @Override
            public void onResponse(CreationResponse response) {
                assertTrue(response.wasSuccess());
            }
        }), defaultFailureListener);
        RequestQueue requestQueue = SharedRequestQueueHolders.getRequestQueue(Robolectric.application);
        requestQueue.add(request);
        waitThenAssertTestResults();

        AccessListController.load(Robolectric.application, user.getSessionToken(), ResponseCallbackTuple.testCallback(new Response.Listener<CMObjectResponse>() {
            @Override
            public void onResponse(CMObjectResponse response) {
                Assert.assertTrue(response.wasSuccess());
                CMObject loadedList = response.getCMObject(list.getObjectId());
                assertEquals(list, loadedList);
            }
        }), defaultFailureListener);

        waitThenAssertTestResults();
    }


    protected AccessListController getCmAccessList(JavaCMUser anotherUser, JavaCMUser user) {
        List<String> userObjectIds = Arrays.asList("freddy", "teddy", "george", "puddin");
        AccessListController list = new AccessListController(user, CMAccessPermission.READ, CMAccessPermission.UPDATE);
        list.grantAccessTo(userObjectIds);
        list.grantAccessTo(anotherUser);
        list.grantPermissions(CMAccessPermission.READ);
        return list;
    }
}
