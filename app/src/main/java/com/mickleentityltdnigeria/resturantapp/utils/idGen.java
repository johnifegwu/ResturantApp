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
        String wID = currencyCode;
        int i,x;
        String s;
        //block one
        s = UUID.randomUUID().toString();
        i = s.length();
        x = i/2;
        wID += s.substring(1,x);
        //block two
        s = UUID.randomUUID().toString();
        i = s.length();
        x = i/2;
        wID += s.substring(x,i);
        //
        return wID.toUpperCase();
    }
}
