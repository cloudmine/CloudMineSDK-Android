package com.cloudmine.api.rest;

import com.cloudmine.api.*;
import com.cloudmine.api.rest.callbacks.CMResponseCallback;
import com.cloudmine.api.rest.callbacks.LoginResponseCallback;
import com.cloudmine.api.rest.callbacks.ObjectModificationResponseCallback;
import com.cloudmine.api.rest.callbacks.SimpleCMObjectResponseCallback;
import com.cloudmine.api.rest.response.*;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.TestServiceCallback;
import com.xtremelabs.robolectric.Robolectric;
import junit.framework.Assert;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.cloudmine.test.AsyncTestResultsCoordinator.*;
import static junit.framework.Assert.*;

/**
 * Copyright CloudMine LLC
 * CMUser: johnmccarthy
 * Date: 5/16/12, 2:40 PM
 */
@RunWith(CloudMineTestRunner.class)
public class CloudMineWebServiceIntegrationTest {
    private static final String APP_ID = "c1a562ee1e6f4a478803e7b51babe287";
    private static final String API_KEY = "3fc494b36d6d432d9afb051d819bdd72";

    private static final String TEST_JSON = "{\"TESTING4703\": [\"value1\", \"value2\"]}";
    private static final String DEEP_KEYED_JSON =             "    \"deepKeyed\": {\n" +
            "        \"innerKey\":\"inner spaced String\",\n" +
            "        \"innerKeyToNumber\":45,\n" +
            "        \"anotherObject\": {\n" +
            "            \"innerObjectKey\":[1, 2, 3, 4, 5]\n" +
            "        }\n" +
            "    }\n";
    private static final String SIMPLE_JSON = " \"oneKey\":{ \"meaningOfLife\":42}";
    private static final String COMPLEX_JSON = "{\n" +
           SIMPLE_JSON + ",\n" +
            DEEP_KEYED_JSON +
            "}";
    public static final CMUser USER = new CMUser("francis@gmail.com", "GOD");
    private CMWebService store;
    @Before
    public void setUp() {
        reset();
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        DeviceIdentifier.initialize(Robolectric.application.getApplicationContext());
        CMApiCredentials.initialize(APP_ID, API_KEY);
        store = AndroidCMWebService.service();
    }
    @After
    public void cleanUp() {
        store.deleteAll();
    }

    @Test
    @Ignore
    public void testSentHeaders() { //TODO this test doesn't work but its not cause the impplementation is broken

        AndroidCMWebService androidService = AndroidCMWebService.service();
        androidService.delete("someKey");

        HttpRequest sentRequest = Robolectric.getSentHttpRequest(0);
        Header idHeader = sentRequest.getFirstHeader(DeviceIdentifier.DEVICE_HEADER_KEY);
        assertNotNull(idHeader);
        assertEquals(DeviceIdentifier.uniqueId(), idHeader.getValue());

        Header agentHeader = sentRequest.getFirstHeader(CMWebService.AGENT_HEADER_KEY);
        assertNotNull(agentHeader);
        assertEquals(AndroidCMWebService.CLOUD_MINE_AGENT, agentHeader.getValue());
    }

    @Test
    @Ignore //Only works when we can delete users
    public void testAsyncCreateUser() throws Exception {
        CMUser newUser = new CMUser("test2@test.com", "password");
        store.asyncCreateUser(newUser, TestServiceCallback.testCallback(new CMResponseCallback() {
            @Override
            public void onCompletion(CMResponse response) {
                Assert.assertTrue(response.was(201));
            }

            @Override
            public void onFailure(Throwable ex, String content) {
                ex.printStackTrace();
                Assert.fail("Failed");
            }
        }));
        waitThenAssertTestResults();
    }

    @Test
    public void testBasicSet() {
        ObjectModificationResponse response = store.set(TEST_JSON);
        assertWasSuccess(response);

        Assert.assertTrue(response.hasSuccessKey("TESTING4703"));
    }

    @Test
    public void testStringSearch() {
        store.set(COMPLEX_JSON);

        SimpleCMObjectResponse response = store.search("[innerKey=\"inner spaced String\"]");
        assertWasSuccess(response);
    }

