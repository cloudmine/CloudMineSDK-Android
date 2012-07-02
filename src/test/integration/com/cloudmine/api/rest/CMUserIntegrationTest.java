package com.cloudmine.api.rest;

import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.CMUser;
import com.cloudmine.api.rest.callbacks.CMResponseCallback;
import com.cloudmine.api.rest.response.CMResponse;
import com.cloudmine.api.rest.response.LoginResponse;
import com.cloudmine.api.rest.response.code.CMResponseCode;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ServiceTestBase;
import com.cloudmine.test.TestServiceCallback;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.cloudmine.test.AsyncTestResultsCoordinator.waitThenAssertTestResults;
import static junit.framework.Assert.*;

/**
 * <br>Copyright CloudMine LLC. All rights reserved<br> See LICENSE file included with SDK for details.
 */
@RunWith(CloudMineTestRunner.class)
public class CMUserIntegrationTest extends ServiceTestBase {

    @Test
    public void testLogin() throws ExecutionException, TimeoutException, InterruptedException {
        CMUser user = CMUser.CMUser("test13131313@test.com", "test");
        service.insert(user);

        LoginResponse loginResponse = user.login().get(10, TimeUnit.SECONDS);
        CMSessionToken token = loginResponse.getSessionToken();
        assertTrue(loginResponse.wasSuccess());
        assertTrue(user.isLoggedIn());
        assertFalse(CMSessionToken.INVALID_TOKEN.equals(token));
        user.login().get(50, TimeUnit.MILLISECONDS); //we're already logged in this should return FAST
    }

    @Test
    public void testLogout() {
        CMUser user = user();

        user.login(hasSuccess);
        waitThenAssertTestResults();

        user.logout(hasSuccess);
        waitThenAssertTestResults();
        assertFalse(user.isLoggedIn());
    }

    @Test
    public void testCreateUser() {
        CMUser user = CMUser.CMUser("@.notanEm!l", "pw");
        user.createUser(TestServiceCallback.testCallback(new CMResponseCallback() {
            public void onCompletion(CMResponse response) {
                assertEquals(CMResponseCode.INVALID_EMAIL_OR_MISSING_PASSWORD, response.getResponseCode());
            }
        }));
        waitThenAssertTestResults();
        user = CMUser.CMUser("vali@email.com", "");
        user.createUser(TestServiceCallback.testCallback(new CMResponseCallback() {
            public void onCompletion(CMResponse response) {
                assertEquals(CMResponseCode.INVALID_EMAIL_OR_MISSING_PASSWORD, response.getResponseCode());
            }
        }));
        waitThenAssertTestResults();
    }
}
