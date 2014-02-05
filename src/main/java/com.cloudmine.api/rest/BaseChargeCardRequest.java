package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.response.PaymentResponse;
import me.cloudmine.annotations.Optional;

/**
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class BaseChargeCardRequest extends CloudMineRequest<PaymentResponse> {
    public static final int REQUEST_TYPE = 417;

    private static final String URL = "/payments/transaction/charge";

    public BaseChargeCardRequest(int cardIndex, String cart, CMSessionToken sessionToken, @Optional Response.Listener<PaymentResponse> successListener, Response.ErrorListener errorListener) {
        super(Method.POST, URL, "{ \"cart\":" + cart + ", \"paymentInfo\":{\"index\":" + cardIndex + ", \"type\":\"card\"}}", sessionToken, null, successListener, errorListener);
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
