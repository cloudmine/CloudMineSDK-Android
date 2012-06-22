package com.cloudmine.test;

import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.CMUser;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.rest.AndroidCMWebService;
import com.cloudmine.api.rest.CMWebService;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;

import static com.cloudmine.test.AsyncTestResultsCoordinator.reset;

/**
 * <br>Copyright CloudMine LLC. All rights reserved<br> See LICENSE file included with SDK for details.
 * User: johnmccarthy
 * Date: 6/14/12, 11:13 AM
 */
public class ServiceTestBase {
    private static final String APP_ID = "c1a562ee1e6f4a478803e7b51babe287";
    private static final String API_KEY = "3fc494b36d6d432d9afb051d819bdd72";
    private static final CMUser user = CMUser.CMUser("tfjghkdfgjkdf@gmail.com", "test");
    protected CMWebService service;
    @Before
    public void setUp() {
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
}
