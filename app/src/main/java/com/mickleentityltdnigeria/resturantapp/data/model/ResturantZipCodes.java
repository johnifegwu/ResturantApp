package com.mickleentityltdnigeria.resturantapp.data.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class ResturantZipCodes  implements Serializable {

    public String ID;
    public String resturantID;
    public String zipCode;

    public ResturantZipCodes() {
    }

    public ResturantZipCodes(String ID, String resturantID, String zipCode) {
        this.ID = ID;
        this.resturantID = resturantID;
        this.zipCode = zipCode;
    }

    @Exclude
    public String getID() {
        return ID;
    }

    @Exclude
    public void setID(String ID) {
        this.ID = ID;
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
    public String getZipCode() {
        return zipCode;
    }

    @Exclude
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
