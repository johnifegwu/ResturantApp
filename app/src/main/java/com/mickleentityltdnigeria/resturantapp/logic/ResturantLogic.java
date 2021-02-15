package com.mickleentityltdnigeria.resturantapp.logic;

import com.mickleentityltdnigeria.resturantapp.dalc.Dalc;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem;
import com.mickleentityltdnigeria.resturantapp.data.model.Resturant;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserException;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.ArrayList;
import java.util.List;

public class ResturantLogic {

    public ResturantLogic() {
    }

    public void AddResturant(Resturant resturant) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        Dalc.Resturant().AddResturant(resturant);
    }

    public void UpdateResturant(Resturant resturant) throws InvalidUserException {
        if((!module.userType.equals(module.UserTypeSELLER)) || (module.isLoggedIn = false)){
            throw new InvalidUserException();
        }
        Dalc.Resturant().UpdateResturant(resturant);
    }

    //Approves a Resturant and all Food Items in the system.
    public void approveResturant(int resturantID) throws InvalidUserException {
        if((!module.userType.equals(module.UserTypeSUPPER)) || (module.isLoggedIn = false)){
            throw new InvalidUserException();
        }
        Resturant resturant = getResturantByResturantID(resturantID);
        if (resturant.getResturantName() != ""){
            //Update resturant
            resturant.setApproved(true);
            Dalc.Resturant().UpdateResturant(resturant);
            //Update User
            User user = Dalc.User().getUserByID(resturant.getUserID());
            user.setUserType(module.UserTypeSELLER);
            Dalc.User().UpdateUser(user);
            //Update food items
            List<FoodItem> food = new ArrayList<FoodItem>();
            food = Dalc.Food().getFoodItemsByResturant(resturantID);
            for (FoodItem f:food) {
                f.setApproved(true);
                f.setZipCodes(resturant.getZipCodes());
                Dalc.Food().UpdateFoodItem(f);
            }
            //
        }
    }

    //Rolls back the Approval process.
    public void disApproveResturant(int resturantID) throws InvalidUserException {
        if((!module.userType.equals(module.UserTypeSUPPER)) || (module.isLoggedIn = false)){
            throw new InvalidUserException();
        }
        Resturant resturant = getResturantByResturantID(resturantID);
        if (resturant.getResturantName() != ""){
            //Update resturant
            resturant.setApproved(false);
            Dalc.Resturant().UpdateResturant(resturant);
            //Update User
            User user = Dalc.User().getUserByID(resturant.getUserID());
            user.setUserType(module.UserTypeCUSTOMER);
            Dalc.User().UpdateUser(user);
            //Update food items
            List<FoodItem> food = new ArrayList<FoodItem>();
            food = Dalc.Food().getFoodItemsByResturant(resturantID);
            for (FoodItem f:food) {
                f.setApproved(false);
                f.setZipCodes(resturant.getZipCodes());
                Dalc.Food().UpdateFoodItem(f);
            }
            //
        }
    }

    public Resturant getResturantByResturantID(int resturantID){
        return Dalc.Resturant().getResturantByResturantID(resturantID);
    }

}
