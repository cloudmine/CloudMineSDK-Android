package com.cloudmine.api.rest.response;

import com.cloudmine.api.CMCreditCard;
import com.cloudmine.api.rest.response.code.PaymentCode;
import org.apache.http.HttpResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <br>Copyright CloudMine, Inc. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class PaymentResponse extends ResponseBase<PaymentCode> {

    private List<CMCreditCard> creditCards;

    protected PaymentResponse(HttpResponse response) {
        super(response);
    }

    public PaymentResponse(String messageBody, int statusCode) {
        super(messageBody, statusCode);
    }

    @Override
    public PaymentCode getResponseCode() {
        return PaymentCode.codeForStatus(getStatusCode());
    }

    public String getStep() {
        Object step = getObject("step");
        return step == null ? "" : step.toString();
    }

    public boolean wasCompleted() {
        return "completed".equals(getStep());
    }

    public List<CMCreditCard> getCreditCards() {
        if(creditCards == null) {
            creditCards = new ArrayList<CMCreditCard>();
            Object cards = getObject("card");
            if(cards instanceof Collection) {
                for(Object card : (Collection)cards) {
                    if(card instanceof Map) {
                        Map<String, String> cardMap = (Map<String, String>) card;
                        String nameOnCard = cardMap.get("nameOnCard");
                        String token = cardMap.get("token");
                        String expirationDate = cardMap.get("expirationDate");
                        String last4Digits = cardMap.get("last4Digits");
                        String type = cardMap.get("type");
                        creditCards.add(new CMCreditCard(nameOnCard, token, expirationDate,last4Digits,type));
                    }
                }
            }
        }
        return creditCards;
    }
}
