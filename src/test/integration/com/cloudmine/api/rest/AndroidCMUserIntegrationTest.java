package com.cloudmine.api.rest;

import android.content.Context;
import com.android.volley.Response;
import com.cloudmine.api.ACMUser;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.integration.CMUserIntegrationTest;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.CreationResponse;
import com.cloudmine.api.rest.response.LoginResponse;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ExtendedACMUser;
import com.cloudmine.test.ResponseCallbackTuple;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.cloudmine.test.AsyncTestResultsCoordinator.waitThenAssertTestResults;
import static com.cloudmine.test.ResponseCallbackTuple.defaultFailureListener;
import static com.cloudmine.test.ResponseCallbackTuple.testCallback;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
@RunWith(CloudMineTestRunner.class)
public class AndroidCMUserIntegrationTest extends CMUserIntegrationTest{

    Context applicationContext;

    @Before
    public void setUp() {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        applicationContext = Robolectric.application.getApplicationContext();
        DeviceIdentifier.initialize(applicationContext);
        super.setUp();
    }

    @Test
    public void testUserCreationRequest() {
        ExtendedACMUser user = new ExtendedACMUser(randomEmail(), randomString());

        user.create(applicationContext, testCallback(new Response.Listener<CreationResponse>() {
            @Override
            public void onResponse(CreationResponse creationResponse) {
                assertTrue(creationResponse.wasSuccess());
            }
        }), defaultFailureListener);
        waitThenAssertTestResults();

        user.login(hasSuccess);
        waitThenAssertTestResults();
    }

    @Test
    public void testUserLoginRequest() {
        String password = randomString();
        ExtendedACMUser user = new ExtendedACMUser(randomEmail(), password);

        testCreateAndLogin(user);

        ACMUser usernameUser = new ACMUser(null, randomString(), randomString());
        testCreateAndLogin(usernameUser);
    }

    @Test
    public void testUserProfileRequest() {
        String email = randomEmail();
        String password = randomString();
        ExtendedACMUser user = new ExtendedACMUser(email, password, false, 5);
        user.create(applicationContext, ResponseCallbackTuple.<CreationResponse>hasSuccess(), defaultFailureListener);
        waitThenAssertTestResults();

        ExtendedACMUser reloadedUser = new ExtendedACMUser(email, password, true, 100000);
        reloadedUser.login(applicationContext, ResponseCallbackTuple.<LoginResponse>hasSuccess(), defaultFailureListener);
        waitThenAssertTestResults();

        assertEquals(user.getPoints(), reloadedUser.getPoints());
        assertEquals(user.isHasProperty(), reloadedUser.isHasProperty());

        reloadedUser.setPoints(10000);
        reloadedUser.saveProfile(applicationContext, ResponseCallbackTuple.<CreationResponse>hasSuccess(), defaultFailureListener);
        waitThenAssertTestResults();

        user.login(applicationContext, ResponseCallbackTuple.<LoginResponse>hasSuccess(), defaultFailureListener);
        waitThenAssertTestResults();

        user.loadProfile(applicationContext, ResponseCallbackTuple.testCallback(new Response.Listener<CMObjectResponse>() {
            @Override
            public void onResponse(CMObjectResponse objectResponse) {
                List<ExtendedACMUser> profiles = objectResponse.getObjects (ExtendedACMUser.class);
                assertEquals(1, profiles.size());
                assertEquals(10000, profiles.get(0).getPoints());
            }
        }), defaultFailureListener);
        waitThenAssertTestResults();

        assertEquals(10000, user.getPoints());
    }

    private void testCreateAndLogin(ACMUser user) {
        user.create(applicationContext, ResponseCallbackTuple.<CreationResponse>hasSuccess(), defaultFailureListener);
        waitThenAssertTestResults();

        user.login(applicationContext, ResponseCallbackTuple.<LoginResponse>hasSuccess(), defaultFailureListener);
        waitThenAssertTestResults();

        assertTrue(user.getSessionToken().isValid());
    }
}
