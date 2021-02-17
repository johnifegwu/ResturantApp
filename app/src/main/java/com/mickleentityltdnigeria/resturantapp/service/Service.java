package com.mickleentityltdnigeria.resturantapp.service;

import com.mickleentityltdnigeria.resturantapp.data.model.User;

public class Service {

    public static FoodService food(){
        return new FoodService();
    }

    public static FoodOrderService foodOder(){
        return new FoodOrderService();
    }

    public static CartService cart(){
        return new CartService();
    }

    public static UserService user(){
        return new UserService();
    }

    public static ResturantService resturant(){
        return new ResturantService();
    }

    public static AddressService address(){
        return new AddressService();
    }

}

