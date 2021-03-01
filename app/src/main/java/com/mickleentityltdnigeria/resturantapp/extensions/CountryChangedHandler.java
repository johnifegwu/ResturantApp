package com.mickleentityltdnigeria.resturantapp.extensions;


import com.mickleentityltdnigeria.resturantapp.data.model.Country;

import java.util.List;

// Define delegate for components that are interested in cartItem changed
@FunctionalInterface
public interface CountryChangedHandler {
    void invoke(List<Country> countries);
}
