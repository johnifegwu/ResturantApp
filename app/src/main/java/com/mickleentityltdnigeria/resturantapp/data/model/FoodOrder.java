package com.mickleentityltdnigeria.resturantapp.data.model;

import java.io.Serializable;
import java.util.Date;

public class FoodOrder implements Serializable {

    private int orderID;
    private int userID;
    private Date oderDate;
    private int resturantID;
    private String trackCode;
    private String paymentAddress;
    private String paymentCity;
    private String paymentZipCode;
    private String paymentState;
    private String PaymentCountry;
    private boolean isPaid;
    private String shippingAddress;
    private String shippingCity;
    private String shippingZipCode;
    private String shippingState;
    private String shippingCountry;
    private boolean isShipped;
    private Date dateShipped;
    private String shippedBy;
    private boolean isDelivered;
    private Date dateDelivered;
    private String deliveredBy;

    public FoodOrder(int orderID, int userID, Date oderDate, int resturantID, String trackCode, String paymentAddress, String paymentCity, String paymentZipCode, String paymentState, String paymentCountry, boolean isPaid, String shippingAddress, String shippingCity, String shippingZipCode, String shippingState, String shippingCountry, boolean isShipped, Date dateShipped, String shippedBy, boolean isDelivered, Date dateDelivered, String deliveredBy) {
        this.orderID = orderID;
        this.userID = userID;
        this.oderDate = oderDate;
        this.resturantID = resturantID;
        this.trackCode = trackCode;
        this.paymentAddress = paymentAddress;
        this.paymentCity = paymentCity;
        this.paymentZipCode = paymentZipCode;
        this.paymentState = paymentState;
        PaymentCountry = paymentCountry;
        this.isPaid = isPaid;
        this.shippingAddress = shippingAddress;
        this.shippingCity = shippingCity;
        this.shippingZipCode = shippingZipCode;
        this.shippingState = shippingState;
        this.shippingCountry = shippingCountry;
        this.isShipped = isShipped;
        this.dateShipped = dateShipped;
        this.shippedBy = shippedBy;
        this.isDelivered = isDelivered;
        this.dateDelivered = dateDelivered;
        this.deliveredBy = deliveredBy;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getOderDate() {
        return oderDate;
    }

    public void setOderDate(Date oderDate) {
        this.oderDate = oderDate;
    }

    public int getResturantID() {
        return resturantID;
    }

    public void setResturantID(int resturantID) {
        this.resturantID = resturantID;
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

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
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

    public boolean isShipped() {
        return isShipped;
    }

    public void setShipped(boolean shipped) {
        isShipped = shipped;
    }

    public Date getDateShipped() {
        return dateShipped;
    }

    public void setDateShipped(Date dateShipped) {
        this.dateShipped = dateShipped;
    }

    public String getShippedBy() {
        return shippedBy;
    }

    public void setShippedBy(String shippedBy) {
        this.shippedBy = shippedBy;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public Date getDateDelivered() {
        return dateDelivered;
    }

    public void setDateDelivered(Date dateDelivered) {
        this.dateDelivered = dateDelivered;
    }

    public String getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(String deliveredBy) {
        this.deliveredBy = deliveredBy;
    }
}
