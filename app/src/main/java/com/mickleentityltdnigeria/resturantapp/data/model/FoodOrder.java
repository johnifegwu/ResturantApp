package com.mickleentityltdnigeria.resturantapp.data.model;

import java.io.Serializable;
import java.util.Date;

public class FoodOrder implements Serializable {

    private String orderID;
    private String userID;
    private Date oderDate;
    private String trackCode;
    private String paymentAddress;
    private String paymentCity;
    private String paymentZipCode;
    private String paymentState;
    private String PaymentCountry;
    private String shippingAddress;
    private String shippingCity;
    private String shippingZipCode;
    private String shippingState;
    private String shippingCountry;

    public FoodOrder() {
    }

    public FoodOrder(String orderID, String userID, Date oderDate, String trackCode, String paymentAddress, String paymentCity, String paymentZipCode, String paymentState, String paymentCountry, String shippingAddress, String shippingCity, String shippingZipCode, String shippingState, String shippingCountry) {
        this.orderID = orderID;
        this.userID = userID;
        this.oderDate = oderDate;
        this.trackCode = trackCode;
        this.paymentAddress = paymentAddress;
        this.paymentCity = paymentCity;
        this.paymentZipCode = paymentZipCode;
        this.paymentState = paymentState;
        PaymentCountry = paymentCountry;
        this.shippingAddress = shippingAddress;
        this.shippingCity = shippingCity;
        this.shippingZipCode = shippingZipCode;
        this.shippingState = shippingState;
        this.shippingCountry = shippingCountry;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getOderDate() {
        return oderDate;
    }

    public void setOderDate(Date oderDate) {
        this.oderDate = oderDate;
    }

    public String getTrackCode() {
        return trackCode;
    }

    public void setTrackCode(String trackCode) {
        this.trackCode = trackCode;
    }

    public String getPaymentAddress() {
        return paymentAddress;
    }

    public void setPaymentAddress(String paymentAddress) {
        this.paymentAddress = paymentAddress;
    }

    public String getPaymentCity() {
        return paymentCity;
    }

    public void setPaymentCity(String paymentCity) {
        this.paymentCity = paymentCity;
    }

    public String getPaymentZipCode() {
        return paymentZipCode;
    }

    public void setPaymentZipCode(String paymentZipCode) {
        this.paymentZipCode = paymentZipCode;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    public String getPaymentCountry() {
        return PaymentCountry;
    }

    public void setPaymentCountry(String paymentCountry) {
        PaymentCountry = paymentCountry;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public String getShippingZipCode() {
        return shippingZipCode;
    }

    public void setShippingZipCode(String shippingZipCode) {
        this.shippingZipCode = shippingZipCode;
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }

    public void setShippingCountry(String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }
}
