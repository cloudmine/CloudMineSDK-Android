package com.cloudmine.api.rest;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.integration.CMObjectIntegrationTest;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ExtendedCMObject;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
@RunWith(CloudMineTestRunner.class)
public class AndroidCMObjectIntegrationTest extends CMObjectIntegrationTest{

    RequestQueue queue;

    @Before
    public void setUp() {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        Context applicationContext = Robolectric.application.getApplicationContext();
        DeviceIdentifier.initialize(applicationContext);
        queue = Volley.newRequestQueue(applicationContext);
        super.setUp();
    }

    @Test
    public void testObjectModificationRequest() {

    }

    @Test
    public void testObjectLoadRequest() {
        ExtendedCMObject object = new ExtendedCMObject("John", 26);
        service.insert(object.transportableRepresentation());

        ObjectLoadRequest objectLoadRequest;

    }
}
