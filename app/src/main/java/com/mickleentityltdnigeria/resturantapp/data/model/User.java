package com.mickleentityltdnigeria.resturantapp.data.model;

import java.io.Serializable;

public class User implements Serializable {

    private String userID;
    private String userName;
    private String passWord;
    private String firstName;
    private String middleName;
    private String lastName;
    private String eMail;
    private String mobilePhone;
    private String contactAddress;
    private String city;
    private String zipCode;
    private String state;
    private String country;
    private String deviceID;
    private String userType;

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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
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

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
