package com.mickleentityltdnigeria.resturantapp.utils;

import android.app.Activity;
import com.google.firebase.auth.FirebaseUser;
import com.mickleentityltdnigeria.resturantapp.AppGlobals;
import com.mickleentityltdnigeria.resturantapp.dalc.AddressDalc;
import com.mickleentityltdnigeria.resturantapp.dalc.CartDalc;
import com.mickleentityltdnigeria.resturantapp.dalc.CountryDalc;
import com.mickleentityltdnigeria.resturantapp.dalc.CurrentLocationDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.data.model.Country;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail;
import com.mickleentityltdnigeria.resturantapp.data.model.Resturant;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.exceptions.NoNetworkException;
import com.mickleentityltdnigeria.resturantapp.extensions.CountryChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.extensions.LoginSuccessHandler;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class module {

    public static Activity activity;

    public static String PACKAGE_NAME;

    public static String Wifi_ENABLED = "Wifi enabled";
    public static String Mobile_Data_ENABLED = "Mobile data enabled";
    public static String No_Network = "No internet enabled";

    public static void checkNetwork() throws NoNetworkException {
        String status = NetworkHelper.getNetworkStatus(AppGlobals.getAppContext());
        if(status == null || status == module.No_Network){
            throw new NoNetworkException();
        }
    }

    public static String toLowerCase(String s){
        return s.charAt(0) + s.substring(1).toLowerCase();
    }

    public static Event<LoginSuccessHandler> loginSuccessHandlerEvent = new Event<>();

    public  static void userSignedInSuccessfully(FirebaseUser user){
        //raise event
        for (LoginSuccessHandler listener : loginSuccessHandlerEvent.listeners()) {
            listener.invoke(user);
        }
    }

    public static List<FoodOrderDetail> orderDetails = new ArrayList<>();
    public static List<CartItem> cartItems = new ArrayList<>();
    public static FoodItem foodItem;
    public static AddressDalc addressDalc;

    public static final String AddressTYPE_SHIPPING = "SHIPPING";
    public static final String AddressTYPE_BILLING = "BILLING";

    //User types for merchants
    public static final String UserTypeSELLER = "SELLER";
    //Appended users
    public static final String UserTypeSELLER2 = "SELLER2";

    //User type for Customers
    public static final String UserTypeCUSTOMER = "CUSTOMER";

    //User types for system Administrators
    public static final String UserTypeSUPPER = "SUPPER";
    public static final String UserTypeSUPPER2 = "SUPPER2";

    public static User userData = new User();
    public static String userID = "";
    public static String userName = "";
    public static String firstName = "";
    public static String lastName = "";
    public static boolean isLoggedIn = false;
    public static String userType = "";
    public static String zipCode = "";
    public static  String country = "";
    public static  String state = "";
    public static String city = "";
    public static String locationID = "";

    //For customer Order
    public static String orderTrackCode = "";

    //For Sellers
    public boolean Resturant_Approved = false;
    public String Resturant_zipCodes = "";
    //Zip Codes derived from Resturant separated by spaces

    public static List<Country> myCountries = new ArrayList<>();
    public static CountryDalc myCountryDalc;

    public static void getCountries(){
        myCountryDalc = new CountryDalc();
        CountryChangedHandler countriesFetched = new CountryChangedHandler() {
            @Override
            public void invoke(List<Country> countries) {
                myCountries = countries;
            }
        };
        myCountryDalc.countriesFetched.addListener(countriesFetched);
        myCountryDalc.getCountries();
    }

    public static CurrentLocationDalc MyCurrentLocation;
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

    //Calculates the total Value of items in the provided FoodOrderDetails including MarkUps.
    public static double getFoodOrderTotalValue(List<FoodOrderDetail> foodOrderDetails){
        double total = 0;
        for(FoodOrderDetail o:foodOrderDetails){
            total += o.getSubTotal();
        }
        return total;
    }

    //Calculates the total Value of items in the provided FoodOrderDetails excluding MarkUps.
    public static double getFoodOrderTotalMerchantValue(List<FoodOrderDetail> foodOrderDetails){
        double total = 0;
        for(FoodOrderDetail o:foodOrderDetails){
            total += (o.getFoodPrice() * o.getFoodQty());
        }
        return total;
    }

    //Calculates the total MarkUp Value of items in the provided FoodOrderDetails.
    public static double getFoodOrderTotalSubMerchantValue(List<FoodOrderDetail> foodOrderDetails){
        double total = 0;
        for(FoodOrderDetail o:foodOrderDetails){
            total += o.getMarkUpValue();
        }
        return total;
    }

    //New Restaurant fields
    public static Resturant newResturant = new Resturant();
    public static byte[] newRestaurantImg = null;


}

