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
 * A Volley {@link com.android.volley.Request} for performing a card charge for a user specified by the given sessionToken
 * <br>Copyright CloudMine, Inc. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class BaseChargeCardRequest extends CloudMineRequest<PaymentResponse> {
    public static final int REQUEST_TYPE = 417;

    private static final String URL = "/payments/transaction/charge";

    /**
     * Charge the card at the specified index for the items in the given cart. The structure of the cart depends on your
     * server side checkout code, but will most likely be JSON.
     * @param cardIndex a 0 based index of which card to use. Use {@link com.cloudmine.api.rest.BaseLoadPaymentMethodsRequest} to load the various options for the given user
     * @param cart The cart that will be passed to the server side checkout functions
     * @param sessionToken The user's whose card (identified by cardIndex) will be charged
     * @param apiCredentials
     * @param serverFunction a server function to run after charging the card
     * @param successListener callback called if the call succeeds on the server
     * @param errorListener callback called if the call fails (error response from server, no Internet, some other exception)
     */
    @Expand
    public BaseChargeCardRequest(int cardIndex, String cart, CMSessionToken sessionToken,
                                 @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, @Optional Response.Listener<PaymentResponse> successListener, Response.ErrorListener errorListener) {
        super(Method.POST, URL, "{ \"cart\":" + cart + ", \"paymentInfo\":{\"index\":" + cardIndex + ", \"type\":\"card\"}}", sessionToken, apiCredentials, serverFunction, successListener, errorListener);
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
