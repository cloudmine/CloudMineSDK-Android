package com.cloudmine.api.db;

import android.content.Context;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.rest.JsonUtilities;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ExtendedCMObject;
import com.cloudmine.test.ExtendedLocallySavableCMObject;
import com.cloudmine.test.ExtendedLocallySavableGeopoint;
import com.cloudmine.test.ServiceTestBase;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.*;

@RunWith(CloudMineTestRunner.class)
public class LocallySavableCMObjectTest extends ServiceTestBase {


    @Before
    public void setUp() {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        DeviceIdentifier.initialize(Robolectric.application.getApplicationContext());
        LocallySavableCMObject.cmObjectDBOpenHelper = null;
        LocallySavableCMObject.requestDBOpenHelper = null;
        super.setUp();
    }

    @Test
    public void testSaveLocally() throws InterruptedException {
        Context context = Robolectric.application.getApplicationContext();
        ExtendedCMObject subobject = new ExtendedCMObject("bob", new Date(), 55);
        ExtendedLocallySavableCMObject savableCMObject = new ExtendedLocallySavableCMObject("Francis", true, subobject, 1000);
        boolean wasSaved = savableCMObject.saveLocally(context);
        assertTrue(wasSaved);

        ExtendedLocallySavableCMObject loadedObject = LocallySavableCMObject.loadLocalObject(context, savableCMObject.getObjectId());
        assertEquals(savableCMObject,  loadedObject);

        savableCMObject.setAwesome(false);
        Thread.sleep(500); //fix heisenbug.. assuming it is a problem with Robolectric and not our library ASSumptions
        wasSaved = savableCMObject.saveLocally(context);
        assertTrue(wasSaved);
        loadedObject = LocallySavableCMObject.loadLocalObject(context, savableCMObject.getObjectId());
        assertFalse(loadedObject.isAwesome());
    }

    @Test
    public void testLoadAllObjects() {
        Context context = Robolectric.application.getApplicationContext();
        ExtendedLocallySavableCMObject savableCMObject = new ExtendedLocallySavableCMObject("john", false, null, 2);
        savableCMObject.saveLocally(context);

        ExtendedLocallySavableGeopoint savableGeopoint = new ExtendedLocallySavableGeopoint("School", 55.2, 39.23);
        savableGeopoint.saveLocally(context);

        List<LocallySavableCMObject> loadedObjects = LocallySavableCMObject.loadLocalObjects(context);
        assertEquals(2, loadedObjects.size());
        for(LocallySavableCMObject object : loadedObjects) {
            if(object instanceof ExtendedLocallySavableCMObject) assertEquals(savableCMObject, object);
            else if(object instanceof ExtendedLocallySavableGeopoint) assertEquals(savableGeopoint, object);
            else fail();
        }
    }

    @Test
    public void testDelete() {

        Context context = Robolectric.application.getApplicationContext();
        ExtendedCMObject subobject = new ExtendedCMObject("bob", new Date(), 55);
        ExtendedLocallySavableCMObject savableCMObject = new ExtendedLocallySavableCMObject("Francis", true, subobject, 1000);
        boolean wasSaved = savableCMObject.saveLocally(context);
        assertTrue(wasSaved);

        LocallySavableCMObject.deleteLocalObject(context, savableCMObject.getObjectId());
        ExtendedLocallySavableCMObject loadedObject = LocallySavableCMObject.loadLocalObject(context, savableCMObject.getObjectId());
        assertNull(loadedObject);
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
