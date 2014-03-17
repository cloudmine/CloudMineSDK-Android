package com.cloudmine.api.rest.callbacks;

import com.cloudmine.api.exceptions.CreationException;
import com.cloudmine.api.rest.response.PaymentResponse;
import com.cloudmine.api.rest.response.ResponseBase;
import com.cloudmine.api.rest.response.ResponseConstructor;
import org.apache.http.HttpResponse;

/**
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class PaymentResponseCallback extends CMCallback<PaymentResponse> {
    public static final ResponseConstructor<PaymentResponse> CONSTRUCTOR = new ResponseConstructor<PaymentResponse>() {
        @Override
        public PaymentResponse construct(HttpResponse response) throws CreationException {
            return new PaymentResponse(ResponseBase.readMessageBody(response), response.getStatusLine().getStatusCode());
        }

        @Override
        public PaymentResponse construct(String messageBody, int responseCode) throws CreationException {
            return new PaymentResponse(messageBody, responseCode);
        }
    };
    /**
     * Classes that extend this should have a noargs constructor that provides this constructor based on T
     *

     */
    public PaymentResponseCallback() {
        super(CONSTRUCTOR);
    }
}
