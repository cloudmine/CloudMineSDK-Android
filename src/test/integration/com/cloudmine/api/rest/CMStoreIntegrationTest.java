package com.cloudmine.api.rest;

import com.cloudmine.api.SimpleCMObject;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ServiceTestBase;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Copyright CloudMine LLC
 * User: johnmccarthy
 * Date: 6/13/12, 3:51 PM
 */
@RunWith(CloudMineTestRunner.class)
public class CMStoreIntegrationTest extends ServiceTestBase {

    @Test
    public void testSaveObject() {
        SimpleCMObject object = SimpleCMObject.SimpleCMObject();
        object.add("bool", true);

        CMStore store = CMStore.CMStore();
        store.saveObject(object);
    }
}
