package com.mickleentityltdnigeria.resturantapp.data.model;

import java.io.Serializable;

public class Address implements Serializable {

    private int addressID;
    private int userID;
    private String addressType;
    private String contactAddress;
    private String city;
    private String zipCode;
    private String state;
    private String country;


    public Address() {
    }

    public Address(int addressID, int userID, String addressType, String contactAddress, String city, String zipCode, String state, String country) {
        this.addressID = addressID;
        this.userID = userID;
        this.addressType = addressType;
        this.contactAddress = contactAddress;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
        this.country = country;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
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
}
