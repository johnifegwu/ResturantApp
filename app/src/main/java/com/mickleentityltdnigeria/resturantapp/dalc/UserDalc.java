package com.mickleentityltdnigeria.resturantapp.dalc;

import com.mickleentityltdnigeria.resturantapp.data.User;
import com.mickleentityltdnigeria.resturantapp.exceptions.DuplicateUserException;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserCredentialsException;
import com.mickleentityltdnigeria.resturantapp.exceptions.RequiredFiledException;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.extensions.UserUpdatedHandler;

import java.util.ArrayList;
import java.util.List;

public class UserDalc {

    private List<User> Users = new ArrayList<User>();

    private boolean isLoggedIn = false;

    public Event<UserUpdatedHandler> userDataChanged = new Event<UserUpdatedHandler>();

    public UserDalc(){
    }

    public void AddUser(User user) throws RequiredFiledException, DuplicateUserException {
        //check required fields
        if(user.getUserName() == "" || user.getPassWord() == "") {
            throw new RequiredFiledException("Please complete all required fields.");
        }
        //check if user data already exists.
        if(checkDuplicateUser(user)){
            throw new DuplicateUserException("User " + user.getUserName() + " already exists in the system.");
        }
        //add new user
        this.Users.add(user);
        //raise event
        for (UserUpdatedHandler listener : userDataChanged.listeners())
        {
            listener.invoke(user.getUserName());
        }
    }

    public boolean LogIn(String userName, String passWord) throws InvalidUserCredentialsException {
        //check compare fields
        if(Users.get(0).getUserName() == userName && Users.get(0).getPassWord() == passWord) {
            this.isLoggedIn = true;
            //raise event
            for (UserUpdatedHandler listener : userDataChanged.listeners())
            {
                listener.invoke(Users.get(0).getUserName());
            }
        }else{
            throw new InvalidUserCredentialsException("Please provide a valid userName and passWord.");
        }
        return this.isLoggedIn;
    }

    public void LogOut(){
        this.isLoggedIn = false;
        this.Users.clear();
    }

    public boolean getIsLoggedIn(){
        return this.isLoggedIn;
    }

    public List<User> getUsers(){
        return this.Users;
    }

    private boolean checkDuplicateUser(User user){
        boolean result = false;
        for (User u:Users) {
            if(u.getUserName() == user.getUserName()){
                result = true;
                break;
            }
        }
        return result;
    }

}
