package com.cloudmine.api.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMCreditCard;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.PaymentResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;
import me.cloudmine.annotations.Single;

import java.util.Collection;

/**
 * A Volley {@link com.android.volley.Request} for adding a payment method for a user. Requires a session token
 * <br>Copyright CloudMine, Inc. All rights reserved
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


    /**
     * Add all of the specified {@link com.cloudmine.api.CMCreditCard} to the user associated with the given {@link com.cloudmine.api.CMSessionToken}
     * @param cards A collection of the cards to add
     * @param sessionToken a valid session token. The credit cards will be added to the user whose session token this is
     * @param apiCredentials nullable CMApiCredentials to override the defaults set when the app is initialized
     * @param serverFunction A nullable server function to call after adding the credit cards to the user
     * @param successListener callback called if the call succeeds on the server
     * @param errorListener callback called if the call fails (error response from server, no Internet, some other exception)
     */
    @Expand
    public BaseAddPaymentMethodRequest(@Single Collection<CMCreditCard> cards, CMSessionToken sessionToken, @Optional CMApiCredentials apiCredentials, @Optional CMServerFunction serverFunction, Response.Listener<PaymentResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.POST, URL, toCreditCardArray(cards), sessionToken, apiCredentials, serverFunction, successListener, errorListener);
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
