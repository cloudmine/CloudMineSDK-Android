package com.cloudmine.api.rest;

import android.content.Context;
import com.cloudmine.api.CMGeoPointInterface;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.db.BaseLocallySavableCMGeoPoint;
import com.cloudmine.api.db.BaseLocallySavableCMObject;
import com.cloudmine.api.integration.CMGeoPointIntegrationTest;
import com.cloudmine.api.persistance.ClassNameRegistry;
import com.cloudmine.api.rest.response.ObjectModificationResponse;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ResponseCallbackTuple;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.cloudmine.test.AsyncTestResultsCoordinator.waitThenAssertTestResults;

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

    @Test
    public void testLoadingGeoThroughVolley() {
        ClassNameRegistry.register(CMGeoPointInterface.GEOPOINT_CLASS, BaseLocallySavableCMGeoPoint.class);
        BaseLocallySavableCMGeoPoint geoPoint = new BaseLocallySavableCMGeoPoint(33, 44);
        geoPoint.saveLocally(applicationContext);
        geoPoint.save(applicationContext, ResponseCallbackTuple.<ObjectModificationResponse>hasSuccess(), ResponseCallbackTuple.defaultFailureListener);
        waitThenAssertTestResults();

        BaseLocallySavableCMObject.loadObject(applicationContext, geoPoint.getObjectId(), ResponseCallbackTuple.wasLoaded(geoPoint), ResponseCallbackTuple.defaultFailureListener);
        waitThenAssertTestResults();
    }

}
