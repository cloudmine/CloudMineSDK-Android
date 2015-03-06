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
 * A Volley {@link com.android.volley.Request} for removing a payment method from a user
 * <br>Copyright CloudMine, Inc. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class BaseRemovePaymentMethodRequest extends CloudMineRequest<PaymentResponse> {
    public static final int REQUEST_TYPE = 419;

    private static final String URL = "/payments/account/methods/card/";

    /**
     * Remove the card at the specified position (0 indexed) belonging to the user associated with the specified sessionToken.
     * @param cardIndex a 0 based index of which card to use. Use {@link com.cloudmine.api.rest.BaseLoadPaymentMethodsRequest} to load the various options for the given user
     * @param sessionToken The user's whose card (identified by cardIndex) will be charged
     * @param serverFunction a nullable server function to be invoked after the payment method has been removed
     * @param successListener callback called if the call succeeds on the server
     * @param errorListener callback called if the call fails (error response from server, no Internet, some other exception)
     */
    @Expand
    public BaseRemovePaymentMethodRequest(int cardIndex, CMSessionToken sessionToken, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, Response.Listener<PaymentResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.DELETE, URL + cardIndex, null, sessionToken, apiCredentials, serverFunction, successListener, errorListener);

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
