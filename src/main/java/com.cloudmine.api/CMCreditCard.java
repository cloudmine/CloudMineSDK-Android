package com.cloudmine.api;


import com.cloudmine.api.db.BaseLocallySavableCMObject;
import com.cloudmine.api.rest.JsonUtilities;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class CMCreditCard extends BaseLocallySavableCMObject {

    private String nameOnCard;
    private String token;
    private String expirationDate;
    private String last4Digits;
    private String type;

    public CMCreditCard() {}

    public CMCreditCard(String nameOnCard, String token, String expirationDate, String last4Digits, String type) {
        this.nameOnCard = nameOnCard;
        this.token = token;
        this.expirationDate = expirationDate;
        this.last4Digits = last4Digits;
        this.type = type;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    /**
     * Format: "0214" month/year
     * @param expirationDate
     */
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getLast4Digits() {
        return last4Digits;
    }

    public void setLast4Digits(String last4Digits) {
        this.last4Digits = last4Digits;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonIgnore
    public String getPaymentTransportRepresentation() {
        return JsonUtilities.objectToJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CMCreditCard that = (CMCreditCard) o;

        if (expirationDate != null ? !expirationDate.equals(that.expirationDate) : that.expirationDate != null)
            return false;
        if (last4Digits != null ? !last4Digits.equals(that.last4Digits) : that.last4Digits != null) return false;
        if (nameOnCard != null ? !nameOnCard.equals(that.nameOnCard) : that.nameOnCard != null) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nameOnCard != null ? nameOnCard.hashCode() : 0;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        result = 31 * result + (last4Digits != null ? last4Digits.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
