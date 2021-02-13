package com.mickleentityltdnigeria.resturantapp.data.model;

import java.io.Serializable;

public class CartItem implements Serializable {

    private int cartID;
    private int foodID;
    private int userID;
    private byte[] foodImg;
    private Double foodPrice;
    private String foodDesc;
    private String foodImgUrl;
    private int cartQty;

    public CartItem() {

    }

    public CartItem(int cartID, int foodID, int userID, byte[] foodImg, Double foodPrice, String foodDesc, String foodImgUrl, int cartQty) {
        this.cartID = cartID;
        this.foodID = foodID;
        this.userID = userID;
        this.foodImg = foodImg;
        this.foodPrice = foodPrice;
        this.foodDesc = foodDesc;
        this.foodImgUrl = foodImgUrl;
        this.cartQty = cartQty;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
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
