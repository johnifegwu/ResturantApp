package com.mickleentityltdnigeria.resturantapp.extensions;

import com.mickleentityltdnigeria.resturantapp.data.model.Address;

import java.util.List;

@FunctionalInterface
public interface TimeOutEventHandler {
    void invoke(boolean SignOut);
}
