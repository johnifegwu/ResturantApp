package com.mickleentityltdnigeria.resturantapp.extensions;

import com.mickleentityltdnigeria.resturantapp.data.model.User;

import java.util.*;

// Define delegate for components that are interested in User changed
@FunctionalInterface
public interface UserUpdatedHandler {
        void invoke(List<User> users);
    }
