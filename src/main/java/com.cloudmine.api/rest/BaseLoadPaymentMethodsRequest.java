package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.PaymentResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

/**
 * A Volley {@link com.android.volley.Request} for loading a user's payment methods
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class BaseLoadPaymentMethodsRequest extends CloudMineRequest<PaymentResponse> {
    public static final int REQUEST_TYPE = 416;

    private static final String URL = "/payments/account/methods";

    /**
     * Load all of the payment methods associated with the user whose sessionToken is passed in
     * @param sessionToken A valid session token from the user whose payment methods are being retrieved
     * @param apiCredentials optional Credentials to use instead of the default ones
     * @param serverFunction A nullable server function to be called after the payment methods are loaded
     * @param successListener callback called if the call succeeds on the server
     * @param errorListener callback called if the call fails (error response from server, no Internet, some other exception)
     */
    @Expand
    public BaseLoadPaymentMethodsRequest(CMSessionToken sessionToken, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, Response.Listener<PaymentResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.GET, URL, "", sessionToken, apiCredentials, serverFunction, successListener, errorListener);
    }

    @Override
    protected Response<PaymentResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new PaymentResponse(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry(networkResponse));
    }

    @Override
    public int getRequestType() {
        return REQUEST_TYPE;
    }
}
