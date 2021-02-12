package com.mickleentityltdnigeria.resturantapp.data;

import com.mickleentityltdnigeria.resturantapp.data.model.LoggedInUser;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserCredentialsException;
import com.mickleentityltdnigeria.resturantapp.extensions.module;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            if(module.userDalc.LogIn(username, password)){
                LoggedInUser realUser = new LoggedInUser(java.util.UUID.randomUUID().toString(),
                        module.userDalc.getUsers().get(0).getFirstName() + " " + module.userDalc.getUsers().get(0).getLastName()
                );
                return new Result.Success<>(realUser);
            }else {
                return new Result.Error(new IOException("Invalid userName or passWord."));
            }
        } catch (InvalidUserCredentialsException e) {
            return new Result.Error(new IOException(e.getMessage(), e));
        }
    }

    public void logout() {
        module.userDalc.LogOut();
    }
}