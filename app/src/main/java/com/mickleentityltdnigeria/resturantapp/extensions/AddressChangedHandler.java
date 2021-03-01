package com.mickleentityltdnigeria.resturantapp.extensions;

import com.mickleentityltdnigeria.resturantapp.data.model.Address;

import java.util.*;

// Define delegate for components that are interested in Address changed
@FunctionalInterface
public interface AddressChangedHandler {
    void invoke(List<Address> addresses);
}
