package com.cloudmine.test;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricConfig;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.util.SQLiteMap;
import org.junit.runners.model.InitializationError;

import java.io.File;

/**
 * <br>Copyright CloudMine LLC. All rights reserved<br> See LICENSE file included with SDK for details.
 * CMUser: johnmccarthy
 * Date: 6/6/12, 4:30 PM
 */
public class CloudMineTestRunner extends RobolectricTestRunner {
    public CloudMineTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass, new RobolectricConfig(new File(".")), new SQLiteMap());
    }

    @Override
    protected void bindShadowClasses() {
        Robolectric.bindShadowClass(ShadowBase64.class);
    }
}
