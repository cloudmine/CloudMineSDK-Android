package com.cloudmine.api.rest;

import com.cloudmine.api.CMUser;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ServiceTestBase;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <br>Copyright CloudMine LLC. All rights reserved<br> See LICENSE file included with SDK for details.
 * User: johnmccarthy
 * Date: 6/14/12, 4:11 PM
 */
@RunWith(CloudMineTestRunner.class)
public class CMUserIntegrationTest extends ServiceTestBase {

    @Test
    public void testLogin() {
        CMUser user = CMUser.CMUser("test@test.com", "test");
        service.insert(user);

        user.login();
    }
}
