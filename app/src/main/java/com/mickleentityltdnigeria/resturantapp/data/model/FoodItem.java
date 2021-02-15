package com.mickleentityltdnigeria.resturantapp.data.model;

import java.io.Serializable;

public class FoodItem implements Serializable {

    private int foodID;
    private int resturantID;
    private String  foodImgUrl;
    private String foodDesc;
    private Double foodPrice;
    private String currency;
    private byte[] foodImg;
    private int userID;
    private boolean approved;
    private String zipCodes; //Zip Codes derived from Resturant separated by spaces

    public FoodItem(){

    }


    public FoodItem(int foodID, int resturantID, String foodImgUrl, String foodDesc, Double foodPrice, String currency, byte[] foodImg, int userID,boolean approved, String zipCodes) {
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean getApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getZipCodes() {
        return zipCodes;
    }

    public void setZipCodes(String zipCodes) {
        this.zipCodes = zipCodes;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public int getResturantID() {
        return resturantID;
    }

    public void setResturantID(int resturantID) {
        this.resturantID = resturantID;
    }

    public String getFoodImgUrl() {
        return foodImgUrl;
    }

    public void setFoodImgUrl(String foodImgUrl) {
        this.foodImgUrl = foodImgUrl;
    }

    public String getFoodDesc() {
        return foodDesc;
    }

    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
    }

    public Double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(Double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public byte[] getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(byte[] foodImg) {
        this.foodImg = foodImg;
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "foodID=" + foodID +
                ", resturantID=" + resturantID +
                ", foodImgUrl='" + foodImgUrl + '\'' +
                ", foodDesc='" + foodDesc + '\'' +
                ", foodPrice=" + foodPrice +
                '}';
    }


}
