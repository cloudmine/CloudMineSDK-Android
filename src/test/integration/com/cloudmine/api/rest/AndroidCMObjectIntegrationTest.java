package com.cloudmine.api.rest;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cloudmine.api.CMObject;
import com.cloudmine.api.CMUser;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.integration.CMObjectIntegrationTest;
import com.cloudmine.api.rest.callbacks.ObjectModificationResponseCallback;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.ObjectModificationResponse;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ExtendedCMObject;
import com.cloudmine.test.ExtendedLocallySavableCMObject;
import com.cloudmine.test.ResponseCallbackTuple;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.cloudmine.test.AsyncTestResultsCoordinator.waitThenAssertTestResults;
import static com.cloudmine.test.ResponseCallbackTuple.hasSuccess;
import static com.cloudmine.test.TestServiceCallback.testCallback;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
@RunWith(CloudMineTestRunner.class)
public class AndroidCMObjectIntegrationTest extends CMObjectIntegrationTest{

    RequestQueue queue;

    @Before
    public void setUp() {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        Context applicationContext = Robolectric.application.getApplicationContext();
        DeviceIdentifier.initialize(applicationContext);
        queue = Volley.newRequestQueue(applicationContext);
        super.setUp();
    }

    @Test
    public void testObjectModificationRequest() {
        final ExtendedLocallySavableCMObject object = new ExtendedLocallySavableCMObject("Fred", true, 55);

        queue.add(new ObjectModificationRequest(object, ResponseCallbackTuple.wasCreated(object.getObjectId()), ResponseCallbackTuple.hasSuccess));
        waitThenAssertTestResults();

        CMUser user = user();
        user.login(hasSuccess);
        waitThenAssertTestResults();

        final ExtendedLocallySavableCMObject userObject = new ExtendedLocallySavableCMObject("George", false, 1);

        ResponseCallbackTuple<ObjectModificationResponse> objectModificationResponseResponseCallbackTuple = ResponseCallbackTuple.testCallback(new Response.Listener<ObjectModificationResponse>() {
            @Override
            public void onResponse(ObjectModificationResponse modificationResponse) {
                assertTrue(modificationResponse.wasSuccess());
                assertTrue(modificationResponse.wasCreated(userObject.getObjectId()));
            }
        });
        queue.add(new ObjectModificationRequest(userObject, user.getSessionToken(), objectModificationResponseResponseCallbackTuple, ResponseCallbackTuple.hasSuccess));
        waitThenAssertTestResults();

        assertUserHasObject(userObject, user);
        assertServerHasObject(object);
    }

    @Test
    public void testObjectLoadRequest() {
        ExtendedCMObject object = new ExtendedCMObject("John", 26);
        insertAndAssert(object);

        queue.add(new ObjectLoadRequest(ResponseCallbackTuple.wasLoaded(object), ResponseCallbackTuple.hasSuccess));
        waitThenAssertTestResults();

        ExtendedCMObject fredObject = new ExtendedCMObject("Fred", 3);
        insertAndAssert(fredObject);

        queue.add(new ObjectLoadRequest(fredObject.getObjectId(), ResponseCallbackTuple.wasLoaded(fredObject), ResponseCallbackTuple.hasSuccess));
        waitThenAssertTestResults();


    }

    private void insertAndAssert(CMObject object) {
        ObjectModificationResponse insertResponse = service.insert(object.transportableRepresentation());
        assertTrue(insertResponse.wasSuccess());
    }

    private void assertServerHasObject(ExtendedLocallySavableCMObject object) {
        String objectId = object.getObjectId();
        CMObjectResponse objectLoadResponse = CMWebService.getService().loadObject(objectId);
        assertTrue(objectLoadResponse.wasSuccess());
        assertEquals(object, objectLoadResponse.getCMObject(objectId));
    }

    private void assertUserHasObject(CMObject object, CMUser user) {
        String objectId = object.getObjectId();
        CMObjectResponse objectLoadResponse = CMWebService.getService().getUserWebService(user.getSessionToken()).loadObject(objectId);
        assertTrue(objectLoadResponse.hasSuccess());
        assertEquals(object, objectLoadResponse.getCMObject(objectId));
    }
}
