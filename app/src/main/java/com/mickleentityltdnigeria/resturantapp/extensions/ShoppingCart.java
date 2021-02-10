package com.mickleentityltdnigeria.resturantapp.extensions;


import java.util.HashMap;

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
        cartItems.put(itemDesc, new CartItem( itemId, itemPrice, itemDesc, itemQty, itemImgUrl));
        for (CartItemChangedHandler listener : cartItemChanged.listeners())
        {
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

    public final HashMap<String,CartItem> getCartItems()
    {
        return this.cartItems;
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



}
