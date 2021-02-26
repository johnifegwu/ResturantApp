package com.mickleentityltdnigeria.resturantapp.extensions;

import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;

import java.util.*;

// Define delegate for components that are interested in cartItem changed
@FunctionalInterface
public interface CartItemChangedHandler
{
    void invoke(List<CartItem> cartItem);
}
