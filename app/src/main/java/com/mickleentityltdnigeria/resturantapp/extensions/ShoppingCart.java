package com.mickleentityltdnigeria.resturantapp.extensions;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingCart
{
    private HashMap<String,CartItem> cartItems;

    public ShoppingCart()
    {
        this.cartItems = new HashMap<String, CartItem>();
    }

    public Event<CartItemChangedHandler> cartItemChanged = new Event<CartItemChangedHandler>();

    public final void addCartItem(int itemId, Double itemPrice, String itemDesc, int itemQty, String itemImgUrl)
    {
        if(cartItems.get(itemDesc) != null){
            cartItems.get(itemDesc).setItemQty(cartItems.get(itemDesc).getItemQty() + itemQty);
        }else {
            cartItems.put(itemDesc, new CartItem(itemId, itemPrice, itemDesc, itemQty, itemImgUrl));
        }
        for (CartItemChangedHandler listener : cartItemChanged.listeners()) {
            listener.invoke(this.countCartItemsQty());
        }
    }

    public final void removeCartItem(String name)
    {
        cartItems.remove(name);
        for (CartItemChangedHandler listener : cartItemChanged.listeners())
        {
            listener.invoke(this.countCartItemsQty());
        }
    }

    public final void updateCartItem(String name, int Qty)
    {
        if(Qty > 0) {
            cartItems.get(name).setItemQty(Qty);
            for (CartItemChangedHandler listener : cartItemChanged.listeners()) {
                listener.invoke(this.countCartItemsQty());
            }
        }else{
            removeCartItem(name);
        }
    }

    public final HashMap<String,CartItem> getCartItems()
    {
        return this.cartItems;
    }

    public final List<CartItem> getCartItemsX()
    {
        List<CartItem> cartItemList = new ArrayList<CartItem>();
        for (CartItem c:this.cartItems.values()) {
            cartItemList.add(c);
        }
        return cartItemList;
    }

    public final int countCartItemsQty()
    {
        int result = 0;
        for (CartItem c : cartItems.values())
        {
            result += c.getItemQty();
        }

        return result;
    }

    public final double getCartTotal()
    {
        double tP = 0.0;
        for (CartItem c : cartItems.values())
        {
            tP += (c.getItemPrice()*c.getItemQty());
        }

        return (tP);
    }



}