    @Test
    public void testDeleteAll() {
        store.set(TEST_JSON);
        ObjectModificationResponse response = store.deleteAll();
        assertWasSuccess(response);
    }

    @Test
    public void testBasicUpdate() {
        ObjectModificationResponse response = store.update(TEST_JSON);
        assertWasSuccess(response);

        Assert.assertTrue(response.hasSuccessKey("TESTING4703"));
    }

    @Test
    public void testBasicGet() {
        store.set(TEST_JSON);
        SimpleCMObjectResponse response = store.get();
        assertWasSuccess(response);
        Assert.assertTrue(response.hasSuccessKey("TESTING4703"));
    }

    @Test
    public void testFileStorageSet() throws Exception {
        InputStream input = getObjectInputStream();
        CMResponse response = store.set(new CMFile(input));
        Assert.assertNotNull(response);
        Assert.assertTrue(response.hasObject("key"));
    }

    @Test
    public void testFileStorageGet() throws Exception {
        CMFile insertedFile = new CMFile(getObjectInputStream(), CMFile.DEFAULT_CONTENT_TYPE, "theFileKey");
        CMResponse response = store.set(
                insertedFile);

        CMFile loadedFile = store.getFile("theFileKey");
//        Assert.assertArrayEquals(insertedFile.getFileContents(), loadedFile.getFileContents());
    }

