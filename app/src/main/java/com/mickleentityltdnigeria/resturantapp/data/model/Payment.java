package com.mickleentityltdnigeria.resturantapp.data.model;

import java.io.Serializable;
import java.util.Date;

public class Payment implements Serializable {

    private int paymentID;
    private Date dateDue;
    private double amountDue;
    private String resturantType;

    public Payment(){}

    public Payment(int paymentID, Date dateDue, double amountDue, String resturantType) {
        this.paymentID = paymentID;
        this.dateDue = dateDue;
        this.amountDue = amountDue;
        this.resturantType = resturantType;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public String getResturantType() {
        return resturantType;
    }

    public void setResturantType(String resturantType) {
        this.resturantType = resturantType;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentID=" + paymentID +
                ", dateDue=" + dateDue +
                ", amountDue=" + amountDue +
                ", resturantType='" + resturantType + '\'' +
                '}';
    }
}
