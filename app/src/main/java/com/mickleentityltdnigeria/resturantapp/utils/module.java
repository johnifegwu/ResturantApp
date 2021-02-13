package com.mickleentityltdnigeria.resturantapp.utils;

import android.content.Context;
import android.content.Intent;

import com.mickleentityltdnigeria.resturantapp.ShoppingCartActivity;
import com.mickleentityltdnigeria.resturantapp.Show_Pic_Activity;

public class module {

    public static Intent genIntentForShowPic(Context c){
        return  new Intent(c, Show_Pic_Activity.class);
    }

    public static Intent genIntentForShoppingCart(Context c){
        return  new Intent(c, ShoppingCartActivity.class);
    }

    public static final String UserTypeSELLER = "SELLER";
    public static final String UserTypeCUSTOMER = "CUSTOMER";

    public static int userID = -1;
    public static String userName = "";
    public static boolean isLoggedIn = false;
    public static String userType = "";
    public static String zipCode = "";


}
