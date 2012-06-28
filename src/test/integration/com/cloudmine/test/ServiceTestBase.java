package com.cloudmine.test;

import com.cloudmine.api.*;
import com.cloudmine.api.rest.AndroidCMWebService;
import com.cloudmine.api.rest.CMWebService;
import com.cloudmine.api.rest.Savable;
import com.cloudmine.api.rest.callbacks.CMResponseCallback;
import com.cloudmine.api.rest.callbacks.ObjectModificationResponseCallback;
import com.cloudmine.api.rest.callbacks.SimpleCMObjectResponseCallback;
import com.cloudmine.api.rest.response.CMResponse;
import com.cloudmine.api.rest.response.ObjectModificationResponse;
import com.cloudmine.api.rest.response.SimpleCMObjectResponse;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;

import java.io.*;

import static com.cloudmine.test.AsyncTestResultsCoordinator.reset;
import static com.cloudmine.test.TestServiceCallback.testCallback;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * <br>Copyright CloudMine LLC. All rights reserved<br> See LICENSE file included with SDK for details.
 * User: johnmccarthy
 * Date: 6/14/12, 11:13 AM
 */
public class ServiceTestBase {
    private static final String APP_ID = "c1a562ee1e6f4a478803e7b51babe287";
    private static final String API_KEY = "3fc494b36d6d432d9afb051d819bdd72";
    private static final CMUser user = CMUser.CMUser("tfjghkdfgjkdf@gmail.com", "test");

    public static final TestServiceCallback hasSuccess = testCallback(new CMResponseCallback() {
        public void onCompletion(CMResponse response) {
            assertTrue(response.wasSuccess());
        }
    });

    public static TestServiceCallback hasSuccessAndHasModified(final Savable... savables) {
        return testCallback(new ObjectModificationResponseCallback() {
           public void onCompletion(ObjectModificationResponse response) {
               assertTrue(response.wasSuccess());
               for(Savable saved : savables) {
                   assertTrue(response.wasModified(saved.getObjectId()));
               }
           }
        });
    }

    public static TestServiceCallback hasSuccessAndHasLoaded(final Savable... savables) {
        return testCallback(new SimpleCMObjectResponseCallback() {
           public void onCompletion(SimpleCMObjectResponse response) {
               assertTrue(response.wasSuccess());
               for(Savable loaded : savables) {
                   assertEquals(loaded, response.getSimpleCMObject(loaded.getObjectId()));
               }
           }
        });
    }

    protected CMWebService service;
    @Before
    public void setUp() {
        System.setProperty("org.slf4j.simplelogger.defaultlog", "debug");
        reset();
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        DeviceIdentifier.initialize(Robolectric.application.getApplicationContext());
        CMApiCredentials.initialize(APP_ID, API_KEY);
        service = AndroidCMWebService.getService();
        deleteAll();
    }

    private void deleteAll() {
        service.deleteAll();
        CMUser user = user();
        CMSessionToken token = service.login(user).getSessionToken();
        service.getUserWebService(token).deleteAll();
    }

    public CMUser user() {
        return user;
    }

    public SimpleCMObject simpleUserObject() {
        SimpleCMObject object = simpleObject();
        object.setSaveWith(user());
        return object;
    }

    public SimpleCMObject simpleObject() {
        SimpleCMObject object = SimpleCMObject.SimpleCMObject();
        object.add("string", "value");
        object.add("bool", true);
        object.add("int", 5);
        return object;
    }


    public InputStream getObjectInputStream() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ObjectOutputStream objectOutput = new ObjectOutputStream(output);
        objectOutput.write(55);
        objectOutput.writeObject("Some String is Written");
        objectOutput.flush();
        objectOutput.close();

        return new ByteArrayInputStream(output.toByteArray());
    }
}
