package com.mickleentityltdnigeria.resturantapp.utils;

import java.io.Serializable;

public class PaymentDetails implements Serializable {

    public String authCode;
    public double amountPaid;
    public double amountCollected;
    public double changeGiven;
    public String paymentMedium;
    public String paymentNote;
    public String itemsCollectedBy;

    public PaymentDetails() {
    }

    public PaymentDetails(String authCode, double amountPaid, double amountCollected, double changeGiven, String paymentMedium, String paymentNote, String itemsCollectedBy) {
        this.authCode = authCode;
        this.amountPaid = amountPaid;
        this.amountCollected = amountCollected;
        this.changeGiven = changeGiven;
        this.paymentMedium = paymentMedium;
        this.paymentNote = paymentNote;
        this.itemsCollectedBy = itemsCollectedBy;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getAmountCollected() {
        return amountCollected;
    }

    public void setAmountCollected(double amountCollected) {
        this.amountCollected = amountCollected;
    }

    public double getChangeGiven() {
        return changeGiven;
    }

    public void setChangeGiven(double changeGiven) {
        this.changeGiven = changeGiven;
    }

    public String getPaymentMedium() {
        return paymentMedium;
    }

    public void setPaymentMedium(String paymentMedium) {
        this.paymentMedium = paymentMedium;
    }

    public String getPaymentNote() {
        return paymentNote;
    }

    public void setPaymentNote(String paymentNote) {
        this.paymentNote = paymentNote;
    }

    public String getItemsCollectedBy() {
        return itemsCollectedBy;
    }

    public void setItemsCollectedBy(String itemsCollectedBy) {
        this.itemsCollectedBy = itemsCollectedBy;
    }
}
