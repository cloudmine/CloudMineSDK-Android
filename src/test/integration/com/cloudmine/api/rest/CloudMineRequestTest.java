package com.cloudmine.api.rest;

import com.android.volley.AuthFailureError;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.test.CloudMineTestRunner;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * <br>Copyright CloudMine, Inc. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
@RunWith(CloudMineTestRunner.class)
public class CloudMineRequestTest {

    @Test
    public void testCMApiCredentials() throws Exception{
        DeviceIdentifier.initialize(Robolectric.application);
        CMApiCredentials.initialize("appid", "apikey", "https://testing.api.cloudmine.me");
        CloudMineRequest request = new BaseObjectLoadRequest(Arrays.asList("objId"), null, null, null, null);
        assertContainsCredentials(request, "testing.api.cloudmine.me", "appid", "apikey");

        CMApiCredentials apiCredentials = new CMApiCredentials("difApp", "difApi", "https://rs.api.cloudmine.me/");
        request = new BaseObjectLoadRequest(Arrays.asList("objId"), null, apiCredentials, null, null, null);
        assertContainsCredentials(request, apiCredentials);

        request = new BaseObjectLoadRequest(Arrays.asList("objId"), null, null, null, null);
        assertContainsCredentials(request, "testing.api.cloudmine.me", "appid", "apikey");
    }

    protected void assertContainsCredentials(CloudMineRequest request,  CMApiCredentials credentials) throws AuthFailureError {
        assertContainsCredentials(request, credentials.getBaseUrl(), credentials.getIdentifier(), credentials.getApiKey());
    }

    protected void assertContainsCredentials(CloudMineRequest request,
                                             String baseUrl, String appid, String apikey) throws com.android.volley.AuthFailureError {
        assertTrue(request.getUrl().contains(baseUrl));
        assertTrue(request.getUrl().contains(appid));
        assertEquals(apikey, request.getHeaders().get(HeaderFactory.API_HEADER_KEY));
    }
}
