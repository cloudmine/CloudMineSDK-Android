package com.cloudmine.api.rest;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cloudmine.api.CMObject;
import com.cloudmine.api.CMUser;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.integration.CMObjectIntegrationTest;
import com.cloudmine.api.persistance.ClassNameRegistry;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.ObjectModificationResponse;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ExtendedCMObject;
import com.cloudmine.test.ExtendedLocallySavableCMObject;
import com.cloudmine.test.ResponseCallbackTuple;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static com.cloudmine.test.AsyncTestResultsCoordinator.waitThenAssertTestResults;
import static com.cloudmine.test.ResponseCallbackTuple.defaultFailureListener;
import static com.cloudmine.test.ResponseCallbackTuple.wasLoaded;
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
    Context applicationContext;

    @Before
    public void setUp() {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        applicationContext = Robolectric.application.getApplicationContext();
        DeviceIdentifier.initialize(applicationContext);
        queue = Volley.newRequestQueue(applicationContext);
        super.setUp();
    }

    public void testCMObjectSaving() {
        ExtendedLocallySavableCMObject object = new ExtendedLocallySavableCMObject("John", true, 55);
        object.save(applicationContext, ResponseCallbackTuple.wasCreated(object.getObjectId()), ResponseCallbackTuple.defaultFailureListener);
        waitThenAssertTestResults();

        CMUser user = loggedInUser();
        object = new ExtendedLocallySavableCMObject("FFF", false, 1);
        object.save(applicationContext, user.getSessionToken(), ResponseCallbackTuple.wasCreated(object.getObjectId()), ResponseCallbackTuple.defaultFailureListener);
        waitThenAssertTestResults();
    }

    @Test
    public void testObjectModificationRequest() {
        final ExtendedLocallySavableCMObject object = new ExtendedLocallySavableCMObject("Fred", true, 55);

        queue.add(new ObjectModificationRequest(object, ResponseCallbackTuple.wasCreated(object.getObjectId()), defaultFailureListener));
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
        queue.add(new ObjectModificationRequest(userObject, user.getSessionToken(), objectModificationResponseResponseCallbackTuple, defaultFailureListener));
        waitThenAssertTestResults();

        assertUserHasObject(userObject, user);
        assertServerHasObject(object);
    }

    @Test
    public void testObjectLoadRequest() {
        ExtendedCMObject object = new ExtendedCMObject("John", 26);
        insertAndAssert(object);

        queue.add(new ObjectLoadRequest(wasLoaded(object), defaultFailureListener));
        waitThenAssertTestResults();

        ExtendedCMObject fredObject = new ExtendedCMObject("Fred", 3);
        insertAndAssert(fredObject);

        queue.add(new ObjectLoadRequest(fredObject.getObjectId(), wasLoaded(fredObject), defaultFailureListener));
        waitThenAssertTestResults();

        queue.add(new ObjectLoadRequestBuilder(wasLoaded(fredObject), defaultFailureListener).search("[number < 10]").build());
        waitThenAssertTestResults();
    }

    @Test
    public void testUserObjectLoadRequest() {
        CMUser user = loggedInUser();
        ExtendedCMObject object = new ExtendedCMObject("Francis Farmer", 55);
        UserCMWebService userService = service.getUserWebService(user.getSessionToken());
        ObjectModificationResponse insertResponse = userService.insert(object.transportableRepresentation());
        assertTrue(insertResponse.wasSuccess());

        queue.add(new ObjectLoadRequest(user.getSessionToken(), wasLoaded(object), defaultFailureListener));
        waitThenAssertTestResults();

        ExtendedLocallySavableCMObject anotherObject = new ExtendedLocallySavableCMObject("Kurt", false, 27);
        userService.insert(anotherObject.transportableRepresentation());
        insertResponse = userService.insert(object.transportableRepresentation());
        assertTrue(insertResponse.wasSuccess());

        queue.add(new ObjectLoadRequest(anotherObject.getObjectId(), user.getSessionToken(), wasLoaded(anotherObject), defaultFailureListener));
        waitThenAssertTestResults();

        String searchString = "[__class__=\"" + ClassNameRegistry.forClass(ExtendedLocallySavableCMObject.class) + "\"]";
        queue.add(new ObjectLoadRequestBuilder(user.getSessionToken(), wasLoaded(anotherObject), defaultFailureListener).search(searchString).build());
        waitThenAssertTestResults();

        queue.add(new ObjectLoadRequest(Arrays.asList(object.getObjectId(), anotherObject.getObjectId()), user.getSessionToken(), wasLoaded(object, anotherObject), defaultFailureListener));
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
