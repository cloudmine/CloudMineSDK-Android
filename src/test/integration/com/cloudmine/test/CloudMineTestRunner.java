package com.cloudmine.test;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.runners.model.InitializationError;

/**
 * <br>Copyright CloudMine LLC. All rights reserved<br> See LICENSE file included with SDK for details.
 * CMUser: johnmccarthy
 * Date: 6/6/12, 4:30 PM
 */
public class CloudMineTestRunner extends RobolectricTestRunner {
    public CloudMineTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected void bindShadowClasses() {
        Robolectric.bindShadowClass(ShadowBase64.class);
    }
}
