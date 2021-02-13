package com.mickleentityltdnigeria.resturantapp.dalc;

import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.exceptions.DuplicateUserException;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserCredentialsException;
import com.mickleentityltdnigeria.resturantapp.exceptions.RequiredFiledException;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.extensions.UserUpdatedHandler;

import java.util.ArrayList;
import java.util.List;

public class UserDalc {

    public UserDalc(){
    }

    public void AddUser(User user) throws RequiredFiledException, DuplicateUserException {
        //check required fields
       //TODO add user to the database.
    }

    public User getUserByName(String userName){
        User user = new User();
        //TODO get the user from database

        return user;
    }

    public void UpdateUser(User user){
        //TODO do the updates here

    }

    public void DeleteUser(int userID){
        //TODO do the delete here.

    }

}
