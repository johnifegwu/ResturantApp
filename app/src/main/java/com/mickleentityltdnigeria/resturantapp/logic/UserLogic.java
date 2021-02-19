
package com.mickleentityltdnigeria.resturantapp.logic;

import com.mickleentityltdnigeria.resturantapp.R;
import com.mickleentityltdnigeria.resturantapp.dalc.Dalc;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.exceptions.DuplicateUserException;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserCredentialsException;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserException;
import com.mickleentityltdnigeria.resturantapp.exceptions.NoNetworkException;
import com.mickleentityltdnigeria.resturantapp.exceptions.RequiredFiledException;
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.extensions.UserUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.service.Service;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.ArrayList;
import java.util.List;

public class UserLogic {

    public Event<UserUpdatedHandler> duplicateUserEvent = new Event<UserUpdatedHandler>();

    private List<User> users = new ArrayList<User>();
    private boolean isLoggedIn = false;

    private boolean isUserExists = false;

    public void Add_SELLER_User(User user) throws RequiredFiledException, InvalidUserException {
        if((!module.userType.equals(module.UserTypeSELLER)) || (module.isLoggedIn = false)){
            throw new InvalidUserException();
        }
        //check required fields
        if (user.getUserName() == "" || user.getPassWord() == "") {
            throw new RequiredFiledException("Please complete all required fields.");
        }
        // Register interest in the user data not found.
        UserUpdatedHandler userNotFound = new UserUpdatedHandler() {
            public void invoke(List<User> users) {
                //add new user
                try{
                    user.setUserType(module.UserTypeSELLER);
                    Dalc.User().AddUser(user);
                }catch (Exception e){

                }
            }
        };
        UserUpdatedHandler userDataFetched = new UserUpdatedHandler() {
            public void invoke(List<User> users) {
                //raise event
                for (UserUpdatedHandler listener : duplicateUserEvent.listeners()) {
                    List<User> result = new ArrayList<User>();
                    result.add(user);
                    listener.invoke(result);
                }
            }
        };
        Dalc.User().userNotFound.addListener(userNotFound);
        Dalc.User().userDataFetched.addListener(userDataFetched);
        //check if user data already exists.
        Dalc.User().getUserByName(user.getUserName().toString().trim());
        //Dalc.User().AddUser(user);
        //
    }

    public void Add_SUPPER_User(User user) throws RequiredFiledException, InvalidUserException {
        if((!module.userType.equals(module.UserTypeSUPPER)) || (module.isLoggedIn = false)){
            throw new InvalidUserException();
        }
        //check required fields
        if (user.getUserName() == "" || user.getPassWord() == "") {
            throw new RequiredFiledException("Please complete all required fields.");
        }
        // Register interest in the user data not found.
        UserUpdatedHandler userNotFound = new UserUpdatedHandler() {
            public void invoke(List<User> users) {
                //add new user
                try{
                    user.setUserType(module.UserTypeSUPPER);
                    Dalc.User().AddUser(user);
                }catch (Exception e){

                }
            }
        };
        UserUpdatedHandler userDataFetched = new UserUpdatedHandler() {
            public void invoke(List<User> users) {
                //raise event
                for (UserUpdatedHandler listener : duplicateUserEvent.listeners()) {
                    List<User> result = new ArrayList<User>();
                    result.add(user);
                    listener.invoke(result);
                }
            }
        };
        Dalc.User().userNotFound.addListener(userNotFound);
        Dalc.User().userDataFetched.addListener(userDataFetched);
        //check if user data already exists.
        Dalc.User().getUserByName(user.getUserName().toString().trim());
        //Dalc.User().AddUser(user);
        //
    }

    public void AddUser(User user) throws RequiredFiledException {
        //check required fields
        if (user.getUserName() == "" || user.getPassWord() == "") {
            throw new RequiredFiledException("Please complete all required fields.");
        }
        // Register interest in the user data not found.
        UserUpdatedHandler userNotFound = new UserUpdatedHandler() {
            public void invoke(List<User> users) {
                //add new user
                try{
                    user.setUserType(module.UserTypeCUSTOMER);
                    Dalc.User().AddUser(user);
                }catch (Exception e){

                }
            }
        };
        UserUpdatedHandler userDataFetched = new UserUpdatedHandler() {
            public void invoke(List<User> users) {
                //raise event
                for (UserUpdatedHandler listener : duplicateUserEvent.listeners()) {
                    List<User> result = new ArrayList<User>();
                    result.add(user);
                    listener.invoke(result);
                }
            }
        };
        Dalc.User().userNotFound.addListener(userNotFound);
        Dalc.User().userDataFetched.addListener(userDataFetched);
        //check if user data already exists.
        Dalc.User().getUserByName(user.getUserName().toString().trim());
        //check if user data already exists.
        //Dalc.User().AddUser(user);
        //
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

    public void getUserByName(String userName) {
        Dalc.User().getUserByName(userName);
    }

    public void LogOut() {
        this.isLoggedIn = false;
        this.users.clear();
    }

    public boolean getIsLoggedIn() {
        return this.isLoggedIn;
    }

}
