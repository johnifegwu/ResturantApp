package com.mickleentityltdnigeria.resturantapp.data.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Date;

@IgnoreExtraProperties
public class Resturant implements Serializable {

    public String resturantID;
    public String userID;
    public String resturantName;
    public String resturantType;
    public String resturantDescription;
    public String resturantLongitude;
    public String resturantLatitude;
    public String resturantImg;
    public String resturantImgUrl;
    public String address;
    public String city;
    public String zipCodes;
    public String state;
    public String country;
    public String contactPerson;
    public String phone;
    public String email;
    public String websiteUrl;
    public boolean paid;
    public boolean approved;
    public Date lastPaidDate;
    public double amountPaid;
    public String paymentChannel;
    public Date nextPaymentDueDate;


    public Resturant(){}

    public Resturant(String resturantID, String userID, String resturantName, String resturantType, String resturantDescription, String resturantLongitude, String resturantLatitude, String resturantImg, String resturantImgUrl, String address, String city, String zipCodes, String state, String country, String contactPerson, String phone, String email, String websiteUrl, boolean paid, boolean approved, Date lastPaidDate, double amountPaid, String paymentChannel, Date nextPaymentDueDate) {
        this.resturantID = resturantID;
        this.userID = userID;
        this.resturantName = resturantName;
        this.resturantType = resturantType;
        this.resturantDescription = resturantDescription;
        this.resturantLongitude = resturantLongitude;
        this.resturantLatitude = resturantLatitude;
        this.resturantImg = resturantImg;
        this.resturantImgUrl = resturantImgUrl;
        this.address = address;
        this.city = city;
        this.zipCodes = zipCodes;
        this.state = state;
        this.country = country;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.email = email;
        this.websiteUrl = websiteUrl;
        this.paid = paid;
        this.approved = approved;
        this.lastPaidDate = lastPaidDate;
        this.amountPaid = amountPaid;
        this.paymentChannel = paymentChannel;
        this.nextPaymentDueDate = nextPaymentDueDate;
    }

    @Exclude
    public String getResturantID() {
        return resturantID;
    }

    @Exclude
    public void setResturantID(String resturantID) {
        this.resturantID = resturantID;
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
    public String getResturantName() {
        return resturantName;
    }

    @Exclude
    public void setResturantName(String resturantName) {
        this.resturantName = resturantName;
    }

    @Exclude
    public String getResturantType() {
        return resturantType;
    }

    @Exclude
    public void setResturantType(String resturantType) {
        this.resturantType = resturantType;
    }

    @Exclude
    public String getResturantDescription() {
        return resturantDescription;
    }

    @Exclude
    public void setResturantDescription(String resturantDescription) {
        this.resturantDescription = resturantDescription;
    }

    @Exclude
    public String getResturantLongitude() {
        return resturantLongitude;
    }

    @Exclude
    public void setResturantLongitude(String resturantLongitude) {
        this.resturantLongitude = resturantLongitude;
    }

    @Exclude
    public String getResturantLatitude() {
        return resturantLatitude;
    }

    @Exclude
    public void setResturantLatitude(String resturantLatitude) {
        this.resturantLatitude = resturantLatitude;
    }

    @Exclude
    public String getResturantImg() {
        return resturantImg;
    }

    @Exclude
    public void setResturantImg(String resturantImg) {
        this.resturantImg = resturantImg;
    }

    @Exclude
    public String getResturantImgUrl() {
        return resturantImgUrl;
    }

    @Exclude
    public void setResturantImgUrl(String resturantImgUrl) {
        this.resturantImgUrl = resturantImgUrl;
    }

    @Exclude
    public String getAddress() {
        return address;
    }

    @Exclude
    public void setAddress(String address) {
        this.address = address;
    }

    @Exclude
    public String getCity() {
        return city;
    }

    @Exclude
    public void setCity(String city) {
        this.city = city;
    }

    @Exclude
    public String getZipCodes() {
        return zipCodes;
    }

    @Exclude
    public void setZipCodes(String zipCodes) {
        this.zipCodes = zipCodes;
    }

    @Exclude
    public String getState() {
        return state;
    }

    @Exclude
    public void setState(String state) {
        this.state = state;
    }

    @Exclude
    public String getCountry() {
        return country;
    }

    @Exclude
    public void setCountry(String country) {
        this.country = country;
    }

    @Exclude
    public String getContactPerson() {
        return contactPerson;
    }

    @Exclude
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    @Exclude
    public String getPhone() {
        return phone;
    }

    @Exclude
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Exclude
    public String getEmail() {
        return email;
    }

    @Exclude
    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getWebsiteUrl() {
        return websiteUrl;
    }

    @Exclude
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    @Exclude
    public boolean isPaid() {
        return paid;
    }

    @Exclude
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Exclude
    public boolean isApproved() {
        return approved;
    }

    @Exclude
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Exclude
    public Date getLastPaidDate() {
        return lastPaidDate;
    }

    @Exclude
    public void setLastPaidDate(Date lastPaidDate) {
        this.lastPaidDate = lastPaidDate;
    }

    @Exclude
    public double getAmountPaid() {
        return amountPaid;
    }

    @Exclude
    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    @Exclude
    public String getPaymentChannel() {
        return paymentChannel;
    }

    @Exclude
    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    @Exclude
    public Date getNextPaymentDueDate() {
        return nextPaymentDueDate;
    }

    @Exclude
    public void setNextPaymentDueDate(Date nextPaymentDueDate) {
        this.nextPaymentDueDate = nextPaymentDueDate;
    }
}


