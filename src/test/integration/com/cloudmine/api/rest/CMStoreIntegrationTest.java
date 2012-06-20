package com.cloudmine.api.rest;

import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.CMUser;
import com.cloudmine.api.SimpleCMObject;
import com.cloudmine.api.StoreIdentifier;
import com.cloudmine.api.rest.callbacks.LoginResponseCallback;
import com.cloudmine.api.rest.callbacks.ObjectModificationResponseCallback;
import com.cloudmine.api.rest.callbacks.SimpleCMObjectResponseCallback;
import com.cloudmine.api.rest.response.LoginResponse;
import com.cloudmine.api.rest.response.ObjectModificationResponse;
import com.cloudmine.api.rest.response.SimpleCMObjectResponse;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ServiceTestBase;
import com.cloudmine.test.TestServiceCallback;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.cloudmine.test.AsyncTestResultsCoordinator.reset;
import static com.cloudmine.test.AsyncTestResultsCoordinator.waitThenAssertTestResults;
import static junit.framework.Assert.*;

/**
 * Copyright CloudMine LLC
 * User: johnmccarthy
 * Date: 6/13/12, 3:51 PM
 */
@RunWith(CloudMineTestRunner.class)
public class CMStoreIntegrationTest extends ServiceTestBase {
    private CMStore store;
    @Before
    public void setUp() {
        super.setUp();
        store = CMStore.CMStore();
    }

    @Test
    public void testSaveObject() {
        final SimpleCMObject object = SimpleCMObject.SimpleCMObject();
        object.add("bool", true);


        store.saveObject(object, TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
            public void onCompletion(ObjectModificationResponse response) {

                SimpleCMObjectResponse loadResponse = CMWebService.getService().loadObject(object.getObjectId());
                SimpleCMObject loadedObject = loadResponse.getSimpleCMObject(object.getObjectId());
                assertNotNull(loadedObject);

                assertEquals(object, loadedObject);
                assertEquals(StoreIdentifier.DEFAULT, object.getSavedWith());

            }
        }));
        waitThenAssertTestResults();
    }

    @Test
    public void testSaveUserObject() {
        final SimpleCMObject object = SimpleCMObject.SimpleCMObject();
        object.add("bool", true);
        CMUser user = CMUser.CMUser("dfljdsfkdfskd@t.com", "t");
        CMWebService.getService().insert(user);
        final CMSessionToken token = CMWebService.getService().login(user).getSessionToken();

        object.setSaveWith(new StoreIdentifier(token));
        CMStore store = CMStore.CMStore();
        store.setLoggedInUser(token);

        store.saveObject(object, TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
            public void onCompletion(ObjectModificationResponse ignoredResponse) {
                assertTrue(ignoredResponse.wasSuccess());
                SimpleCMObjectResponse response = CMWebService.getService().getUserWebService(token).loadObject(object.getObjectId());
                assertTrue(response.wasSuccess());
                SimpleCMObject loadedObject = response.getSimpleCMObject(object.getObjectId());
                assertNotNull(loadedObject);
                assertEquals(object, loadedObject);
            }
        }));
        waitThenAssertTestResults();

        SimpleCMObjectResponse response = CMWebService.getService().getUserWebService(token).loadObject(object.getObjectId());
        assertTrue(response.wasSuccess());
        assertEquals(response.getSimpleCMObject(object.getObjectId()), object);
    }

    @Test
    public void testUserLogin() {
        CMUser user = user();
        service.insert(user);
        CMSessionToken token = service.login(user).getSessionToken();
        service.getUserWebService(token).insert(SimpleCMObject.SimpleCMObject("key").add("k", "v").asJson());
        reset(2);
        store.login(user, TestServiceCallback.testCallback(new LoginResponseCallback() {
            public void onCompletion(LoginResponse response) {
                assertTrue(response.wasSuccess());
                store.loadAllUserObjects(TestServiceCallback.testCallback(new SimpleCMObjectResponseCallback() {
                    public void onCompletion(SimpleCMObjectResponse response) {
                        assertTrue(response.wasSuccess());
                        assertEquals("v", response.getSimpleCMObject("key").getString("k"));
                    }
                }));
            }
        }));

        waitThenAssertTestResults();

    }

    @Test
    public void testLoadUserObjects() {
        SimpleCMObject appObject = SimpleCMObject.SimpleCMObject();
        appObject.add("SomeKey", "Value");


        service.insert(appObject.asJson());

        CMUser user = user();
        service.insert(user);
        CMSessionToken token = service.login(user).getSessionToken();

        final List<SimpleCMObject> userObjects = new ArrayList<SimpleCMObject>();
        UserCMWebService userService = service.getUserWebService(token);
        for(int i = 0; i < 5; i++) {
            SimpleCMObject userObject = SimpleCMObject.SimpleCMObject();
            userObject.add("integer", i);
            userObjects.add(userObject);
            userService.insert(userObject.asJson());
        }
        store.setLoggedInUser(token);
        store.loadAllUserObjects(TestServiceCallback.testCallback(new SimpleCMObjectResponseCallback() {
            public void onCompletion(SimpleCMObjectResponse response) {
                assertTrue(response.wasSuccess());
                assertEquals(userObjects.size(), response.getObjects().size());
                for (SimpleCMObject object : userObjects) {
                    assertEquals(object, response.getSimpleCMObject(object.getObjectId()));
                }
            }
        }));
        waitThenAssertTestResults();
    }

    @Test
    public void testDeleteObjects() {
        final SimpleCMObject appObject = SimpleCMObject.SimpleCMObject();
        appObject.add("SomeKey", "Value");


        service.insert(appObject.asJson());

        store.deleteObject(appObject, TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
            public void onCompletion(ObjectModificationResponse response) {
                assertTrue(response.wasSuccess());
                response.wasDeleted(appObject.getObjectId());
            }
        }));
        waitThenAssertTestResults();
    }

    @Test
    public void testDeleteUserObject() {
        final SimpleCMObject userObject = SimpleCMObject.SimpleCMObject();
        userObject.add("key", "value");

        CMUser user = user();
        CMSessionToken token = service.login(user).getSessionToken();
        userObject.setSaveWith(token);
        store.setLoggedInUser(token);
        store.saveObject(userObject, TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
            public void onCompletion(ObjectModificationResponse response) {
                assertTrue(response.wasSuccess());
                assertTrue(response.wasCreated(userObject.getObjectId()));
            }
        }));
        waitThenAssertTestResults();
        store.deleteObject(userObject, TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
            public void onCompletion(ObjectModificationResponse response) {
                assertTrue(response.wasSuccess());
                assertTrue(response.wasDeleted(userObject.getObjectId()));
            }
        }));
        waitThenAssertTestResults();
    }

    @Test
    public void testSaveAddedObjects() throws ExecutionException, TimeoutException, InterruptedException {
        SimpleCMObject appObject = SimpleCMObject.SimpleCMObject();
        appObject.setSaveWith(StoreIdentifier.DEFAULT);
        appObject.add("simple", "value");

        store.addObject(appObject);
        Future<ObjectModificationResponse> responseFuture = store.saveStoreApplicationObjects();
        ObjectModificationResponse response = responseFuture.get(10, TimeUnit.SECONDS);
        assertTrue(response.wasSuccess());
        assertTrue(response.wasCreated(appObject.getObjectId()));
    }
}
