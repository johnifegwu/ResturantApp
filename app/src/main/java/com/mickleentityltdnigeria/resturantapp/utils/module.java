package com.mickleentityltdnigeria.resturantapp.utils;

import android.content.Context;
import android.content.Intent;

import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class module {

    public static List<CartItem> cartItems = new ArrayList<CartItem>();
    public static FoodItem foodItem;

    public static final String UserTypeSELLER = "SELLER";
    public static final String UserTypeCUSTOMER = "CUSTOMER";
    public static final String UserTypeSUPPER = "SUPPER";

    public static int userID = -1;
    public static String userName = "";
    public static boolean isLoggedIn = false;
    public static String userType = "";
    public static String zipCode = "";

    //For Sellers
    public boolean Resturant_Approved = false;
    public String Resturant_zipCodes = "";
    //Zip Codes derived from Resturant separated by spaces


}
