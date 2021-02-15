package com.mickleentityltdnigeria.resturantapp.data.model;

import java.io.Serializable;

public class FoodOrderDetail implements Serializable {

    private int ID;
    private int oderID;
    private int foodID;
    private double foodPrice;
    private String currency;
    private double foodQty;

    public FoodOrderDetail(int ID, int oderID, int foodID, double foodPrice, String currency, double foodQty) {
        this.ID = ID;
        this.oderID = oderID;
        this.foodID = foodID;
        this.foodPrice = foodPrice;
        this.currency = currency;
        this.foodQty = foodQty;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getOderID() {
        return oderID;
    }

    public void setOderID(int oderID) {
        this.oderID = oderID;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getFoodQty() {
        return foodQty;
    }

    public void setFoodQty(double foodQty) {
        this.foodQty = foodQty;
    }
}
