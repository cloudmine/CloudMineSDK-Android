package com.cloudmine.api.rest;

import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.CMUser;
import com.cloudmine.api.SimpleCMObject;
import com.cloudmine.api.rest.callbacks.LoginResponseCallback;
import com.cloudmine.api.rest.callbacks.ObjectModificationResponseCallback;
import com.cloudmine.api.rest.response.LoginResponse;
import com.cloudmine.api.rest.response.ObjectModificationResponse;
import com.cloudmine.api.rest.response.SimpleCMObjectResponse;
import com.cloudmine.test.AsyncTestResultsCoordinator;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ServiceTestBase;
import com.cloudmine.test.TestServiceCallback;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.cloudmine.test.AsyncTestResultsCoordinator.waitThenAssertTestResults;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;

/**
 * Copyright CloudMine LLC
 * User: johnmccarthy
 * Date: 6/14/12, 10:53 AM
 */
@RunWith(CloudMineTestRunner.class)
public class SimpleCMObjectIntegrationTest extends ServiceTestBase {

    @Test
    public void testDefaultSave() {
        final SimpleCMObject object = SimpleCMObject.SimpleCMObject();
        object.add("string", "value");
        object.save(TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
            public void onCompletion(ObjectModificationResponse response) {
                SimpleCMObjectResponse loadResponse = AndroidCMWebService.getService().loadObject(object.getObjectId());
                Assert.assertTrue(loadResponse.wasSuccess());
                SimpleCMObject loadedObject = loadResponse.getSimpleCMObject(object.getObjectId());
                assertEquals(object, loadedObject);
            }
        }));
        waitThenAssertTestResults();
        CMUser user = user();
        service.insert(user);
        CMSessionToken token = service.login(user).getSessionToken();
        assertFalse(object.setSaveWith(token));

        object.save();
        SimpleCMObject loadedObject = service.getUserWebService(token).loadObject(object.getObjectId()).getSimpleCMObject(object.getObjectId());
        assertNull(loadedObject);

    }

    @Test
    public void testUserSave() {
        final SimpleCMObject object = SimpleCMObject.SimpleCMObject();
        object.add("bool", true);
        final CMUser user = CMUser.CMUser("w@w.com", "w");
        AsyncTestResultsCoordinator.reset(2);
        user.login(TestServiceCallback.testCallback(new LoginResponseCallback() {
            public void onCompletion(LoginResponse response) {
                object.setSaveWith(response.getSessionToken());
                object.save(TestServiceCallback.testCallback(new ObjectModificationResponseCallback() {
                    public void onCompletion(ObjectModificationResponse response) {
                        SimpleCMObjectResponse loadedObjectResponse = service.loadObject(object.getObjectId());
                        SimpleCMObject loadedObject = loadedObjectResponse.getSimpleCMObject(object.getObjectId());

                        Assert.assertNull(loadedObject);
                    }

                }));
            }
        }));
        waitThenAssertTestResults();
    }


}
