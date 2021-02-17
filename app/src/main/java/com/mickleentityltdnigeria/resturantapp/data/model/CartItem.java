package com.mickleentityltdnigeria.resturantapp.data.model;

import java.io.Serializable;

public class CartItem implements Serializable {

    private String cartID;
    private String foodID;
    private String resturantID;
    private String userID;
    private byte[] foodImg;
    private Double foodPrice;
    private String currency;
    private String foodDesc;
    private String foodImgUrl;
    private int cartQty;

    public CartItem() {

    }

    public CartItem(String cartID, String foodID, String resturantID, String userID, byte[] foodImg, Double foodPrice, String currency, String foodDesc, String foodImgUrl, int cartQty) {
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

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

    public String getResturantID() {
        return resturantID;
    }

    public void setResturantID(String resturantID) {
        this.resturantID = resturantID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public byte[] getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(byte[] foodImg) {
        this.foodImg = foodImg;
    }

    public Double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(Double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFoodDesc() {
        return foodDesc;
    }

    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
    }

    public String getFoodImgUrl() {
        return foodImgUrl;
    }

    public void setFoodImgUrl(String foodImgUrl) {
        this.foodImgUrl = foodImgUrl;
    }

    public int getCartQty() {
        return cartQty;
    }

    public void setCartQty(int cartQty) {
        this.cartQty = cartQty;
    }
}
