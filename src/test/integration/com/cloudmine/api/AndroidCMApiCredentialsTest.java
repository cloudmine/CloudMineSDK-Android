package com.cloudmine.api;

import com.cloudmine.test.CloudMineTestRunner;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * Created with IntelliJ IDEA.
 * User: johnmccarthy
 * Date: 5/2/13
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(CloudMineTestRunner.class)
public class AndroidCMApiCredentialsTest extends CMApiCredentialsTest {

    @Before
    public void setUp() {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        DeviceIdentifier.initialize(Robolectric.application.getApplicationContext());
        super.setUp();
    }
}
