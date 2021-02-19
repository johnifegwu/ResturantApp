package com.mickleentityltdnigeria.resturantapp.data.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class FoodItem implements Serializable {

    public String foodID;
    public String resturantID;
    public String  foodImgUrl;
    public String foodDesc;
    public Double foodPrice;
    public String currency;
    public String foodImg;
    public String userID;
    public boolean approved;
    public String zipCodes; //Zip Codes derived from Resturant separated by spaces

    public FoodItem(){

    }

    public FoodItem(String foodID, String resturantID, String foodImgUrl, String foodDesc, Double foodPrice, String currency, String foodImg, String userID, boolean approved, String zipCodes) {
        this.foodID = foodID;
        this.resturantID = resturantID;
        this.foodImgUrl = foodImgUrl;
        this.foodDesc = foodDesc;
        this.foodPrice = foodPrice;
        this.currency = currency;
        this.foodImg = foodImg;
        this.userID = userID;
        this.approved = approved;
        this.zipCodes = zipCodes;
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
    public String getResturantID() {
        return resturantID;
    }

    @Exclude
    public void setResturantID(String resturantID) {
        this.resturantID = resturantID;
    }

    @Exclude
    public String getFoodImgUrl() {
        return foodImgUrl;
    }

    @Exclude
    public void setFoodImgUrl(String foodImgUrl) {
        this.foodImgUrl = foodImgUrl;
    }

    @Exclude
    public String getFoodDesc() {
        return foodDesc;
    }

    @Exclude
    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
    }

    @Exclude
    public Double getFoodPrice() {
        return foodPrice;
    }

    @Exclude
    public void setFoodPrice(Double foodPrice) {
        this.foodPrice = foodPrice;
    }

    @Exclude
    public String getCurrency() {
        return currency;
    }

    @Exclude
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Exclude
    public String getFoodImg() {
        return foodImg;
    }

    @Exclude
    public void setFoodImg(String foodImg) {
        this.foodImg = foodImg;
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
    public boolean isApproved() {
        return approved;
    }

    @Exclude
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Exclude
    public String getZipCodes() {
        return zipCodes;
    }

    @Exclude
    public void setZipCodes(String zipCodes) {
        this.zipCodes = zipCodes;
    }
}
