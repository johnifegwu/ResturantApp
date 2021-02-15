package com.mickleentityltdnigeria.resturantapp.logic;

import com.mickleentityltdnigeria.resturantapp.dalc.Dalc;
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserException;
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.List;

public class CartLogic {

    public Event<CartItemChangedHandler> cartItemChanged = new Event<CartItemChangedHandler>();

    public CartLogic() {
    }

    public void AddCartItem(CartItem cartItem, String userName) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        //
        Dalc.Cart().AddCartItem(cartItem);
        //
        for (CartItemChangedHandler listener : cartItemChanged.listeners()) {
            listener.invoke(this.countCartItemsQty(userName));
        }
    }

    public void UpdateCartItem(int cartID, int Qty, String userName) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        if(Qty > 0) {
            CartItem cartItem = Dalc.Cart().getCartItemByID(cartID);
            cartItem.setCartQty(Qty);
            Dalc.Cart().UpdateCart(cartItem);
        }else{
            Dalc.Cart().DeleteCart(cartID);
        }
        for (CartItemChangedHandler listener : cartItemChanged.listeners()) {
            listener.invoke(this.countCartItemsQty(userName));
        }
    }

    public void DeleteCartItem(int cartID, String userName) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        Dalc.Cart().DeleteCart(cartID);
        for (CartItemChangedHandler listener : cartItemChanged.listeners())
        {
            listener.invoke(this.countCartItemsQty(userName));
        }
    }


    public List<CartItem> getCartItems(String userName)
    {
       return Dalc.Cart().getCartItems(userName);
    }

    public int countCartItemsQty(String userName)
    {
        int result = 0;
        List<CartItem> cartItems = Dalc.Cart().getCartItems(userName);
        for (int i = 0; i < cartItems.size(); i++) {
            result += cartItems.get(i).getCartQty();
        }
        return result;
    }

    public double getCartTotal(String userName)
    {
        double tP = 0.0;
        List<CartItem> cartItems = Dalc.Cart().getCartItems(userName);
        for (int i = 0; i < cartItems.size(); i++)
        {
            tP += (cartItems.get(i).getFoodPrice()*cartItems.get(i).getCartQty());
        }

        return (tP);
    }

}
