package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMCreditCard;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.PaymentResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;
import me.cloudmine.annotations.Single;

import java.util.Collection;

/**
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class BaseAddPaymentMethodRequest extends CloudMineRequest<PaymentResponse> {
    public static final int REQUEST_TYPE = 415;

    private static final String URL = "/payments/account/methods/card";

    private static String toCreditCardArray(Collection<CMCreditCard> creditCards) {
        StringBuilder ccArray = new StringBuilder("{\"payments\":[");
        if(creditCards != null) {
            String separator = "";
            for(CMCreditCard card : creditCards) {
                ccArray.append(separator).append(card.getPaymentTransportRepresentation());
                separator = ", ";
            }
        }
        ccArray.append("]}");
        return ccArray.toString();
    }

    @Expand
    public BaseAddPaymentMethodRequest(@Single Collection<CMCreditCard> cards, CMSessionToken sessionToken, @Optional CMServerFunction serverFunction, Response.Listener<PaymentResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.POST, URL, toCreditCardArray(cards), sessionToken, serverFunction, successListener, errorListener);
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new PaymentResponse(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry(networkResponse));
    }

    @Override
    public int getRequestType() {
        return REQUEST_TYPE;
    }
}
