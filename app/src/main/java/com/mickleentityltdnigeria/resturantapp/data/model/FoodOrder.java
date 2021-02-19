package com.mickleentityltdnigeria.resturantapp.data.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Date;

@IgnoreExtraProperties
public class FoodOrder implements Serializable {

    public String orderID;
    public String userID;
    public Date oderDate;
    public String trackCode;
    public String paymentAddress;
    public String paymentCity;
    public String paymentZipCode;
    public String paymentState;
    public String PaymentCountry;
    public String shippingAddress;
    public String shippingCity;
    public String shippingZipCode;
    public String shippingState;
    public String shippingCountry;

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

    @Exclude
    public String getOrderID() {
        return orderID;
    }

    @Exclude
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    @Exclude
    public String getUserID() {
        return userID;
    }

    @Exclude
    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Exclude
    public Date getOderDate() {
        return oderDate;
    }

    @Exclude
    public void setOderDate(Date oderDate) {
        this.oderDate = oderDate;
    }

    @Exclude
    public String getTrackCode() {
        return trackCode;
    }

    @Exclude
    public void setTrackCode(String trackCode) {
        this.trackCode = trackCode;
    }

    @Exclude
    public String getPaymentAddress() {
        return paymentAddress;
    }

    @Exclude
    public void setPaymentAddress(String paymentAddress) {
        this.paymentAddress = paymentAddress;
    }

    @Exclude
    public String getPaymentCity() {
        return paymentCity;
    }

    @Exclude
    public void setPaymentCity(String paymentCity) {
        this.paymentCity = paymentCity;
    }

    @Exclude
    public String getPaymentZipCode() {
        return paymentZipCode;
    }

    @Exclude
    public void setPaymentZipCode(String paymentZipCode) {
        this.paymentZipCode = paymentZipCode;
    }

    @Exclude
    public String getPaymentState() {
        return paymentState;
    }

    @Exclude
    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    @Exclude
    public String getPaymentCountry() {
        return PaymentCountry;
    }

    @Exclude
    public void setPaymentCountry(String paymentCountry) {
        PaymentCountry = paymentCountry;
    }

    @Exclude
    public String getShippingAddress() {
        return shippingAddress;
    }

    @Exclude
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Exclude
    public String getShippingCity() {
        return shippingCity;
    }

    @Exclude
    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    @Exclude
    public String getShippingZipCode() {
        return shippingZipCode;
    }

    @Exclude
    public void setShippingZipCode(String shippingZipCode) {
        this.shippingZipCode = shippingZipCode;
    }

    @Exclude
    public String getShippingState() {
        return shippingState;
    }

    @Exclude
    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }

    @Exclude
    public String getShippingCountry() {
        return shippingCountry;
    }

    @Exclude
    public void setShippingCountry(String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }
}
