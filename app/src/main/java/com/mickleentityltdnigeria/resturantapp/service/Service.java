package com.mickleentityltdnigeria.resturantapp.service;

import com.mickleentityltdnigeria.resturantapp.data.model.User;

public class Service {

    public static FoodService food(){
        return new FoodService();
    }

    public static CartService cart(){
        return new CartService();
    }

    public static UserService user(){
        return new UserService();
    }
}

