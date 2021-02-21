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
}
