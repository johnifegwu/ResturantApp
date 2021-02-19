package com.mickleentityltdnigeria.resturantapp.data.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Date;

@IgnoreExtraProperties
public class Payment implements Serializable {

    public String paymentID;
    public Date dateDue;
    public double amountDue;
    public String resturantType;

    public Payment(){}

    public Payment(String paymentID, Date dateDue, double amountDue, String resturantType) {
        this.paymentID = paymentID;
        this.dateDue = dateDue;
        this.amountDue = amountDue;
        this.resturantType = resturantType;
    }

    @Exclude
    public String getPaymentID() {
        return paymentID;
    }

    @Exclude
    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    @Exclude
    public Date getDateDue() {
        return dateDue;
    }

    @Exclude
    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    @Exclude
    public double getAmountDue() {
        return amountDue;
    }

    @Exclude
    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    @Exclude
    public String getResturantType() {
        return resturantType;
    }

    @Exclude
    public void setResturantType(String resturantType) {
        this.resturantType = resturantType;
    }
}
