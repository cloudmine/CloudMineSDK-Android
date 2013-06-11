package com.cloudmine.api;

import android.content.Context;
import com.cloudmine.api.db.Request;
import com.cloudmine.api.db.RequestDBOpenHelper;
import com.cloudmine.api.rest.CMWebService;
import com.cloudmine.api.rest.JsonUtilities;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ExtendedCMObject;
import com.cloudmine.test.ExtendedLocallySavableCMObject;
import com.cloudmine.test.ServiceTestBase;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(CloudMineTestRunner.class)
public class LocallySavableCMObjectTest extends ServiceTestBase {


    @Before
    public void setUp() {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        DeviceIdentifier.initialize(Robolectric.application.getApplicationContext());
        super.setUp();
    }

    @Test
    public void testSaveLocally() {
        Context context = Robolectric.application.getApplicationContext();
        ExtendedCMObject subobject = new ExtendedCMObject("bob", new Date(), 55);
        ExtendedLocallySavableCMObject savableCMObject = new ExtendedLocallySavableCMObject("Francis", true, subobject, 1000);
        boolean wasSaved = savableCMObject.saveLocally(context);
        assertTrue(wasSaved);

        ExtendedLocallySavableCMObject loadedObject = LocallySavableCMObject.loadObject(context, savableCMObject.getObjectId());
        assertEquals(savableCMObject,  loadedObject);
    }

    @Test
    public void testSaveEventually() throws InterruptedException {
        Context context = Robolectric.application.getApplicationContext();
        ExtendedCMObject subobject = new ExtendedCMObject("bob", new Date(), 55);
        ExtendedLocallySavableCMObject savableCMObject = new ExtendedLocallySavableCMObject("Francis", true, subobject, 1000);

        boolean wasStored = savableCMObject.saveEventually(context);
        assertTrue(wasStored);
        RequestDBOpenHelper openHelper = LocallySavableCMObject.getRequestDBOpenHelper(context);
        Map<Integer, Request> unsentRequests = openHelper.retrieveRequestsForSending(context);
        assertFalse(unsentRequests.isEmpty());
        Request saveRequest = unsentRequests.values().iterator().next();
        assertTrue(JsonUtilities.isJsonEquivalent(savableCMObject.transportableRepresentation(), saveRequest.getJsonBody()));
    }
}
