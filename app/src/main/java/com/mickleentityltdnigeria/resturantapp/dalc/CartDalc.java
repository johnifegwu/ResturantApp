package com.mickleentityltdnigeria.resturantapp.dalc;


import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartDalc
{

    public CartDalc() {
    }

    public void AddCartItem(CartItem cartItem){
        //TODO do database updates here.

    }

    public void UpdateCart(CartItem cartItem){
        //TODO do database updates here.

    }

    public void DeleteCart(int cartID){
        //TODO do database updates here.

    }

    public List<CartItem> getCartItems(String userName){
        //TODO do database updates here.
        List<CartItem> cartItems = new ArrayList<CartItem>();


        return cartItems;
    }

    public CartItem getCartItemByID(int cartID){
        //TODO do database updates here.
        CartItem cartItem = new CartItem();


        return cartItem;
    }

}
