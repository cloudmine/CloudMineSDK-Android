package com.cloudmine.api.rest;

import android.content.Context;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.integration.CMGeoPointIntegrationTest;
import com.cloudmine.test.CloudMineTestRunner;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.runner.RunWith;
/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
@RunWith(CloudMineTestRunner.class)
public class AndroidCMGeoPointIntegrationTest extends CMGeoPointIntegrationTest {

    Context applicationContext;

    @Before
    public void setUp() {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        applicationContext = Robolectric.application.getApplicationContext();
        DeviceIdentifier.initialize(applicationContext);
        super.setUp();
    }

}
