package com.mickleentityltdnigeria.resturantapp.data.model;

import java.io.Serializable;
import java.util.Date;

public class Resturant implements Serializable {

    private int resturantID;
    private String resturantName;
    private String resturantType;
    private String resturantLongitude;
    private String resturantLatitude;
    private byte[] resturantImg;
    private String resturantImgUrl;
    private String address;
    private String city;
    private String zipCode;
    private String state;
    private String country;
    private String contactPerson;
    private String phone;
    private String email;
    private String websiteUrl;
    private boolean paid;
    private Date lastPaidDate;
    private double amountPaid;
    private String paymentChannel;
    private Date nextPaymentDueDate;

    public Resturant(){}

    public Resturant(int resturantID, String resturantName, String resturantType, String resturantLongitude, String resturantLatitude, byte[] resturantImg, String resturantImgUrl, String address, String city, String zipCode, String state, String country, String contactPerson, String phone, String email, String websiteUrl, boolean paid, Date lastPaidDate, double amountPaid, String paymentChannel, Date nextPaymentDueDate) {
        this.resturantID = resturantID;
        this.resturantName = resturantName;
        this.resturantType = resturantType;
        this.resturantLongitude = resturantLongitude;
        this.resturantLatitude = resturantLatitude;
        this.resturantImg = resturantImg;
        this.resturantImgUrl = resturantImgUrl;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
        this.country = country;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.email = email;
        this.websiteUrl = websiteUrl;
        this.paid = paid;
        this.lastPaidDate = lastPaidDate;
        this.amountPaid = amountPaid;
        this.paymentChannel = paymentChannel;
        this.nextPaymentDueDate = nextPaymentDueDate;
    }

    public int getResturantID() {
        return resturantID;
    }

    public void setResturantID(int resturantID) {
        this.resturantID = resturantID;
    }

    public String getResturantName() {
        return resturantName;
    }

    public void setResturantName(String resturantName) {
        this.resturantName = resturantName;
    }

    public String getResturantType() {
        return resturantType;
    }

    public void setResturantType(String resturantType) {
        this.resturantType = resturantType;
    }

    public String getResturantLongitude() {
        return resturantLongitude;
    }

    public void setResturantLongitude(String resturantLongitude) {
        this.resturantLongitude = resturantLongitude;
    }

    public String getResturantLatitude() {
        return resturantLatitude;
    }

    public void setResturantLatitude(String resturantLatitude) {
        this.resturantLatitude = resturantLatitude;
    }

    public byte[] getResturantImg() {
        return resturantImg;
    }

    public void setResturantImg(byte[] resturantImg) {
        this.resturantImg = resturantImg;
    }

    public String getResturantImgUrl() {
        return resturantImgUrl;
    }

    public void setResturantImgUrl(String resturantImgUrl) {
        this.resturantImgUrl = resturantImgUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Date getLastPaidDate() {
        return lastPaidDate;
    }

    public void setLastPaidDate(Date lastPaidDate) {
        this.lastPaidDate = lastPaidDate;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public Date getNextPaymentDueDate() {
        return nextPaymentDueDate;
    }

    public void setNextPaymentDueDate(Date nextPaymentDueDate) {
        this.nextPaymentDueDate = nextPaymentDueDate;
    }
}


