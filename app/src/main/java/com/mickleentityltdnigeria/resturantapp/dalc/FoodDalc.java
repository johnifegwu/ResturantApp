package com.mickleentityltdnigeria.resturantapp.dalc;

import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class FoodDalc {

    public FoodDalc() {
    }


    public void AddFoodItem(FoodItem foodItem){
        //TODO update database here.

    }

    public void UpdateFoodItem(FoodItem foodItem){
        //TODO update database here.

    }

    public void DeleteFoodItem(String foodID){
        //TODO update database here.

    }

    public List<FoodItem> SearchFoodItems(String searchTerm, String zipCode, boolean isApproved){
        //TODO update database here.
        List<FoodItem> foodItems =  new ArrayList<FoodItem>();

        return foodItems;
    }

    public List<FoodItem> getFoodItemsByUser(String userID){
        //TODO update database here.
        List<FoodItem> foodItems =  new ArrayList<FoodItem>();

        return foodItems;
    }

    public List<FoodItem> getFoodItemsByResturant(String resturantID){
        //TODO update database here.
        List<FoodItem> foodItems =  new ArrayList<FoodItem>();

        return foodItems;
    }

    public FoodItem getFoodItemByFoodID(String foodID){
        //TODO update database here.
        FoodItem foodItem = null;

        return foodItem;
    }

}
