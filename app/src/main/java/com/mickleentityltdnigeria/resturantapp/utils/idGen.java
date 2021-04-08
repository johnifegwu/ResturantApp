package com.mickleentityltdnigeria.resturantapp.utils;

import java.util.UUID;

public class idGen{

    public static idGen mInstant;

    public static  idGen getInstance(){
        if(mInstant==null){
            mInstant = new idGen();
        }
        return mInstant;
    }

    public String getUUID(){
        return UUID.randomUUID().toString();
    }

    //A function for generating MICKLE-PAY WALLET ID.
    public String getWalletID(String currencyCode){
        String wID = currencyCode +  UUID.randomUUID().toString();
        return wID.toUpperCase();
    }
}
