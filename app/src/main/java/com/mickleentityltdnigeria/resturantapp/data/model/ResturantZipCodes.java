package com.mickleentityltdnigeria.resturantapp.data.model;

import java.io.Serializable;

public class ResturantZipCodes  implements Serializable {

    private String ID;
    private String resturantID;
    private String zipCode;

    public ResturantZipCodes() {
    }

    public ResturantZipCodes(String ID, String resturantID, String zipCode) {
        this.ID = ID;
        this.resturantID = resturantID;
        this.zipCode = zipCode;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getResturantID() {
        return resturantID;
    }

    public void setResturantID(String resturantID) {
        this.resturantID = resturantID;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
