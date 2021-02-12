package com.mickleentityltdnigeria.resturantapp.extensions;

import java.util.*;

// Define delegate for components that are interested in User changed
@FunctionalInterface
public interface UserUpdatedHandler {
        void invoke(String userName);
    }
