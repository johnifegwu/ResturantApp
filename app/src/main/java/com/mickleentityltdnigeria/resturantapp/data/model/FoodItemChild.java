package com.mickleentityltdnigeria.resturantapp.data.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class FoodItemChild implements Serializable {

    public String foodID;
    public String zipCode;

    public FoodItemChild() {
    }

    public FoodItemChild(String foodID, String zipCode) {
        this.foodID = foodID;
        this.zipCode = zipCode;
    }

    @Exclude
    public String getFoodID() {
        return foodID;
    }

    @Exclude
    public void setFoodID(String foodID) {
        this.foodID = foodID;
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
