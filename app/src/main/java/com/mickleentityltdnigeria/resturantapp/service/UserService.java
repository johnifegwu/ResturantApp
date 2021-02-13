package com.mickleentityltdnigeria.resturantapp.service;

import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.exceptions.DuplicateUserException;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserCredentialsException;
import com.mickleentityltdnigeria.resturantapp.exceptions.RequiredFiledException;
import com.mickleentityltdnigeria.resturantapp.logic.UserLogic;

public class UserService {

    public UserLogic User = new UserLogic();

    public UserService() {
    }

    //Adds new User to the system.
    public void AddUser(User user) throws RequiredFiledException, DuplicateUserException {
        this.User.AddUser(user);
    }

    //Updates the user in the system.
    public void UpdateUser(User user){
        this.User.UpdateUser(user);
    }

    //Deletes the user from the system.
    public void DeleteUser(int userID){
        this.User.DeleteUser(userID);
    }

    //Gets the given user from the system.
    public User getUserByName(String userName){
        return this.User.getUserByName(userName);
    }

    public boolean LogIn(String userName, String passWord) throws InvalidUserCredentialsException {
        return this.User.LogIn(userName, passWord);
    }

    public void LogOut(){
        this.User.LogOut();
    }

    public boolean getIsLoggedIn() {
        return this.User.getIsLoggedIn();
    }

}
