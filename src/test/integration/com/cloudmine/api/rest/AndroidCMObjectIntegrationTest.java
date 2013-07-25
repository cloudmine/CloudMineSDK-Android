package com.cloudmine.api.rest;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cloudmine.api.CMAccessList;
import com.cloudmine.api.CMAccessPermission;
import com.cloudmine.api.CMObject;
import com.cloudmine.api.CMUser;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.db.BaseLocallySavableCMObject;
import com.cloudmine.api.db.LocallySavableCMObject;
import com.cloudmine.api.integration.CMObjectIntegrationTest;
import com.cloudmine.api.persistance.ClassNameRegistry;
import com.cloudmine.api.rest.callbacks.CreationResponseCallback;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.CreationResponse;
import com.cloudmine.api.rest.response.ObjectModificationResponse;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ExtendedCMObject;
import com.cloudmine.test.ExtendedLocallySavableCMObject;
import com.cloudmine.test.ResponseCallbackTuple;
import com.cloudmine.test.TestServiceCallback;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.cloudmine.test.AsyncTestResultsCoordinator.waitThenAssertTestResults;
import static com.cloudmine.test.ResponseCallbackTuple.*;
import static junit.framework.Assert.*;

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

    @Test
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

        queue.add(new com.cloudmine.api.rest.ObjectModificationRequest(object, ResponseCallbackTuple.wasCreated(object.getObjectId()), defaultFailureListener));
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
        queue.add(new BaseObjectModificationRequest(userObject, user.getSessionToken(), null, objectModificationResponseResponseCallbackTuple, defaultFailureListener));
        waitThenAssertTestResults();

        assertUserHasObject(userObject, user);
        assertServerHasObject(object);
    }

    @Test
    public void testServerFunction() {
        final ExtendedLocallySavableCMObject object = new ExtendedLocallySavableCMObject("Fred", true, 55);
        insertAndAssert(object);
        queue.add(new com.cloudmine.api.rest.ObjectLoadRequest(new CMServerFunction("NewSnippet", false), testCallback(new Response.Listener<CMObjectResponse>() {
            @Override
            public void onResponse(CMObjectResponse objectResponse) {
                Object result = objectResponse.getObject("result");
                assertNotNull(result);
                assertEquals(object, objectResponse.getCMObject(object.getObjectId()));
            }
        }), ResponseCallbackTuple.defaultFailureListener));
         waitThenAssertTestResults();
    }

    @Test
    public void testObjectLoadRequest() {
        ExtendedCMObject object = new ExtendedCMObject("John", 26);
        insertAndAssert(object);

        queue.add(new com.cloudmine.api.rest.ObjectLoadRequest(wasLoaded(object), defaultFailureListener));
        waitThenAssertTestResults();

        ExtendedCMObject fredObject = new ExtendedCMObject("Fred", 3);
        insertAndAssert(fredObject);

        queue.add(new com.cloudmine.api.rest.ObjectLoadRequest(fredObject.getObjectId(), wasLoaded(fredObject), defaultFailureListener));
        waitThenAssertTestResults();

        queue.add(new ObjectLoadRequestBuilder(wasLoaded(fredObject), defaultFailureListener).search("[number < 10]").build());
        waitThenAssertTestResults();

        queue.add(new BaseObjectLoadRequest((Collection<String>) null, null, null, wasLoaded(fredObject, object), defaultFailureListener));
        waitThenAssertTestResults();
    }

    @Test
    public void testUserObjectLoadRequest() {
        CMUser user = loggedInUser();
        ExtendedCMObject object = new ExtendedCMObject("Francis Farmer", 55);
        UserCMWebService userService = service.getUserWebService(user.getSessionToken());
        ObjectModificationResponse insertResponse = userService.insert(object.transportableRepresentation());
        assertTrue(insertResponse.wasSuccess());

        queue.add(new com.cloudmine.api.rest.ObjectLoadRequest(user.getSessionToken(), wasLoaded(object), defaultFailureListener));
        waitThenAssertTestResults();

        ExtendedLocallySavableCMObject anotherObject = new ExtendedLocallySavableCMObject("Kurt", false, 27);
        userService.insert(anotherObject.transportableRepresentation());
        insertResponse = userService.insert(object.transportableRepresentation());
        assertTrue(insertResponse.wasSuccess());

        queue.add(new com.cloudmine.api.rest.ObjectLoadRequest(anotherObject.getObjectId(), user.getSessionToken(), wasLoaded(anotherObject), defaultFailureListener));
        waitThenAssertTestResults();

        String searchString = "[__class__=\"" + ClassNameRegistry.forClass(ExtendedLocallySavableCMObject.class) + "\"]";
        queue.add(new ObjectLoadRequestBuilder(user.getSessionToken(), wasLoaded(anotherObject), defaultFailureListener).search(searchString).build());
        waitThenAssertTestResults();

        queue.add(new com.cloudmine.api.rest.ObjectLoadRequest(Arrays.asList(object.getObjectId(), anotherObject.getObjectId()), user.getSessionToken(), wasLoaded(object, anotherObject), defaultFailureListener));
        waitThenAssertTestResults();
    }

    @Test
    public void testMassCreate() {
        createMultipleObjects(27);
    }

    private List<CMObject> createMultipleObjects(int numberToCreate) {
        final List<CMObject> objects = new ArrayList<CMObject>();
        for(int i = 0; i < numberToCreate; i++) {
            ExtendedLocallySavableCMObject anotherObject = new ExtendedLocallySavableCMObject("Kurt" + i, false,i);
            objects.add(anotherObject);
        }
        LocallySavableCMObject.saveObjects(applicationContext, objects, testCallback(new Response.Listener<ObjectModificationResponse>() {
            @Override
            public void onResponse(ObjectModificationResponse modificationResponse) {
                assertEquals(objects.size(), modificationResponse.getCreatedObjectIds().size());
                for (CMObject object : objects) {
                    assertTrue(modificationResponse.wasCreated(object.getObjectId()));
                }
            }
        }), ResponseCallbackTuple.defaultFailureListener);
        waitThenAssertTestResults();
        return objects;
    }

    @Test
    public void testPagingSupport() {
        testMassCreate();

        Request objectLoadRequest = new ObjectLoadRequestBuilder(testCallback(new Response.Listener<CMObjectResponse>() {
            @Override
            public void onResponse(CMObjectResponse objectResponse) {
                List<CMObject> objects = objectResponse.getObjects();
                assertEquals(10, objects.size());

                for(int i = 0; i < 10; i++) {
                    assertEquals((10+i), ((ExtendedLocallySavableCMObject)objects.get(i)).getNumberOfHighFives());
                }
            }
        }), ResponseCallbackTuple.defaultFailureListener).startAt(10).limit(10).sortBy("numberOfHighFives").build();
        queue.add(objectLoadRequest);
        waitThenAssertTestResults();

        objectLoadRequest = new ObjectLoadRequestBuilder(testCallback(new Response.Listener<CMObjectResponse>() {
            @Override
            public void onResponse(CMObjectResponse objectResponse) {
                List<CMObject> objects = objectResponse.getObjects();
                assertEquals(7, objects.size());
                assertEquals(27, objectResponse.getCount());
                for(int i = 0; i < 7; i++) {
                    assertEquals((20+i), ((ExtendedLocallySavableCMObject)objects.get(i)).getNumberOfHighFives());
                }
            }
        }), ResponseCallbackTuple.defaultFailureListener).limit(10).getCount().sortBy("numberOfHighFives").startAt(20).build();
        queue.add(objectLoadRequest);
        waitThenAssertTestResults();
    }

    @Test
    public void testLoadSharedSupport() {
        CMUser owner = loggedInUser();
        CMUser notOwner = randomLoggedInUser();
        ExtendedLocallySavableCMObject sharableObject = new ExtendedLocallySavableCMObject("Bill", false, 0);

        CMAccessList accessList = new CMAccessList(owner, CMAccessPermission.READ);
        accessList.grantAccessTo(notOwner);
        sharableObject.grantAccess(accessList);

        accessList.save(TestServiceCallback.testCallback(new CreationResponseCallback() {
            public void onCompletion(CreationResponse response) {
                assertTrue(response.wasSuccess());
            }
        }));
        waitThenAssertTestResults();
        sharableObject.setSaveWith(owner);
        sharableObject.save(applicationContext, ResponseCallbackTuple.testCallback(new Response.Listener<ObjectModificationResponse>() {
            @Override
            public void onResponse(ObjectModificationResponse o) {
                assertTrue(o.wasSuccess());
            }
        }), ResponseCallbackTuple.defaultFailureListener);
        waitThenAssertTestResults();

        BaseObjectLoadRequest request = new ObjectLoadRequestBuilder(notOwner.getSessionToken(), ResponseCallbackTuple.wasLoaded(sharableObject), ResponseCallbackTuple.defaultFailureListener).getShared().build();
        queue.add(request);
        waitThenAssertTestResults();
    }

    @Test
    public void testObjectDeletion() {
        List<CMObject> deletableObjects = createMultipleObjects(5);
        LocallySavableCMObject firstObject = (LocallySavableCMObject) deletableObjects.get(0);

        firstObject.delete(applicationContext, ResponseCallbackTuple.wasDeleted(firstObject.getObjectId()), ResponseCallbackTuple.defaultFailureListener);
        waitThenAssertTestResults();

        final String[] deleteIds = new String[4];
        for(int i = 0; i < 4; i++) {
            deleteIds[i] = deletableObjects.get(i+1).getObjectId();
        }
        LocallySavableCMObject.delete(applicationContext, Arrays.asList(deleteIds), ResponseCallbackTuple.wasDeleted(deleteIds), ResponseCallbackTuple.defaultFailureListener);
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
