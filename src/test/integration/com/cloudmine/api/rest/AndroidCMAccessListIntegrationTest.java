package com.cloudmine.api.rest;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.cloudmine.api.CMAccessPermission;
import com.cloudmine.api.CMUser;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.SearchQuery;
import com.cloudmine.api.db.BaseLocallySavableCMAccessList;
import com.cloudmine.api.db.BaseLocallySavableCMObject;
import com.cloudmine.api.integration.CMAccessListIntegrationTest;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.CreationResponse;
import com.cloudmine.api.rest.response.LoginResponse;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ExtendedLocallySavableCMObject;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.cloudmine.test.AsyncTestResultsCoordinator.waitThenAssertTestResults;
import static com.cloudmine.test.ResponseCallbackTuple.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
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
        CMUser user = loggedInUser();
        LoginResponse loginResponse = service.login(user);
        assertTrue(loginResponse.wasSuccess());

        final BaseLocallySavableCMAccessList cmAccessList = new BaseLocallySavableCMAccessList(user, CMAccessPermission.READ);
        cmAccessList.setIsPublic(false);
        cmAccessList.setLoggedIn(true);
        cmAccessList.save(applicationContext, testCallback(new Response.Listener<CreationResponse>() {
            @Override
            public void onResponse(CreationResponse response) {
                assertTrue(response.wasSuccess());
            }
        }), defaultFailureListener);
        waitThenAssertTestResults();

        ExtendedLocallySavableCMObject object = new ExtendedLocallySavableCMObject("euth", false, 3);
        queue.add(new BaseObjectModificationRequest(object, user.getSessionToken(), null, wasCreated(object.getObjectId()), defaultFailureListener));
        waitThenAssertTestResults();

        CMUser anotherUser = randomLoggedInUser();

        BaseLocallySavableCMObject.loadObject(applicationContext, object.getObjectId(), anotherUser.getSessionToken(), null, testCallback(new Response.Listener<CMObjectResponse>() {
            @Override
            public void onResponse(CMObjectResponse response) {
                assertTrue(response.wasSuccess());
                assertEquals(0, response.getObjects().size());
            }
        }), defaultFailureListener);
        waitThenAssertTestResults();

        object.grantAccess(cmAccessList);
        queue.add(new BaseObjectModificationRequest(object, user.getSessionToken(), null, wasCreatedOrUpdated(object.getObjectId()), defaultFailureListener));
        waitThenAssertTestResults();
        queue.add(new ObjectLoadRequestBuilder(user.getSessionToken(), wasLoaded(object), defaultFailureListener).search(SearchQuery.filter(ExtendedLocallySavableCMObject.class).searchQuery()).getShared().build());

        waitThenAssertTestResults();
    }
}
