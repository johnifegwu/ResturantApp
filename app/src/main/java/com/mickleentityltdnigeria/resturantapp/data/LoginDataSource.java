package com.mickleentityltdnigeria.resturantapp.data;

import com.mickleentityltdnigeria.resturantapp.dalc.Dalc;
import com.mickleentityltdnigeria.resturantapp.data.model.LoggedInUser;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserCredentialsException;
import com.mickleentityltdnigeria.resturantapp.extensions.UserUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.service.Service;
import com.mickleentityltdnigeria.resturantapp.utils.module;
import com.mickleentityltdnigeria.resturantapp.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    Result<LoggedInUser> result;

    public Result<LoggedInUser> login(String username, String password) {
        // Register interest in the user data not found.
        UserUpdatedHandler userNotFound = new UserUpdatedHandler() {
            public void invoke(List<User> users) {
               result  = new Result.Error(new IOException("Invalid userName or passWord."));
            }
        };
        UserUpdatedHandler userDataFetched = new UserUpdatedHandler() {
            public void invoke(List<User> users) {
                if(users.get(0).getUserName().toString().trim().equals(username.trim()) && users.get(0).getPassWord().toString().trim().equals(password.trim())){
                    LoggedInUser realUser = new LoggedInUser( users.get(0).getUserName().toString().trim(),
                            users.get(0).getFirstName().toString().trim() + " " + users.get(0).getLastName().toString().trim()
                    );
                    module.userID = users.get(0).getUserID().toString();
                    module.userName = users.get(0).getUserName().toString().trim();
                    module.isLoggedIn = true;
                    module.userType = users.get(0).getUserType().toString().trim();
                    module.zipCode = users.get(0).getZipCode().toString().trim();
                    //
                    result = new Result.Success<>(realUser);
                }else{
                    result  = new Result.Error(new IOException("Invalid userName or passWord."));
                }
            }
        };
        Dalc.User().userNotFound.addListener(userNotFound);
        Dalc.User().userDataFetched.addListener(userDataFetched);
        //check if user data already exists.
        Service.user().User.getUserByName(username);
        //
        return result;
    }

    public void logout() {
        Service.user().LogOut();
        module.isLoggedIn = false;
        module.userName = "";
    }
}
