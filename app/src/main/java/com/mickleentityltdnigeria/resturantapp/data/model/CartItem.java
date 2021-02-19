package com.mickleentityltdnigeria.resturantapp.data.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class CartItem implements Serializable {

    public String cartID;
    public String foodID;
    public String resturantID;
    public String userID;
    public String foodImg;
    public Double foodPrice;
    public String currency;
    public String foodDesc;
    public String foodImgUrl;
    public int cartQty;

    public CartItem() {

    }

    public CartItem(String cartID, String foodID, String resturantID, String userID, String foodImg, Double foodPrice, String currency, String foodDesc, String foodImgUrl, int cartQty) {
        this.cartID = cartID;
        this.foodID = foodID;
        this.resturantID = resturantID;
        this.userID = userID;
        this.foodImg = foodImg;
        this.foodPrice = foodPrice;
        this.currency = currency;
        this.foodDesc = foodDesc;
        this.foodImgUrl = foodImgUrl;
        this.cartQty = cartQty;
    }

    @Exclude
    public String getCartID() {
        return cartID;
    }

    @Exclude
    public void setCartID(String cartID) {
        this.cartID = cartID;
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
    public String getUserID() {
        return userID;
    }

    @Exclude
    public void setUserID(String userID) {
        this.userID = userID;
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
    public String getFoodDesc() {
        return foodDesc;
    }

    @Exclude
    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
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
    public int getCartQty() {
        return cartQty;
    }

    @Exclude
    public void setCartQty(int cartQty) {
        this.cartQty = cartQty;
    }
}
