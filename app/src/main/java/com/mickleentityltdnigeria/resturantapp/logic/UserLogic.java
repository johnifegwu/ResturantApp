
package com.mickleentityltdnigeria.resturantapp.logic;

import com.mickleentityltdnigeria.resturantapp.dalc.Dalc;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.exceptions.DuplicateUserException;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserCredentialsException;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserException;
import com.mickleentityltdnigeria.resturantapp.exceptions.RequiredFiledException;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.extensions.UserUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.ArrayList;
import java.util.List;

public class UserLogic {

    public Event<UserUpdatedHandler> userDataChanged = new Event<UserUpdatedHandler>();

    private List<User> users = new ArrayList<User>();
    private boolean isLoggedIn = false;

    public void Add_SELLER_User(User user) throws RequiredFiledException, DuplicateUserException, InvalidUserException {
        if((!module.userType.equals(module.UserTypeSELLER)) || (module.isLoggedIn = false)){
            throw new InvalidUserException();
        }
        //check required fields
        if (user.getUserName() == "" || user.getPassWord() == "") {
            throw new RequiredFiledException("Please complete all required fields.");
        }
        //check if user data already exists.
        User user2 = Dalc.User().getUserByName(user.getUserName());
        //
        if (checkDuplicateUser(user, user2)) {
            throw new DuplicateUserException("User " + user.getUserName() + " already exists in the system.");
        }
        //add new user
        user.setUserType(module.UserTypeSELLER);
        Dalc.User().AddUser(user);
        //raise event
        for (UserUpdatedHandler listener : userDataChanged.listeners()) {
            listener.invoke(user.getUserName());
        }
    }

    public void Add_SUPPER_User(User user) throws RequiredFiledException, DuplicateUserException, InvalidUserException {
        if((!module.userType.equals(module.UserTypeSUPPER)) || (module.isLoggedIn = false)){
            throw new InvalidUserException();
        }
        //check required fields
        if (user.getUserName() == "" || user.getPassWord() == "") {
            throw new RequiredFiledException("Please complete all required fields.");
        }
        //check if user data already exists.
        User user2 = Dalc.User().getUserByName(user.getUserName());
        //
        if (checkDuplicateUser(user, user2)) {
            throw new DuplicateUserException("User " + user.getUserName() + " already exists in the system.");
        }
        //add new user
        user.setUserType(module.UserTypeSUPPER);
        Dalc.User().AddUser(user);
        //raise event
        for (UserUpdatedHandler listener : userDataChanged.listeners()) {
            listener.invoke(user.getUserName());
        }
    }

    public void AddUser(User user) throws RequiredFiledException, DuplicateUserException {
        //check required fields
        if (user.getUserName() == "" || user.getPassWord() == "") {
            throw new RequiredFiledException("Please complete all required fields.");
        }
        //check if user data already exists.
        User user2 = Dalc.User().getUserByName(user.getUserName());
        //
        if (checkDuplicateUser(user, user2)) {
            throw new DuplicateUserException("User " + user.getUserName() + " already exists in the system.");
        }
        //add new user
        Dalc.User().AddUser(user);
        //raise event
        for (UserUpdatedHandler listener : userDataChanged.listeners()) {
            listener.invoke(user.getUserName());
        }
    }

    public void UpdateUser(User user) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        Dalc.User().UpdateUser(user);
    }

    public void DeleteUser(String userID) throws InvalidUserException {
        if((!module.userType.equals(module.UserTypeSUPPER)) || (module.isLoggedIn = false)){
            throw new InvalidUserException();
        }
        Dalc.User().DeleteUser(userID);
    }

    public User getUserByName(String userName) {
        return Dalc.User().getUserByName(userName);
    }

    public boolean LogIn(String userName, String passWord) throws InvalidUserCredentialsException {
        //check compare fields
        User user = Dalc.User().getUserByName(userName);
        //
        if (user.getUserName() == userName && user.getPassWord() == passWord) {
            this.isLoggedIn = true;
            this.users.add(user);
            //raise event
            for (UserUpdatedHandler listener : userDataChanged.listeners()) {
                listener.invoke(user.getUserName());
            }
        } else {
            this.isLoggedIn = false;
            throw new InvalidUserCredentialsException("Please provide a valid userName and passWord.");
        }
        //raise event
        for (UserUpdatedHandler listener : userDataChanged.listeners()) {
            listener.invoke(userName);
        }
        return this.isLoggedIn;
    }

    public void LogOut() {
        this.isLoggedIn = false;
        this.users.clear();
    }

    public boolean getIsLoggedIn() {
        return this.isLoggedIn;
    }

    private boolean checkDuplicateUser(User user, User user2) {
        //
        boolean result = false;
        if (user2.getUserName() == user.getUserName()) {
            result = true;
        }
        return result;
    }
}
