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

    public void DeleteFoodItem(int foodID){
        //TODO update database here.

    }

    public List<FoodItem> SearchFoodItems(String searchTerm){
        //TODO update database here.
        List<FoodItem> foodItems =  new ArrayList<FoodItem>();

        return foodItems;
    }

    public List<FoodItem> getFoodItemsByUser(int userID){
        //TODO update database here.
        List<FoodItem> foodItems =  new ArrayList<FoodItem>();

        return foodItems;
    }

    public List<FoodItem> getFoodItemsByResturant(int resturantID){
        //TODO update database here.
        List<FoodItem> foodItems =  new ArrayList<FoodItem>();

        return foodItems;
    }

}
