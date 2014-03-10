package com.cloudmine.api.rest;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.cloudmine.api.ACMUser;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.LibrarySpecificClassCreator;
import com.cloudmine.api.rest.response.LoginResponse;
import com.cloudmine.api.rest.response.PaymentResponse;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ServiceTestBase;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.cloudmine.test.AsyncTestResultsCoordinator.waitThenAssertTestResults;
import static com.cloudmine.test.ResponseCallbackTuple.defaultFailureListener;
import static com.cloudmine.test.ResponseCallbackTuple.testCallback;
import static org.junit.Assert.assertTrue;

/**
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
@RunWith(CloudMineTestRunner.class)
public class AndroidCMPaymentsIntegrationTest extends ServiceTestBase {

    //EasyPay
    private static final String APP_ID = "245008ea0b494bdca5874e80e2bc1c69";
    private static final String API_KEY = "c755a72ae9d94a91933ae49d3af26847";

    RequestQueue queue;
    Context applicationContext;

    @Before
    public void setUp() {
        applicationContext = Robolectric.application.getApplicationContext();
        CMApiCredentials.initialize(APP_ID, API_KEY, applicationContext);
        CloudMineRequest.setCachingEnabled(false);
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        DeviceIdentifier.initialize(applicationContext);
        queue = SharedRequestQueueHolders.getRequestQueue(applicationContext);
        super.setUp();
        CMApiCredentials.initialize(APP_ID, API_KEY, applicationContext);
        service = new CMWebService(CMApiCredentials.getApplicationIdentifier(), LibrarySpecificClassCreator.getCreator().getAsynchronousHttpClient());
    }

    @Test
    public void testChargeCardRequest() {
        ACMUser user = new ACMUser("test@test.com", "testing");
        LoginResponse response = service.login(user);
        assertTrue(response.wasSuccess());
        String cartJson = "{\n" +
                "    \"custid\": \"cleanshow\",\n" +
                "    \"discounts\":     {\n" +
                "        \"loyalty\": 0\n" +
                "    },\n" +
                "    \"items\":     [\n" +
                "                {\n" +
                "            \"mid\": \"40\",\n" +
                "            \"vend\":             {\n" +
                "                \"minutes\": 12\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"location\":     {\n" +
                "        \"lat\": 37.785834,\n" +
                "        \"lon\": -122.406417\n" +
                "    },\n" +
                "    \"locid\": \"1\",\n" +
                "    \"payment\":     {\n" +
                "        \"cc_id\": 0,\n" +
                "        \"cc_last4\": \"2040\"\n" +
                "    }\n" +
                "}\n";
        //cartJson = "{\"items\":[{\"mid\":\"24\",\"vend\":{\"minutes\":\"20\"}}],\"discounts\":{\"loyalty\":0},\"location\":{\"lat\":37.785834,\"lon\":-122.406417},\"payment\":{\"cc_id\":\"0\",\"cc_last4\":\"2040\"},\"custid\":\"cleanshow\",\"locid\":\"1\"}";
        CloudMineRequest request = new BaseChargeCardRequest(0, cartJson, response.getSessionToken(), testCallback(new Response.Listener<PaymentResponse>() {
            @Override
            public void onResponse(PaymentResponse response) {
                assertTrue(response.wasSuccess());
                assertTrue(response.wasCompleted());
            }
        }), defaultFailureListener);
        queue.add(request);
        waitThenAssertTestResults();
    }
}
