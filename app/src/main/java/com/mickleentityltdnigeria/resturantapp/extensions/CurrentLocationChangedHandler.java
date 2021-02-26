package com.mickleentityltdnigeria.resturantapp.extensions;

import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.data.model.CurrentLocation;

import java.util.List;

@FunctionalInterface
public interface CurrentLocationChangedHandler {
    void invoke(List<CurrentLocation> location);
}

