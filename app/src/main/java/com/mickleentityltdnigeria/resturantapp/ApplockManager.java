package com.mickleentityltdnigeria.resturantapp;

import android.app.Application;

import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.extensions.TimeOutEventHandler;

public class ApplockManager {

    private static ApplockManager instance;
    private static DefaultApplock currentAppLocker;
    public static Event<TimeOutEventHandler> userTimedOut = new Event<>();

    public static ApplockManager getInstance() {
        if (instance == null) {
            instance = new ApplockManager();
        }
        return instance;
    }

    public void enableDefaultAppLockIfAvailable(Application currentApp) {

        currentAppLocker = new DefaultApplock(currentApp);
        TimeOutEventHandler timeOutEventHandler = new TimeOutEventHandler() {
            @Override
            public void invoke(boolean SignOut) {
                //raise event
                for (TimeOutEventHandler listener : userTimedOut.listeners()) {
                    listener.invoke(true);
                }
            }
        };
        currentAppLocker.userTimedOut.addListener(timeOutEventHandler);

    }

    public void updateTouch(){
        currentAppLocker.updateTouch();
    }
}