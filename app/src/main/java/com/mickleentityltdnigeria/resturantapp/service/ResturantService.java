package com.mickleentityltdnigeria.resturantapp.service;

import com.mickleentityltdnigeria.resturantapp.data.model.Resturant;
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
    public void approveResturant(int resturantID) throws InvalidUserException {
        this.resturant.approveResturant(resturantID);
    }

    //Rolls back the Approval process.
    public void disApproveResturant(int resturantID) throws InvalidUserException {
        this.resturant.disApproveResturant(resturantID);
    }

    public Resturant getResturantByResturantID(int resturantID){
        return this.resturant.getResturantByResturantID(resturantID);
    }

}