    @Test
    public void testAsyncObjectLoad() throws Throwable {
        final SimpleCMObject task = new SimpleCMObject();
        task.setClass("task");
        task.add("name", "Do dishes");
        task.add("isDone", false);

        store.asyncInsert(task, TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
            @Override
            public void onCompletion(ObjectModificationResponse response) {
                store.allObjectsOfClass("task", new SimpleCMObjectResponseCallback() {
                    public void onCompletion(SimpleCMObjectResponse objectResponse) {
                        Assert.assertEquals(1, objectResponse.objects().size());
                        Assert.assertEquals(task, objectResponse.objects().get(0));
                    }
                });
                assertWasSuccess(response);
            }

            @Override
            public void onFailure(Throwable error, String message) {
                error.printStackTrace();
                Assert.fail("failed! " + message);
            }
        }));
        waitForTestResults();
        assertAsyncTaskResult();
    }

    @Test
    public void testKeyedDelete() {
        store.set(COMPLEX_JSON);

        SuccessErrorResponse response = store.delete("deepKeyed");

        assertWasSuccess(response);

        response = store.get();
        assertWasSuccess(response);
        Assert.assertTrue(response.hasSuccessKey("oneKey"));
        Assert.assertFalse(response.hasSuccessKey("deepKeyed"));

        store.set(COMPLEX_JSON);

        store.delete(Arrays.asList("deepKeyed", "oneKey"));
        response = store.get();
        Assert.assertFalse(response.hasSuccessKey("oneKey"));
        Assert.assertFalse(response.hasSuccessKey("deepKeyed"));
    }

    @Test
    @Ignore // until we can delete users this test will fail every time but the first time its run
    public void testCreateUser() {
        CMResponse response = store.set(USER);
        Assert.assertTrue(response.was(201));
    }

    @Test
    public void testUserLogin() {
        CMUser nonExistentUser = new CMUser("some@dude.com", "123");
        LogInResponse response = store.login(nonExistentUser);
        Assert.assertTrue(response.was(401));
        store.set(USER);

        response = store.login(USER);
        Assert.assertTrue(response.was(200));
    }

    @Test
    public void testUserLogout() {
        CMResponse response = store.logout(new CMUserToken("this token doesn't exist", new Date()));
        Assert.assertEquals(401, response.getStatusCode());

        store.set(USER);
        LogInResponse loginResponse = store.login(USER);
        Assert.assertTrue(loginResponse.was(200));

        response = store.logout(loginResponse.userToken());
        Assert.assertTrue(response.was(200));
    }

    @Test
    public void testAsyncLogin() {
        store.asyncLogin(new CMUser("thisdoesntexist@dddd.com", "somepass"), TestServiceCallback.testCallback(new LoginResponseCallback() {
            public void onCompletion(LogInResponse response) {
                Assert.assertEquals(CMUserToken.FAILED, response.userToken());
            }
        }));
        waitThenAssertTestResults();
        store.set(USER);
        store.asyncLogin(USER, TestServiceCallback.testCallback(new LoginResponseCallback() {
            @Override
            public void onCompletion(LogInResponse response) {
                Assert.assertTrue(response.wasSuccess());
            }
        }));
        waitThenAssertTestResults();
    }

    @Test
    public void testAsyncLogout() {
        store.set(USER);
        reset(2);
        store.asyncLogin(USER, TestServiceCallback.testCallback(new LoginResponseCallback() {
            public void onCompletion(LogInResponse response) {
                Assert.assertTrue(response.wasSuccess());

                store.asyncLogout(response.userToken(), TestServiceCallback.testCallback(new CMResponseCallback() {
                    public void onCompletion(CMResponse response) {
                        Assert.assertTrue(response.wasSuccess());
                    }
                }));
            }
        }));
        waitThenAssertTestResults();
    }

    @Test
    public void testAsyncGet() {
        store.set(COMPLEX_JSON);
        store.asyncLoadObjects(TestServiceCallback.testCallback(new SimpleCMObjectResponseCallback() {
            public void onCompletion(SimpleCMObjectResponse response) {
                Assert.assertEquals(2, response.objects().size());
                SimpleCMObject object = response.object("deepKeyed");
                Assert.assertEquals(Integer.valueOf(45), object.getInteger("innerKeyToNumber"));
            }
        }));
        waitThenAssertTestResults();

        store.asyncLoadObject("oneKey", TestServiceCallback.testCallback(new SimpleCMObjectResponseCallback() {
            public void onCompletion(SimpleCMObjectResponse response) {
                Assert.assertTrue(response.wasSuccess());
                Assert.assertEquals(1, response.objects().size());
            }
        }));
        waitThenAssertTestResults();
    }

    @Test
    public void testAsyncSearch() {
        store.set(COMPLEX_JSON);

        store.asyncSearch("[innerKey=\"inner spaced String\"]", TestServiceCallback.testCallback(new SimpleCMObjectResponseCallback() {
            public void onCompletion(SimpleCMObjectResponse response) {
                Assert.assertTrue(response.wasSuccess());
                Assert.assertEquals(1, response.objects().size());
                SimpleCMObject object = response.object("deepKeyed");

                SimpleCMObject anotherObject = object.getSimpleCMObject("anotherObject");

                Assert.assertEquals(Arrays.asList(1, 2, 3, 4, 5), anotherObject.getList("innerObjectKey"));
            }
        }));

        waitThenAssertTestResults();
    }

    @Test
    public void testAsyncGeoSearch() {
        SimpleCMObject geoObject = new SimpleCMObject("location");
        geoObject.add("geoPoint", new CMGeoPoint(50, 50, "geo"));

        store.set(geoObject.asJson());

        store.asyncSearch("[geoPoint near (50, 50)]", TestServiceCallback.testCallback(new SimpleCMObjectResponseCallback() {
            public void onCompletion(SimpleCMObjectResponse response) {
                List<SimpleCMObject> objects = response.objects();
                Assert.assertEquals(1, objects.size());

                CMGeoPoint geoPoint = objects.get(0).getGeoPoint("geoPoint");
                Assert.assertEquals(50, geoPoint.latitude(), 2);
                Assert.assertEquals(50, geoPoint.longitude(), 2);

            }
        }));
        waitThenAssertTestResults();
    }

    @Test
    public void testAsyncInsert() {
        SimpleCMObject deepObject = new SimpleCMObject(JsonUtilities.jsonCollection(DEEP_KEYED_JSON));
        SimpleCMObject simpleObject = new SimpleCMObject(JsonUtilities.jsonCollection(SIMPLE_JSON));
        store.asyncInsert(Arrays.asList(deepObject, simpleObject), TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
            public void onCompletion(ObjectModificationResponse response) {
                Assert.assertTrue(response.wasSuccess());
                Assert.assertTrue(response.wasCreated("deepKeyed"));
                Assert.assertTrue(response.wasCreated("oneKey"));
            }
        }));
        waitThenAssertTestResults();

        deepObject.remove("innerKey");

        store.asyncInsert(Arrays.asList(deepObject, simpleObject), TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
            public void onCompletion(ObjectModificationResponse response) {
                Assert.assertTrue(response.wasSuccess());
                Assert.assertTrue(response.wasUpdated("deepKeyed"));
                Assert.assertTrue(response.wasUpdated("oneKey"));
                SimpleCMObjectResponse loadObjectResponse = store.get();
                Assert.assertEquals(2, loadObjectResponse.objects().size());

                SimpleCMObject deepObject = loadObjectResponse.object("deepKeyed");
                Assert.assertNull(deepObject.get("innerKey"));
            }
        }));
        waitThenAssertTestResults();
    }

    @Test
    public void testAsyncDelete() {
        store.set(COMPLEX_JSON);

        store.asyncDelete("oneKey", TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
            public void onCompletion(ObjectModificationResponse response) {
                List<SimpleCMObject> successObjects = response.getSuccessObjects();
                assertTrue(response.wasSuccess());
                assertTrue(response.wasDeleted("oneKey"));
                assertEquals(1, successObjects.size());
            }
        }));
        waitThenAssertTestResults();
        store.asyncDelete(Arrays.asList("deepKeyed", "oneKey"), TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
            public void onCompletion(ObjectModificationResponse response) {
                List<SimpleCMObject> successObjects = response.getSuccessObjects();
                assertTrue(response.wasSuccess());
                assertTrue(response.wasDeleted("deepKeyed"));
                assertEquals(1, successObjects.size());
                assertEquals(1, response.getErrorObjects().size());
            }
        }));
        waitThenAssertTestResults();
    }

    @Test
    public void testAsyncUpdate() {
        //These tests are the same as above EXCEPT when innerKey is deleted and then inserted, the value
        //should still exist on the returned object. copy/paste code D:
        SimpleCMObject deepObject = new SimpleCMObject(JsonUtilities.jsonCollection(DEEP_KEYED_JSON));
        SimpleCMObject simpleObject = new SimpleCMObject(JsonUtilities.jsonCollection(SIMPLE_JSON));
        store.asyncUpdate(Arrays.asList(deepObject, simpleObject),
                TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
                    public void onCompletion(ObjectModificationResponse response) {
                        Assert.assertTrue(response.wasSuccess());
                        Assert.assertTrue(response.wasModified("deepKeyed"));
                        Assert.assertTrue(response.wasModified("oneKey")); //if these pass and below fail then delete between runs didn't happen
                        Assert.assertTrue(response.wasCreated("deepKeyed"));
                        Assert.assertTrue(response.wasCreated("oneKey"));
                    }
                }));
        waitThenAssertTestResults();

        deepObject.remove("innerKey");

        store.asyncUpdate(Arrays.asList(deepObject, simpleObject),
                TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
                    public void onCompletion(ObjectModificationResponse response) {
                        Assert.assertTrue(response.wasSuccess());
                        Assert.assertTrue(response.wasUpdated("deepKeyed"));
                        Assert.assertTrue(response.wasUpdated("oneKey"));
                        SimpleCMObjectResponse loadObjectResponse = store.get();
                        Assert.assertEquals(2, loadObjectResponse.objects().size());

                        SimpleCMObject deepObject = loadObjectResponse.object("deepKeyed");
                        Assert.assertNotNull(deepObject.get("innerKey")); //This is where this test differs from above
                    }
                }));
        waitThenAssertTestResults();
    }


    private InputStream getObjectInputStream() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ObjectOutputStream objectOutput = new ObjectOutputStream(output);
        objectOutput.write(55);
        objectOutput.writeObject("Some String is Written");
        objectOutput.flush();
        objectOutput.close();

        return new ByteArrayInputStream(output.toByteArray());
    }

    private void assertWasSuccess(SuccessErrorResponse response) {
        Assert.assertNotNull(response);
        Assert.assertFalse(response.hasError());
        Assert.assertTrue(response.hasSuccess());

    }

}
