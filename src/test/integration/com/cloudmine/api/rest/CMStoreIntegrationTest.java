package com.cloudmine.api.rest;

import com.cloudmine.api.*;
import com.cloudmine.api.rest.callbacks.Callback;
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
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.cloudmine.test.AsyncTestResultsCoordinator.reset;
import static com.cloudmine.test.AsyncTestResultsCoordinator.waitThenAssertTestResults;
import static junit.framework.Assert.*;

/**
 * <br>Copyright CloudMine LLC. All rights reserved<br> See LICENSE file included with SDK for details.
 * User: johnmccarthy
 * Date: 6/13/12, 3:51 PM
 */
@RunWith(CloudMineTestRunner.class)
public class CMStoreIntegrationTest extends ServiceTestBase {
    private CMStore store;
    private int FUTURE_WAIT_TIME = 10;

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

        object.setSaveWith(StoreIdentifier.StoreIdentifier(token));
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
        final CMSessionToken token = service.login(user).getSessionToken();

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
                    SimpleCMObject responseObject = response.getSimpleCMObject(object.getObjectId());
                    assertEquals(StoreIdentifier.StoreIdentifier(token), responseObject.getSavedWith());
                    assertEquals(object, responseObject);
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
        assertEquals(1, store.getStoredObjects().size());
        store.deleteObject(userObject, TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
            public void onCompletion(ObjectModificationResponse response) {
                assertTrue(response.wasSuccess());
                assertTrue(response.wasDeleted(userObject.getObjectId()));
            }
        }));
        waitThenAssertTestResults();
        assertEquals(0, store.getStoredObjects().size());

    }

    @Test
    public void testSaveAddedObjects() throws ExecutionException, TimeoutException, InterruptedException {
        SimpleCMObject appObject = SimpleCMObject.SimpleCMObject();
        appObject.setSaveWith(StoreIdentifier.DEFAULT);
        appObject.add("simple", "value");

        store.addObject(appObject);
        Future<ObjectModificationResponse> responseFuture = store.saveStoreApplicationObjects();
        ObjectModificationResponse response = responseFuture.get(FUTURE_WAIT_TIME, TimeUnit.SECONDS);
        assertTrue(response.wasSuccess());
        assertTrue(response.wasCreated(appObject.getObjectId()));
    }

    @Test
    public void testStoreKeepValues() throws ExecutionException, TimeoutException, InterruptedException {
        SimpleCMObject object = simpleObject();

        Future<ObjectModificationResponse> responseFuture = store.saveObject(object);
        responseFuture.get(FUTURE_WAIT_TIME, TimeUnit.SECONDS);
        object.add("number", 10);
        object.add("string", "name");
        object.setClass("testObject");

        Future<ObjectModificationResponse> secondSaveFuture = store.saveStoreApplicationObjects();
        ObjectModificationResponse response = secondSaveFuture.get(FUTURE_WAIT_TIME, TimeUnit.SECONDS);
        assertTrue(response.wasSuccess());
        assertEquals(object, store.getStoredObject(object.getObjectId()));

        Future<SimpleCMObjectResponse> loadFuture = store.loadAllApplicationObjects();
        SimpleCMObjectResponse loadResponse = loadFuture.get(FUTURE_WAIT_TIME, TimeUnit.SECONDS);
        assertTrue(loadResponse.wasSuccess());
        SimpleCMObject loadObject = loadResponse.getSimpleCMObject(object.getObjectId());
        assertEquals(object, loadObject);
        assertEquals(object, store.getStoredObject(object.getObjectId()));
    }

    @Test
    public void testStoreFunctionOptions() throws ExecutionException, TimeoutException, InterruptedException {
        SimpleCMObject object = simpleObject();
        object.add("string", "dog");
        assertTrue(store.saveObject(object).get(FUTURE_WAIT_TIME, TimeUnit.SECONDS).wasSuccess());
        CMServerFunction function = CMServerFunction.CMServerFunction("NewSnippet", false);
        Future<SimpleCMObjectResponse> loadResponseFuture = store.loadApplicationObjectWithObjectId(object.getObjectId(), Callback.DO_NOTHING,
                CMRequestOptions.CMRequestOptions(function));
        SimpleCMObjectResponse response = loadResponseFuture.get(FUTURE_WAIT_TIME, TimeUnit.SECONDS);
        Object result = response.getObject("result");
        //assertNotNull(result);
    }

    @Test
    public void testStorePagingOptions() throws ExecutionException, TimeoutException, InterruptedException {
        Collection<SimpleCMObject> objects = new ArrayList<SimpleCMObject>();
        for(int i = 0; i < 5; i++) {
            objects.add(simpleObject());
        }
        store.addObjects(objects);
        ObjectModificationResponse response = store.saveStoreApplicationObjects().get(FUTURE_WAIT_TIME, TimeUnit.SECONDS);
        assertTrue(response.wasSuccess());

        CMRequestOptions options = CMRequestOptions.CMRequestOptions(CMPagingOptions.CMPagingOptions(2, 0, true));
        SimpleCMObjectResponse loadResponse = store.loadAllApplicationObjects(Callback.DO_NOTHING, options).get(FUTURE_WAIT_TIME, TimeUnit.SECONDS);
        assertTrue(loadResponse.wasSuccess());

        assertEquals(2, loadResponse.getObjects().size());
        assertEquals(5, loadResponse.getCount());

    }


    private SimpleCMObject simpleObject() {
        SimpleCMObject object = SimpleCMObject.SimpleCMObject();
        object.add("bool", false);
        return object;
    }
}
