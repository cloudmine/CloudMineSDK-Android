package com.cloudmine.api.rest;

import com.cloudmine.api.CMAccessList;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.CMUser;
import com.cloudmine.api.SimpleCMObject;
import com.cloudmine.api.rest.callbacks.CreationResponseCallback;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.CreationResponse;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ServiceTestBase;
import com.cloudmine.test.TestServiceCallback;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static com.cloudmine.test.AsyncTestResultsCoordinator.waitThenAssertTestResults;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
@RunWith(CloudMineTestRunner.class)
public class CMAccessListIntegrationTest extends ServiceTestBase {

    @Test
    public void testStoreAccessList() {
        CMUser anotherUser = randomUser();
        anotherUser.save(hasSuccess);
        waitThenAssertTestResults();

        CMUser user = user();
        SimpleCMObject anObject = SimpleCMObject.SimpleCMObject();
        anObject.add("aSecret", true);
        anObject.saveWithUser(user, hasSuccessAndHasModified(anObject));
        waitThenAssertTestResults();


        final CMAccessList list = CMAccessList.CMAccessList(user);
        List<String> userObjectIds = Arrays.asList("freddy", "teddy", "george", "puddin");
        list.grantAccessTo(userObjectIds);
        list.grantAccessTo(anotherUser);
        list.save(TestServiceCallback.testCallback(new CreationResponseCallback() {
            @Override
            public void onCompletion(CreationResponse response) {
                assertTrue(response.wasSuccess());
                assertEquals(list.getObjectId(), response.getObjectId());
            }
        }));
        waitThenAssertTestResults();

        CMSessionToken token = user.getSessionToken();

        CMObjectResponse response = service.getUserWebService(token).loadObject(anObject.getObjectId());
        assertTrue(response.hasSuccess());
        assertEquals(1, response.getObjects().size());
        assertEquals(anObject, response.getObject(anObject.getObjectId()));
    }
}
