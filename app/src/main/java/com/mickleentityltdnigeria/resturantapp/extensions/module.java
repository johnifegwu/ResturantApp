package com.mickleentityltdnigeria.resturantapp.extensions;

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

    public static  ShoppingCart shoppingCart = new ShoppingCart();

}
