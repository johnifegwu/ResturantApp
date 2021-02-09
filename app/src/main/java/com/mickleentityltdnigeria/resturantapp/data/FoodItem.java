package com.mickleentityltdnigeria.resturantapp.data;

import java.io.Serializable;

public class FoodItem implements Serializable {

    private String  foodUrl;
    private String foodDesc;
    private Double foodPrice;
    private int ID;

    public FoodItem(int id, String foodUrl, String foodDesc, Double foodPrice){
        this.ID = id;
        this.foodUrl = foodUrl;
        this.foodDesc = foodDesc;
        this.foodPrice = foodPrice;
    }

    public FoodItem(){

    }

    public void setFoodUrl(String foodUrl){
        this.foodUrl = foodUrl;
    }

    public String getFoodUrl(){
        return this.foodUrl;
    }

    public void setFoodDesc(String foodDesc){
        this.foodDesc = foodDesc;
    }

    public String getFoodDesc(){
        return this.foodDesc;
    }

    public void setFoodPrice(Double foodPrice){
        this.foodPrice = foodPrice;
    }

    public Double getFoodPrice(){
        return  this.foodPrice;
    }

    public void setID(int id){
        this.ID = id;
    }

    public int getID(){
        return this.ID;
    }



}
