package com.mickleentityltdnigeria.resturantapp.service;

import com.mickleentityltdnigeria.resturantapp.data.model.Resturant;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserException;
import com.mickleentityltdnigeria.resturantapp.logic.ResturantLogic;

public class ResturantService {

    public ResturantLogic resturant =  new ResturantLogic();

    public ResturantService() {
    }

    public void AddResturant(Resturant resturant) throws InvalidUserException {
        this.resturant.AddResturant(resturant);
    }

    public void UpdateResturant(Resturant resturant) throws InvalidUserException {
        this.resturant.UpdateResturant(resturant);
    }

    //Approves a Resturant and all Food Items in the system.
    public void approveResturant(Resturant resturant, User user) throws InvalidUserException {
        this.resturant.approveResturant(resturant, user);
    }

    //Rolls back the Approval process.
    public void disApproveResturant(Resturant resturant, User user) throws InvalidUserException {
        this.resturant.disApproveResturant(resturant, user);
    }

    public Resturant getResturantByResturantID(String resturantID){
        return this.resturant.getResturantByResturantID(resturantID);
    }

}
