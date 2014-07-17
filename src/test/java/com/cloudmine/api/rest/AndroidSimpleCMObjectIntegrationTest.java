package com.cloudmine.api.rest;

import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.integration.SimpleCMObjectIntegrationTest;
import com.cloudmine.test.CloudMineTestRunner;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
@RunWith(CloudMineTestRunner.class)
public class AndroidSimpleCMObjectIntegrationTest extends SimpleCMObjectIntegrationTest{

    @Before
    public void setUp() {
        CMApiCredentials.initialize(APP_ID, API_KEY, Robolectric.application);
        CloudMineRequest.setCachingEnabled(false);
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        DeviceIdentifier.initialize(Robolectric.application.getApplicationContext());
        super.setUp();
    }

    @Test
    public void testAndroidInitialize() {
        CMApiCredentials.initialize("aId", "aKey", Robolectric.application);
        assertEquals("aId", CMApiCredentials.getApplicationIdentifier());
        assertEquals("aKey", CMApiCredentials.getApplicationApiKey());
    }
}
