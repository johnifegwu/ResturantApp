package com.mickleentityltdnigeria.resturantapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AppGlobals extends Activity {
    private static Context appContext;

    public static Context getAppContext(){
        return appContext;
    }

    public static void setAppContext(Context context){
        appContext = context;
    }

    public static void StartActivity(Intent intent){
        appContext.startActivity(intent);
    }



}
