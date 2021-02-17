package com.mickleentityltdnigeria.resturantapp.service;

import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserException;
import com.mickleentityltdnigeria.resturantapp.logic.CartLogic;

import java.util.List;

public class CartService {

    public CartLogic Cart = new CartLogic();

    public CartService() {
    }


    public void addCartItem(CartItem cartItem, String userName) throws InvalidUserException {
        this.Cart.AddCartItem(cartItem, userName);
    }

    public void UpdateCartItem(String cartID, int Qty, String userName) throws InvalidUserException {
        this.Cart.UpdateCartItem(cartID, Qty, userName);
    }

    public void DeleteCartItem(String cartID, String userName) throws InvalidUserException {
        this.Cart.DeleteCartItem(cartID, userName);
    }

    public List<CartItem> getCartItems(String userName){
        return this.Cart.getCartItems(userName);
    }

    public int countCartItemsQty(String userName){
        return this.Cart.countCartItemsQty(userName);
    }

}
