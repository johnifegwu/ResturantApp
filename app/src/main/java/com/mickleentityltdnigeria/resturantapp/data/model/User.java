package com.mickleentityltdnigeria.resturantapp.data.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class User implements Serializable {

    public String userID;
    public String userName;
    public String passWord;
    public String firstName;
    public String middleName;
    public String lastName;
    public String eMail;
    public String mobilePhone;
    public String contactAddress;
    public String city;
    public String zipCode;
    public String state;
    public String country;
    public String deviceID;
    public String userType;

    public User(){}

    public User(String userID, String userName, String passWord, String firstName, String middleName, String lastName, String eMail, String mobilePhone, String contactAddress, String city, String zipCode, String state, String country, String deviceID, String userType) {
        this.userID = userID;
        this.userName = userName;
        this.passWord = passWord;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.mobilePhone = mobilePhone;
        this.contactAddress = contactAddress;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
        this.country = country;
        this.deviceID = deviceID;
        this.userType = userType;
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
    public String getUserName() {
        return userName;
    }

    @Exclude
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Exclude
    public String getPassWord() {
        return passWord;
    }

    @Exclude
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Exclude
    public String getFirstName() {
        return firstName;
    }

    @Exclude
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Exclude
    public String getMiddleName() {
        return middleName;
    }

    @Exclude
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Exclude
    public String getLastName() {
        return lastName;
    }

    @Exclude
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Exclude
    public String geteMail() {
        return eMail;
    }

    @Exclude
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    @Exclude
    public String getMobilePhone() {
        return mobilePhone;
    }

    @Exclude
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Exclude
    public String getContactAddress() {
        return contactAddress;
    }

    @Exclude
    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
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
    }

    @Exclude
    public String getDeviceID() {
        return deviceID;
    }

    @Exclude
    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    @Exclude
    public String getUserType() {
        return userType;
    }

    @Exclude
    public void setUserType(String userType) {
        this.userType = userType;
    }
}
