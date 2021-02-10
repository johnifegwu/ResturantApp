package com.mickleentityltdnigeria.resturantapp.extensions;

import java.util.*;

// Define delegate for components that are interested in cartItem changed
@FunctionalInterface
public interface CartItemChangedHandler
{
    void invoke(int qty);
}
