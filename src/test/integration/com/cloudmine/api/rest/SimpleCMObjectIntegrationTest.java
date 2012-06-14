package com.cloudmine.api.rest;

import com.cloudmine.api.SimpleCMObject;
import com.cloudmine.api.rest.response.SimpleCMObjectResponse;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ServiceTestBase;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Copyright CloudMine LLC
 * User: johnmccarthy
 * Date: 6/14/12, 10:53 AM
 */
@RunWith(CloudMineTestRunner.class)
public class SimpleCMObjectIntegrationTest extends ServiceTestBase {

    @Test
    public void testDefaultSave() {
        SimpleCMObject object = new SimpleCMObject();
        object.add("string", "value");
        object.save();

        SimpleCMObjectResponse response = AndroidCMWebService.service().get(object.key());
        Assert.assertTrue(response.wasSuccess());
        SimpleCMObject loadedObject = response.object(object.key());
        assertEquals(object, loadedObject);
    }
}
