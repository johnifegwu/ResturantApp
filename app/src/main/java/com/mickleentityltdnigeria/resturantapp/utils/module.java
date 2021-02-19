package com.mickleentityltdnigeria.resturantapp.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.mickleentityltdnigeria.resturantapp.AppGlobals;
import com.mickleentityltdnigeria.resturantapp.dalc.Dalc;
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail;
import com.mickleentityltdnigeria.resturantapp.exceptions.NoNetworkException;

import java.util.ArrayList;
import java.util.List;

public class module {

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

    //For customer Order
    public static String orderTrackCode = "";

    //For Sellers
    public boolean Resturant_Approved = false;
    public String Resturant_zipCodes = "";
    //Zip Codes derived from Resturant separated by spaces

    public static double getCartTotal(String userName)
    {
        double tP = 0.0;
         cartItems = Dalc.Cart().getCartItems(userName);
        for (int i = 0; i < cartItems.size(); i++)
        {
            tP += (cartItems.get(i).getFoodPrice()*cartItems.get(i).getCartQty());
        }

        return (tP);
    }


}
