package com.mickleentityltdnigeria.resturantapp.data.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class CartItem implements Serializable {

    public String cartID;
    public String foodID;
    public String resturantID;
    public String userName;
    public String foodImg;
    public Double foodPrice;
    public String currency;
    public String foodDesc;
    public String foodImgUrl;
    public int foodQty;
    public double markUp = 0.01;
    public double markUpValue = 0.0;

    public CartItem() {

    }

    public CartItem(String cartID, String foodID, String resturantID, String userName, String foodImg, Double foodPrice, String currency, String foodDesc, String foodImgUrl, int foodQty) {
        this.cartID = cartID;
        this.foodID = foodID;
        this.resturantID = resturantID;
        this.userName = userName;
        this.foodImg = foodImg;
        this.foodPrice = foodPrice;
        this.currency = currency;
        this.foodDesc = foodDesc;
        this.foodImgUrl = foodImgUrl;
        this.foodQty = foodQty;
        this.markUpValue = ((foodPrice * foodQty) * this.markUp);
    }

    @Exclude
    public double getSubTotal(){
        return ((this.foodPrice * this.foodQty) + ((this.foodPrice * this.foodQty) * this.markUp));
    }

    @Exclude
    public double getMarkUp() {
        return markUp;
    }

    @Exclude
    public void setMarkUp(double markUp) {
        this.markUp = markUp;
    }

    @Exclude
    public double getMarkUpValue() {
        return markUpValue;
    }

    @Exclude
    public void setMarkUpValue(double markUpValue) {
        this.markUpValue = markUpValue;
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
    public String getUserName() {
        return userName;
    }

    @Exclude
    public void setUserName(String userName) {
        this.userName = userName;
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
        return this.currency;
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
    public int getFoodQty() {
        return this.foodQty;
    }

    @Exclude
    public void setFoodQty(int foodQty) {
        this.foodQty = foodQty;
    }
}
