package com.mickleentityltdnigeria.resturantapp.extensions;

import android.content.Context;
import android.content.Intent;

import com.mickleentityltdnigeria.resturantapp.Show_Pic_Activity;

public class module {

    public static Intent genIntentForShowPic(Context c){
        return  new Intent(c, Show_Pic_Activity.class);
    }

    public static  ShoppingCart shoppingCart = new ShoppingCart();
}
