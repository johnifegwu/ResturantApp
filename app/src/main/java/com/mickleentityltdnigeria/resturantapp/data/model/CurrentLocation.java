package com.mickleentityltdnigeria.resturantapp.data.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class CurrentLocation implements Serializable {

    public String locationID;
    public String userID;
    public String country;
    public String state;
    public String city;
    public String zipCode;

    public CurrentLocation() {
    }

    public CurrentLocation(String locationID, String userID, String country, String state, String city, String zipCode) {
        this.locationID = locationID;
        this.userID = userID;
        this.country = country;
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;
    }

    @Exclude
    public String getLocationID() {
        return locationID;
    }

    @Exclude
    public void setLocationID(String locationID) {
        this.locationID = locationID;
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
    public String getCountry() {
        return country;
    }

    @Exclude
    public void setCountry(String country) {
        this.country = country;
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
}
