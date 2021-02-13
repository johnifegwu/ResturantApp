package com.mickleentityltdnigeria.resturantapp.logic;

import com.mickleentityltdnigeria.resturantapp.dalc.Dalc;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserException;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.extensions.FoodItemUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.UserUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.List;

public class FoodLogic {

    public Event<FoodItemUpdatedHandler> foodItemChanged = new Event<FoodItemUpdatedHandler>();

    public FoodLogic() {
    }

    public void AddFoodItem(FoodItem foodItem) throws InvalidUserException {
        if((!module.userType.equals(module.UserTypeSELLER)) || (module.isLoggedIn = false)){
            throw new InvalidUserException();
        }
        Dalc.Food().AddFoodItem(foodItem);
        //raise event
        for (FoodItemUpdatedHandler listener : foodItemChanged.listeners()) {
            listener.invoke(foodItem.getFoodID());
        }
    }

    public void UpdateFoodItem(FoodItem foodItem) throws InvalidUserException {
        if((!module.userType.equals(module.UserTypeSELLER)) || (module.isLoggedIn = false)){
            throw new InvalidUserException();
        }
        Dalc.Food().UpdateFoodItem(foodItem);
        //raise event
        for (FoodItemUpdatedHandler listener : foodItemChanged.listeners()) {
            listener.invoke(foodItem.getFoodID());
        }
    }

    public void DeleteFoodItem(int foodID) throws InvalidUserException {
        if((!module.userType.equals(module.UserTypeSELLER)) || (module.isLoggedIn = false)){
            throw new InvalidUserException();
        }
        Dalc.Food().DeleteFoodItem(foodID);
        //raise event
        for (FoodItemUpdatedHandler listener : foodItemChanged.listeners()) {
            listener.invoke(foodID);
        }
    }

    public List<FoodItem> SearchFoodItems(String searchTerm, String zipCode){
        return Dalc.Food().SearchFoodItems(searchTerm,zipCode);
    }

    public List<FoodItem> getFoodItemsByUser(int userID) throws InvalidUserException {
        if((!module.userType.equals(module.UserTypeSELLER)) || (module.isLoggedIn = false)){
            throw new InvalidUserException();
        }
        return Dalc.Food().getFoodItemsByUser(userID);
    }

    public List<FoodItem> getFoodItemsByResturant(int resturantID) throws InvalidUserException  {
        if((!module.userType.equals(module.UserTypeSELLER)) || (module.isLoggedIn = false)){
            throw new InvalidUserException();
        }
        return Dalc.Food().getFoodItemsByResturant(resturantID);
    }
}
