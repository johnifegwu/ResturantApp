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
    public int moneyBackGuaranteeInDays;
    public String resturantLongitude;
    public String resturantLatitude;
    public String resturantImg;
    public String resturantImgUrl;
    public String address;
    public String city;
    public String zipCode;
    public String zipCodes;
    public String zipCodesX;
    public String state;
    public String country;
    public String currencyCode;
    public String MicklePayWalletID;
    public String contactPerson;
    public String phone;
    public String email;
    public String websiteUrl;
    public boolean paid;
    public boolean approved;
    public Date dateApproved;
    public  String approvedBy;
    public boolean suspended;
    public Date dateSuspended;
    public  String suspendedBy;
    public Date lastPaidDate;
    public double amountPaid;
    public String paymentChannel;
    public Date nextPaymentDueDate;
    public String queryString;


    public Resturant(){}

    public Resturant(String resturantID, String userID, String resturantName, String resturantType, String resturantDescription, int moneyBackGuaranteeInDays, String resturantLongitude, String resturantLatitude, String resturantImg, String resturantImgUrl, String address, String city, String zipCode, String zipCodes, String zipCodesX, String state, String country, String currencyCode, String MicklePayWalletID , String contactPerson, String phone, String email, String websiteUrl, boolean paid, boolean approved, Date dateApproved, String approvedBy, boolean suspended, Date dateSuspended, String suspendedBy, Date lastPaidDate, double amountPaid, String paymentChannel, Date nextPaymentDueDate) {
        this.resturantID = resturantID;
        this.userID = userID;
        this.resturantName = resturantName;
        this.resturantType = resturantType;
        this.resturantDescription = resturantDescription;
        this.moneyBackGuaranteeInDays = moneyBackGuaranteeInDays;
        this.resturantLongitude = resturantLongitude;
        this.resturantLatitude = resturantLatitude;
        this.resturantImg = resturantImg;
        this.resturantImgUrl = resturantImgUrl;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.zipCodes = zipCodes;
        this.zipCodesX = zipCodesX;
        this.state = state;
        this.country = country;
        this.currencyCode = currencyCode;
        this.MicklePayWalletID = MicklePayWalletID;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.email = email;
        this.websiteUrl = websiteUrl;
        this.paid = paid;
        this.approved = approved;
        this.dateApproved = dateApproved;
        this.approvedBy = approvedBy;
        this.suspended = suspended;
        this.dateSuspended = dateSuspended;
        this.suspendedBy = suspendedBy;
        this.lastPaidDate = lastPaidDate;
        this.amountPaid = amountPaid;
        this.paymentChannel = paymentChannel;
        this.nextPaymentDueDate = nextPaymentDueDate;
        this.queryString = getQqueryString(country, suspended);
    }

    @Exclude
    public static String getQqueryString(String country, boolean suspended){
        return (country + String.valueOf(suspended));
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
    public int getMoneyBackGuaranteeInDays() {
        return moneyBackGuaranteeInDays;
    }

    @Exclude
    public void setMoneyBackGuaranteeInDays(int moneyBackGuaranteeInDays) {
        this.moneyBackGuaranteeInDays = moneyBackGuaranteeInDays;
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
    public String getZipCodesX() {
        return zipCodesX;
    }

    @Exclude
    public void setZipCodesX(String zipCodesX) {
        this.zipCodesX = zipCodesX;
    }

    @Exclude
    public String getZipCode() {
        return zipCode;
    }

    @Exclude
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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
        this.queryString = getQqueryString(country,this.suspended);
    }

    @Exclude
    public String getCurrencyCode() {
        return currencyCode;
    }

    @Exclude
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Exclude
    public String getMicklePayWalletID() {
        return MicklePayWalletID;
    }

    @Exclude
    public void setMicklePayWalletID(String MicklePayWalletID) {
        this.MicklePayWalletID = MicklePayWalletID;
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
    public Date getDateApproved() {
        return dateApproved;
    }

    @Exclude
    public void setDateApproved(Date dateApproved) {
        this.dateApproved = dateApproved;
    }

    @Exclude
    public String getApprovedBy() {
        return approvedBy;
    }

    @Exclude
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    @Exclude
    public boolean isSuspended() {
        return suspended;
    }

    @Exclude
    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
        this.queryString = getQqueryString(this.country,this.suspended);
    }

    @Exclude
    public Date getDateSuspended() {
        return dateSuspended;
    }

    @Exclude
    public void setDateSuspended(Date dateSuspended) {
        this.dateSuspended = dateSuspended;
    }

    @Exclude
    public String getSuspendedBy() {
        return suspendedBy;
    }

    @Exclude
    public void setSuspendedBy(String suspendedBy) {
        this.suspendedBy = suspendedBy;
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


