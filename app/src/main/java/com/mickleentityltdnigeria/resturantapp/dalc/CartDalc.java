package com.mickleentityltdnigeria.resturantapp.dalc;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;

import java.util.ArrayList;
import java.util.List;

public class CartDalc
{

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference shoppingCartDB = database.getReference("shoppingCarts");

    public Event<CartItemChangedHandler> cartItemsFetched = new Event<CartItemChangedHandler>();
    public Event<CartItemChangedHandler> cartItemsNotFound = new Event<CartItemChangedHandler>();
    public Event<CartItemChangedHandler> cartItemsAdded = new Event<CartItemChangedHandler>();
    public Event<CartItemChangedHandler> cartItemsUpdated = new Event<CartItemChangedHandler>();
    public Event<CartItemChangedHandler> cartItemsDeleted = new Event<CartItemChangedHandler>();

    public CartDalc() {
    }

    public void AddCartItem(CartItem cartItem){
        //Get ID from the system.
        String cartID = shoppingCartDB.push().getKey();
        //Update model with acquired data.
        cartItem.setCartID(cartID);
        //save cart to the system.
        shoppingCartDB.child(cartID).setValue(cartItem);
        //raise event
        for (CartItemChangedHandler listener : cartItemsAdded.listeners()) {
            List<CartItem> result = new ArrayList<CartItem>();
            result.add(cartItem);
            listener.invoke(result);
        }
    }

    public void UpdateCart(CartItem cartItem){
        //Get ID from the system.
        String cartID = cartItem.getCartID();
        //save cart to the system.
        shoppingCartDB.child(cartID).setValue(cartItem);
        //raise event
        for (CartItemChangedHandler listener : cartItemsUpdated.listeners()) {
            List<CartItem> result = new ArrayList<CartItem>();
            result.add(cartItem);
            listener.invoke(result);
        }
    }

    public void DeleteCart(String cartID){
        //save cart to the system.
        shoppingCartDB.child(cartID).setValue(null);
        //raise event
        for (CartItemChangedHandler listener : cartItemsDeleted.listeners()) {
            List<CartItem> result = new ArrayList<CartItem>();
            listener.invoke(result);
        }
    }

    public void getCartItems(String userName){
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<CartItem> result = new ArrayList<CartItem>();
                        //
                        if (snapshot.hasChildren()){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                CartItem cartItem = userSnapshot.getValue(CartItem.class);
                                result.add(cartItem);
                            }
                            //raise event
                            for (CartItemChangedHandler listener : cartItemsFetched.listeners()) {
                                listener.invoke(result);
                            }
                        }
                    }
                }else{
                    //raise event
                    for (CartItemChangedHandler listener : cartItemsNotFound.listeners()) {
                        List<CartItem> result = new ArrayList<CartItem>();
                        listener.invoke(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (CartItemChangedHandler listener : cartItemsNotFound.listeners()) {
                    List<CartItem> result = new ArrayList<CartItem>();
                    listener.invoke(result);
                }
            }
        };
        //
        Query query = FirebaseDatabase.getInstance().getReference("shoppingCarts")
                .orderByChild("userName")
                .equalTo(userName);
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }

}
