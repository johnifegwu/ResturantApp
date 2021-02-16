package com.mickleentityltdnigeria.resturantapp.extensions;

import java.util.*;

// Define delegate for components that are interested in Address changed
@FunctionalInterface
public interface AddressChangedHandler {
    void invoke(String address);
}
