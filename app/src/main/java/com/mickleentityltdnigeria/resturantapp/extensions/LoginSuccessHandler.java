package com.mickleentityltdnigeria.resturantapp.extensions;

import com.google.firebase.auth.FirebaseUser;

@FunctionalInterface
public interface LoginSuccessHandler {
    void invoke(FirebaseUser user);
}
