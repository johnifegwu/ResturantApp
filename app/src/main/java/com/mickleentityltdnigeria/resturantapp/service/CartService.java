package com.mickleentityltdnigeria.resturantapp.service;

import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.logic.CartLogic;

import java.util.List;

public class CartService {

    public CartLogic Cart = new CartLogic();

    public CartService() {
    }


    public void addCartItem(CartItem cartItem, String userName){
        this.Cart.AddCartItem(cartItem, userName);
    }

    public void UpdateCartItem(int cartID, int Qty, String userName){
        this.Cart.UpdateCartItem(cartID, Qty, userName);
    }

    public void DeleteCartItem(int cartID, String userName){
        this.Cart.DeleteCartItem(cartID, userName);
    }

    public List<CartItem> getCartItems(String userName){
        return this.Cart.getCartItems(userName);
    }

    public int countCartItemsQty(String userName){
        return this.Cart.countCartItemsQty(userName);
    }

    public double getCartTotal(String userName){
        return this.Cart.getCartTotal(userName);
    }

}
