package com.cloudmine.api.rest;

import android.content.Context;
import com.cloudmine.EnvironmentVariables;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMGeoPointInterface;
import com.cloudmine.api.db.LocallySavableCMGeoPoint;
import com.cloudmine.api.db.LocallySavableCMObject;
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
 * Copyright CloudMine, Inc. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
@RunWith(CloudMineTestRunner.class)
public class AndroidCMGeoPointIntegrationTest extends CMGeoPointIntegrationTest {

    Context applicationContext;

    @Before
    public void setUp() {
        applicationContext = Robolectric.application.getApplicationContext();
        CMApiCredentials.initialize(EnvironmentVariables.getCredentials().getIdentifier(), EnvironmentVariables.getCredentials().getApiKey(), applicationContext);
        CloudMineRequest.setCachingEnabled(false);
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);


        super.setUp();
    }

    @Test
    public void testLoadingGeoThroughVolley() {
        ClassNameRegistry.register(CMGeoPointInterface.GEOPOINT_CLASS, LocallySavableCMGeoPoint.class);
        LocallySavableCMGeoPoint geoPoint = new LocallySavableCMGeoPoint(33, 44);
        geoPoint.saveLocally(applicationContext);
        geoPoint.save(applicationContext, ResponseCallbackTuple.<ObjectModificationResponse>hasSuccess(), ResponseCallbackTuple.defaultFailureListener);
        waitThenAssertTestResults();

        LocallySavableCMObject.loadObject(applicationContext, geoPoint.getObjectId(), ResponseCallbackTuple.wasLoaded(geoPoint), ResponseCallbackTuple.defaultFailureListener);
        waitThenAssertTestResults();
    }

}
