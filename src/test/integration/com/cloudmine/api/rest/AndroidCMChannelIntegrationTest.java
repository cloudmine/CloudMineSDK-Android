package com.cloudmine.api.rest;

import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.integration.CMChannelIntegrationTest;
import com.cloudmine.test.CloudMineTestRunner;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.Robolectric;
/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
@RunWith(CloudMineTestRunner.class)
public class AndroidCMChannelIntegrationTest extends CMChannelIntegrationTest {

    @Before
    public void setUp() {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        CMApiCredentials.initialize(APP_ID, API_KEY, Robolectric.application.getApplicationContext());
        super.setUp();
    }

}
