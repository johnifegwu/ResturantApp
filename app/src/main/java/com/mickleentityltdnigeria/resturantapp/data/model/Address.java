package com.mickleentityltdnigeria.resturantapp.data.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Address implements Serializable {

    public String addressID;
    public String userID;
    public String addressType;
    public String contactAddress;
    public String city;
    public String zipCode;
    public String state;
    public String country;
    public String contactPerson;
    public String contactPhone;


    public Address() {
    }

    public Address(String addressID, String userID, String addressType, String contactAddress, String city, String zipCode, String state, String country, String contactPerson, String contactPhone) {
        this.addressID = addressID;
        this.userID = userID;
        this.addressType = addressType;
        this.contactAddress = contactAddress;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
        this.country = country;
        this.contactPerson = contactPerson;
        this.contactPhone = contactPhone;
    }

    @Exclude
    public String getAddressID() {
        return addressID;
    }

    @Exclude
    public void setAddressID(String addressID) {
        this.addressID = addressID;
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
    public String getAddressType() {
        return addressType;
    }

    @Exclude
    public void setAddressType(String addressType) {
        this.addressType = addressType;
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
    public String getContactPerson() {
        return contactPerson;
    }

    @Exclude
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    @Exclude
    public String getContactPhone() {
        return contactPhone;
    }

    @Exclude
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
