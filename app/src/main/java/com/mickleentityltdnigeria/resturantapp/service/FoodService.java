package com.mickleentityltdnigeria.resturantapp.service;

import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserException;
import com.mickleentityltdnigeria.resturantapp.logic.FoodLogic;

import java.util.List;

public class FoodService {

    public FoodLogic food = new FoodLogic();

    public FoodService() {
    }

    public void AddFoodItem(FoodItem foodItem) throws InvalidUserException {
        this.food.AddFoodItem(foodItem);
    }

    public void UpdateFoodItem(FoodItem foodItem) throws InvalidUserException {
        this.food.UpdateFoodItem(foodItem);
    }

    public void DeleteFoodItem(int foodID) throws InvalidUserException {
        this.food.DeleteFoodItem(foodID);
    }

    public List<FoodItem> SearchFoodItems(String searchTerm, String zipCode, boolean b){
        return this.food.SearchFoodItems(searchTerm, zipCode,b);
    }

    public List<FoodItem> getFoodItemsByUser(int userID) throws InvalidUserException {
        return this.food.getFoodItemsByUser(userID);
    }

    public List<FoodItem> getFoodItemsByResturant(int resturantID) throws InvalidUserException  {
        return this.food.getFoodItemsByResturant(resturantID);
    }

    public FoodItem getFoodItemByFoodID(int foodID) throws InvalidUserException {
        return this.food.getFoodItemByFoodID(foodID);
    }

}
