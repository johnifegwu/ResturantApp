package com.mickleentityltdnigeria.resturantapp.data;

import com.mickleentityltdnigeria.resturantapp.data.model.LoggedInUser;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserCredentialsException;
import com.mickleentityltdnigeria.resturantapp.extensions.module;
import com.mickleentityltdnigeria.resturantapp.service.UserService;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {
        UserService service = new UserService();
        try {
            if(service.LogIn(username, password)){
                User user = service.getUserByName(username);
                LoggedInUser realUser = new LoggedInUser( user.getUserName(),
                        user.getFirstName() + " " + user.getLastName()
                );
                module.userName = user.getUserName();
                module.isLoggedIn = true;
                return new Result.Success<>(realUser);
            }else {
                return new Result.Error(new IOException("Invalid userName or passWord."));
            }
        } catch (InvalidUserCredentialsException e) {
            return new Result.Error(new IOException(e.getMessage(), e));
        }
    }

    public void logout() {
        new UserService().LogOut();
        module.isLoggedIn = false;
        module.userName = "";
    }
}