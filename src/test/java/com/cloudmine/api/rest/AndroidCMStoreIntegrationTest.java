package com.cloudmine.api.rest;

import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.integration.CMStoreIntegrationTest;
import com.cloudmine.test.CloudMineTestRunner;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * <br>
 * Copyright CloudMine, Inc. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
@RunWith(CloudMineTestRunner.class)
public class AndroidCMStoreIntegrationTest extends CMStoreIntegrationTest {

    @Before
    public void setUp() {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        super.setUp();
        CloudMineRequest.setCachingEnabled(false);
        DeviceIdentifier.initialize(Robolectric.application.getApplicationContext());

    }
}
