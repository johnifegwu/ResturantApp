package com.mickleentityltdnigeria.resturantapp.utils;

import android.app.Activity;

import com.mickleentityltdnigeria.resturantapp.AppGlobals;
import com.mickleentityltdnigeria.resturantapp.dalc.CartDalc;
import com.mickleentityltdnigeria.resturantapp.dalc.Dalc;
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail;
import com.mickleentityltdnigeria.resturantapp.exceptions.NoNetworkException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class module {

    public static Activity activity;

    public static String Wifi_ENABLED = "Wifi enabled";
    public static String Mobile_Data_ENABLED = "Mobile data enabled";
    public static String No_Network = "No internet enabled";

    public static void checkNetwork() throws NoNetworkException {
        String status = NetworkHelper.getNetworkStatus(AppGlobals.getAppContext());
        if(status == null || status == module.No_Network){
            throw new NoNetworkException();
        }
    }

    public static List<FoodOrderDetail> orderDetails = new ArrayList<FoodOrderDetail>();
    public static List<CartItem> cartItems = new ArrayList<CartItem>();
    public static FoodItem foodItem;

    public static final String AddressTYPE_SHIPPING = "SHIPPING";
    public static final String AddressTYPE_BILLING = "BILLING";

    public static final String UserTypeSELLER = "SELLER";
    public static final String UserTypeCUSTOMER = "CUSTOMER";
    public static final String UserTypeSUPPER = "SUPPER";

    public static String userID = "";
    public static String userName = "";
    public static boolean isLoggedIn = false;
    public static String userType = "";
    public static String zipCode = "";
    public static  String country = "";
    public static  String state = "";

    //For customer Order
    public static String orderTrackCode = "";

    //For Sellers
    public boolean Resturant_Approved = false;
    public String Resturant_zipCodes = "";
    //Zip Codes derived from Resturant separated by spaces

    public static CartDalc MyShoppingCart;

    //Calculates the total quantity of items in the provided Shopping Cart.
    public static int getCartTotalQty(List<CartItem> cartItems){
        int total = 0;
        for(CartItem c:cartItems){
            total += c.foodQty;
        }
        return total;
    }

    //Calculates the total quantity of items in the provided Shopping Cart.
    public static double getCartTotalValue(List<CartItem> cartItems){
        double total = 0;
        for(CartItem c:cartItems){
            total += c.getSubTotal();
        }
        return total;
    }

}

