package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.PaymentResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

/**
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class BaseRemovePaymentMethodRequest extends CloudMineRequest<PaymentResponse> {
    public static final int REQUEST_TYPE = 419;

    private static final String URL = "/payments/account/methods/card/";

    @Expand
    public BaseRemovePaymentMethodRequest(int position, CMSessionToken sessionToken, @Optional CMServerFunction serverFunction, Response.Listener<PaymentResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.DELETE, URL + position, null, sessionToken, serverFunction, successListener, errorListener);
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
