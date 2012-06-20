package com.cloudmine.api.rest;

import com.cloudmine.api.*;
import com.cloudmine.api.rest.callbacks.*;
import com.cloudmine.api.rest.response.*;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ServiceTestBase;
import com.cloudmine.test.TestServiceCallback;
import com.xtremelabs.robolectric.Robolectric;
import junit.framework.Assert;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.cloudmine.test.AsyncTestResultsCoordinator.*;
import static junit.framework.Assert.*;

/**
 * Copyright CloudMine LLC
 * CMUser: johnmccarthy
 * Date: 5/16/12, 2:40 PM
 */
@RunWith(CloudMineTestRunner.class)
public class CloudMineWebServiceIntegrationTest extends ServiceTestBase{

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
    public static final CMUser USER = new AndroidCMUser("francis2@gmail.com", "GOD");


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
        CMUser newUser = CMUser.CMUser("test2@test.com", "password");
        service.asyncCreateUser(newUser, TestServiceCallback.testCallback(new CMResponseCallback() {
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
        ObjectModificationResponse response = service.set(TEST_JSON);
        assertWasSuccess(response);

        Assert.assertTrue(response.hasSuccessKey("TESTING4703"));
    }

    @Test
    public void testStringSearch() {
        service.set(COMPLEX_JSON);

        SimpleCMObjectResponse response = service.search("[innerKey=\"inner spaced String\"]");
        assertWasSuccess(response);
    }

    @Test
    public void testDeleteAll() {
        service.set(TEST_JSON);
        ObjectModificationResponse response = service.deleteAll();
        assertWasSuccess(response);
    }

    @Test
    public void testBasicUpdate() {
        ObjectModificationResponse response = service.update(TEST_JSON);
        assertWasSuccess(response);

        Assert.assertTrue(response.hasSuccessKey("TESTING4703"));
    }

    @Test
    public void testBasicGet() {
        service.set(TEST_JSON);
        SimpleCMObjectResponse response = service.get();
        assertWasSuccess(response);
        Assert.assertTrue(response.hasSuccessKey("TESTING4703"));
    }

    @Test
    public void testFileStorageSet() throws Exception {
        InputStream input = getObjectInputStream();
        CMResponse response = service.set(CMFile.CMFile(input));
        Assert.assertNotNull(response);
        Assert.assertTrue(response.hasObject("key"));
    }

    @Test
    public void testFileStorageGet() throws Exception {
        CMFile insertedFile = CMFile.CMFile(getObjectInputStream(), "theFileKey", CMFile.DEFAULT_CONTENT_TYPE);
        CMResponse response = service.set(
                insertedFile);

        CMFile loadedFile = service.getFile("theFileKey");
        byte[] insertedFileContents = insertedFile.fileContents();
        byte[] loadedFileContents = loadedFile.fileContents();
        assertEquals(insertedFileContents.length, loadedFileContents.length);
        for(int i = 0; i < loadedFileContents.length; i++) {
            assertEquals(insertedFileContents[i], loadedFileContents[i]);
        }
    }

    @Test
    public void testAsyncObjectLoad() throws Throwable {
        final SimpleCMObject task = SimpleCMObject.SimpleCMObject();
        task.setClass("task");
        task.add("name", "Do dishes");
        task.add("isDone", false);

        service.asyncInsert(task, TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
            @Override
            public void onCompletion(ObjectModificationResponse response) {
                service.asyncLoadObjectsOfClass("task", new SimpleCMObjectResponseCallback() {
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
        service.set(COMPLEX_JSON);

        SuccessErrorResponse response = service.delete("deepKeyed");

        assertWasSuccess(response);

        response = service.get();
        assertWasSuccess(response);
        Assert.assertTrue(response.hasSuccessKey("oneKey"));
        Assert.assertFalse(response.hasSuccessKey("deepKeyed"));

        service.set(COMPLEX_JSON);

        service.delete(Arrays.asList("deepKeyed", "oneKey"));
        response = service.get();
        Assert.assertFalse(response.hasSuccessKey("oneKey"));
        Assert.assertFalse(response.hasSuccessKey("deepKeyed"));
    }

    @Test
    @Ignore // until we can delete users this test will fail every time but the first time its run
    public void testCreateUser() {
        CMResponse response = service.set(USER);
        Assert.assertTrue(response.was(201));
    }

    @Test
    public void testUserLogin() {
        CMUser nonExistentUser = CMUser.CMUser("some@dude.com", "123");
        LoginResponse response = service.login(nonExistentUser);
        Assert.assertTrue(response.was(401));
        service.set(USER);

        response = service.login(USER);
        Assert.assertTrue(response.was(200));
    }

    @Test
    public void testUserLogout() {
        CMResponse response = service.logout(CMSessionToken.CMSessionToken("this token doesn't exist", new Date()));
        Assert.assertEquals(401, response.getStatusCode());

        service.set(USER);
        LoginResponse loginResponse = service.login(USER);
        Assert.assertTrue(loginResponse.was(200));

        response = service.logout(loginResponse.userToken());
        Assert.assertTrue(response.was(200));
    }

    @Test
    public void testAsyncChangePassword() {
        CMUser user = randomUser();
        service.set(user);
        service.asyncChangePassword(user, "newPassword", TestServiceCallback.testCallback(new CMResponseCallback() {
           public void onCompletion(CMResponse response) {
               assertTrue(response.wasSuccess());
           }
        }));
        waitThenAssertTestResults();
        CMUser newPasswordUser = CMUser.CMUser(user.email(), "newPassword");
        LoginResponse response = service.login(newPasswordUser);
        assertTrue(response.wasSuccess());
    }

    @Test
    public void testAsyncResetPassword() {
        CMUser user = randomUser();
        service.set(user);

        service.asyncResetPasswordRequest(user.email(), TestServiceCallback.testCallback(new CMResponseCallback() {
            @Override
            public void onCompletion(CMResponse response) {
                assertTrue(response.wasSuccess());
            }
        }));//can't actually test the code since it goes to an email address
        waitThenAssertTestResults();
    }

    private CMUser randomUser() {
        String userName = UUID.randomUUID().toString().replaceAll("-", "") + "@gmail.com";
        String password = "the";
        return new AndroidCMUser(userName,  password);
    }

    @Test
    public void testAsyncLogin() {
        service.set(USER);
        service.asyncLogin(USER, TestServiceCallback.testCallback(new LoginResponseCallback() {
            @Override
            public void onCompletion(LoginResponse response) {
                Assert.assertTrue(response.wasSuccess());
            }
        }));
        waitThenAssertTestResults();
        service.asyncLogin(CMUser.CMUser("thisdoesntexist@dddd.com", "somepass"), TestServiceCallback.testCallback(new LoginResponseCallback() {
            public void onCompletion(LoginResponse response) {
                Assert.assertEquals(CMSessionToken.FAILED, response.userToken());
            }
        }));
        waitThenAssertTestResults();
    }

    @Test
    public void testAsyncLogout() {
        service.set(USER);
        reset(2);
        service.asyncLogin(USER, TestServiceCallback.testCallback(new LoginResponseCallback() {
            public void onCompletion(LoginResponse response) {
                Assert.assertTrue(response.wasSuccess());

                service.asyncLogout(response.userToken(), TestServiceCallback.testCallback(new CMResponseCallback() {
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
        service.set(COMPLEX_JSON);
        service.asyncLoadObjects(TestServiceCallback.testCallback(new SimpleCMObjectResponseCallback() {
            public void onCompletion(SimpleCMObjectResponse response) {
                Assert.assertEquals(2, response.objects().size());
                SimpleCMObject object = response.object("deepKeyed");
                Assert.assertEquals(Integer.valueOf(45), object.getInteger("innerKeyToNumber"));
            }
        }));
        waitThenAssertTestResults();

        service.asyncLoadObject("oneKey", TestServiceCallback.testCallback(new SimpleCMObjectResponseCallback() {
            public void onCompletion(SimpleCMObjectResponse response) {
                Assert.assertTrue(response.wasSuccess());
                Assert.assertEquals(1, response.objects().size());
            }
        }));
        waitThenAssertTestResults();
    }

    @Test
    public void testAsyncSearch() {
        service.set(COMPLEX_JSON);

        service.asyncSearch("[innerKey=\"inner spaced String\"]", TestServiceCallback.testCallback(new SimpleCMObjectResponseCallback() {
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
        SimpleCMObject geoObject = SimpleCMObject.SimpleCMObject("location");
        geoObject.add("geoPoint", CMGeoPoint.CMGeoPoint(50, 50, "geo"));

        service.set(geoObject.asJson());

        service.asyncSearch("[geoPoint near (50, 50)]", TestServiceCallback.testCallback(new SimpleCMObjectResponseCallback() {
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
        SimpleCMObject deepObject = SimpleCMObject.SimpleCMObject(JsonUtilities.jsonCollection(DEEP_KEYED_JSON));
        SimpleCMObject simpleObject = SimpleCMObject.SimpleCMObject(JsonUtilities.jsonCollection(SIMPLE_JSON));
        service.asyncInsert(Arrays.asList(deepObject, simpleObject), TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
            public void onCompletion(ObjectModificationResponse response) {
                Assert.assertTrue(response.wasSuccess());
                Assert.assertTrue(response.wasCreated("deepKeyed"));
                Assert.assertTrue(response.wasCreated("oneKey"));
            }
        }));
        waitThenAssertTestResults();

        deepObject.remove("innerKey");

        service.asyncInsert(Arrays.asList(deepObject, simpleObject), TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
            public void onCompletion(ObjectModificationResponse response) {
                Assert.assertTrue(response.wasSuccess());
                Assert.assertTrue(response.wasUpdated("deepKeyed"));
                Assert.assertTrue(response.wasUpdated("oneKey"));
                SimpleCMObjectResponse loadObjectResponse = service.get();
                Assert.assertEquals(2, loadObjectResponse.objects().size());

                SimpleCMObject deepObject = loadObjectResponse.object("deepKeyed");
                Assert.assertNull(deepObject.get("innerKey"));
            }
        }));
        waitThenAssertTestResults();
    }

    @Test
    public void testAsyncDelete() {
        service.set(COMPLEX_JSON);

        service.asyncDelete("oneKey", TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
            public void onCompletion(ObjectModificationResponse response) {
                List<SimpleCMObject> successObjects = response.successObjects();
                assertTrue(response.wasSuccess());
                assertTrue(response.wasDeleted("oneKey"));
                assertEquals(1, successObjects.size());
            }
        }));
        waitThenAssertTestResults();
        service.asyncDelete(Arrays.asList("deepKeyed", "oneKey"), TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
            public void onCompletion(ObjectModificationResponse response) {
                List<SimpleCMObject> successObjects = response.successObjects();
                assertTrue(response.wasSuccess());
                assertTrue(response.wasDeleted("deepKeyed"));
                assertEquals(1, successObjects.size());
                assertEquals(1, response.errorObjects().size());
            }
        }));
        waitThenAssertTestResults();
    }

    @Test
    public void testAsyncUpload() throws IOException {
        InputStream input = getObjectInputStream();
        CMFile file = CMFile.CMFile(input);
        service.asyncUpload(file, TestServiceCallback.testCallback(new FileCreationResponseCallback() {
            public void onCompletion(FileCreationResponse file) {
                assertTrue(file.wasSuccess());
                assertNotNull(file.fileKey());
            }
        }));
        waitThenAssertTestResults();
    }

    @Test
    public void testAsyncLoadFile() throws Exception {
        InputStream input = getObjectInputStream();
        final CMFile file = CMFile.CMFile(input, "fileKey", null);
        CMResponse response = service.set(file);
        assertTrue(response.wasSuccess());

        service.asyncLoadFile("fileKey", TestServiceCallback.testCallback(new FileLoadCallback("fileKey") {
           public void onCompletion(CMFile loadedFile) {
               assertEquals(file, loadedFile);
           }
        }));
        waitThenAssertTestResults();
    }

    @Test
    public void testUrlCharacters() {
        service.asyncDelete("a key with spaces");
    }

    @Test
    public void testAsyncLoadNullFile() {
        service.asyncLoadFile("nonexistentfile", TestServiceCallback.testCallback(new FileLoadCallback("nonexistent key for a file") {
            public void onCompletion(CMFile loadedFile) {
                assertEquals(1, loadedFile.fileContents().length);
                assertTrue(CMFile.isEmpty(loadedFile));
            }
        }));
        waitThenAssertTestResults();
    }

    @Test
    public void testAsyncDeleteFile() throws Exception {
        InputStream input = getObjectInputStream();
        final CMFile file = CMFile.CMFile(input, "fileKey", null);
        service.set(file);

        service.asyncDeleteFile(file, TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
            public void onCompletion(ObjectModificationResponse response) {
                assertTrue(response.wasSuccess());
                assertTrue(response.wasDeleted("fileKey"));
            }
        }));
        waitThenAssertTestResults();

    }

    @Test
    public void testAsyncUpdate() {
        //These tests are the same as above EXCEPT when innerKey is deleted and then inserted, the value
        //should still exist on the returned object. copy/paste code D:
        SimpleCMObject deepObject = SimpleCMObject.SimpleCMObject(JsonUtilities.jsonCollection(DEEP_KEYED_JSON));
        SimpleCMObject simpleObject = SimpleCMObject.SimpleCMObject(JsonUtilities.jsonCollection(SIMPLE_JSON));
        service.asyncUpdate(Arrays.asList(deepObject, simpleObject),
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

        service.asyncUpdate(Arrays.asList(deepObject, simpleObject),
                TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
                    public void onCompletion(ObjectModificationResponse response) {
                        Assert.assertTrue(response.wasSuccess());
                        Assert.assertTrue(response.wasUpdated("deepKeyed"));
                        Assert.assertTrue(response.wasUpdated("oneKey"));
                        SimpleCMObjectResponse loadObjectResponse = service.get();
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
