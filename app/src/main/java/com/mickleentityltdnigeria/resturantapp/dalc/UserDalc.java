package com.mickleentityltdnigeria.resturantapp.dalc;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.exceptions.DuplicateUserException;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserCredentialsException;
import com.mickleentityltdnigeria.resturantapp.exceptions.RequiredFiledException;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.extensions.UserUpdatedHandler;

import java.util.ArrayList;
import java.util.List;

public class UserDalc {

    DatabaseReference userDB;

    public UserDalc(){
        userDB = FirebaseDatabase.getInstance().getReference("User");
    }

    public void AddUser(User user) throws RequiredFiledException, DuplicateUserException {
        //check required fields
      String userID = userDB.push().getKey();
      user.setUserID(userID);
      userDB.child(userID).setValue(user);
    }

    public void UpdateUser(User user){
        //TODO do the updates here

    }

    public void DeleteUser(String userID){
        //TODO do the delete here.

    }

    public User getUserByName(String userName){
        User user = new User();
        //TODO get the user from database

        return user;
    }

    public User getUserByID(String userID){
        User user = new User();
        //TODO get the user from database

        return user;
    }

}
