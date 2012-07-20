package com.cloudmine.api.rest;

import com.cloudmine.api.DeviceIdentifier;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class AndroidCMWebServiceIntegrationTest extends CloudMineWebServiceIntegrationTest {

    @Before
    public void setUp() {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        DeviceIdentifier.initialize(Robolectric.application.getApplicationContext());
        super.setUp();
    }
}
