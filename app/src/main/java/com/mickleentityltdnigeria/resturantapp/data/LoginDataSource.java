package com.mickleentityltdnigeria.resturantapp.data;

import com.mickleentityltdnigeria.resturantapp.data.model.LoggedInUser;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserCredentialsException;
import com.mickleentityltdnigeria.resturantapp.service.Service;
import com.mickleentityltdnigeria.resturantapp.utils.module;
import com.mickleentityltdnigeria.resturantapp.service.UserService;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {
        try {
            if(Service.user().LogIn(username, password)){
                User user = Service.user().getUserByName(username);
                LoggedInUser realUser = new LoggedInUser( user.getUserName(),
                        user.getFirstName() + " " + user.getLastName()
                );
                module.userID = user.getUserID();
                module.userName = user.getUserName();
                module.isLoggedIn = true;
                module.userType = user.getUserType();
                module.zipCode = user.getZipCode();
                //
                return new Result.Success<>(realUser);
            }else {
                return new Result.Error(new IOException("Invalid userName or passWord."));
            }
        } catch (InvalidUserCredentialsException e) {
            return new Result.Error(new IOException(e.getMessage(), e));
        }
    }

    public void logout() {
        Service.user().LogOut();
        module.isLoggedIn = false;
        module.userName = "";
    }
}