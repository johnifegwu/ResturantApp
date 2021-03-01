package com.mickleentityltdnigeria.resturantapp.dalc;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem;
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.extensions.FoodItemUpdatedHandler;

import java.util.ArrayList;
import java.util.List;

public class FoodDalc {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference foodItemDB = database.getReference("fooditems");

    public Event<FoodItemUpdatedHandler> foodItemsFetched = new Event<FoodItemUpdatedHandler>();
    public Event<FoodItemUpdatedHandler> foodItemsNotFound = new Event<FoodItemUpdatedHandler>();
    public Event<FoodItemUpdatedHandler> foodItemsAdded = new Event<FoodItemUpdatedHandler>();
    public Event<FoodItemUpdatedHandler> foodItemsUpdated = new Event<FoodItemUpdatedHandler>();
    public Event<FoodItemUpdatedHandler> foodItemsDeleted = new Event<FoodItemUpdatedHandler>();

    public FoodDalc() {
    }


    public void AddFoodItem(FoodItem foodItem){
        //Get ID from the system.
        String foodID = foodItemDB.push().getKey();
        //Update model with acquired data.
        foodItem.setFoodID(foodID);
        //save cart to the system.
        foodItemDB.child(foodID).setValue(foodItem);
        //raise event
        for (FoodItemUpdatedHandler listener : foodItemsAdded.listeners()) {
            List<FoodItem> result = new ArrayList<FoodItem>();
            result.add(foodItem);
            listener.invoke(result);
        }
    }

    public void UpdateFoodItem(FoodItem foodItem){
        //Get ID from the system.
        String foodID = foodItem.getFoodID();
        //save cart to the system.
        foodItemDB.child(foodID).setValue(foodItem);
        //raise event
        for (FoodItemUpdatedHandler listener : foodItemsUpdated.listeners()) {
            List<FoodItem> result = new ArrayList<FoodItem>();
            result.add(foodItem);
            listener.invoke(result);
        }
    }

    public void DeleteFoodItem(String foodID){
        //save cart to the system.
        foodItemDB.child(foodID).setValue(null);
        //raise event
        for (FoodItemUpdatedHandler listener : foodItemsDeleted.listeners()) {
            List<FoodItem> result = new ArrayList<FoodItem>();
            listener.invoke(result);
        }
    }

    public void SearchFoodItems(String searchTerm, String zipCode){
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<FoodItem> result = new ArrayList<FoodItem>();
                        //
                        if (snapshot.getChildrenCount() > 1){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                FoodItem foodItem = userSnapshot.getValue(FoodItem.class);
                                if(foodItem.foodDesc.toString().contains(searchTerm)){
                                    result.add(foodItem);
                                }
                            }
                        }else{
                            FoodItem foodItem = snapshot.getValue(FoodItem.class);
                            if(foodItem.foodDesc.toString().contains(searchTerm)){
                                result.add(foodItem);
                            }
                        }
                        //raise event
                        for (FoodItemUpdatedHandler listener : foodItemsFetched.listeners()) {
                            listener.invoke(result);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (FoodItemUpdatedHandler listener : foodItemsNotFound.listeners()) {
                    List<FoodItem> result = new ArrayList<FoodItem>();
                    result.add(null);
                    listener.invoke(result);
                }
            }
        };
        //
        foodItemDB.addListenerForSingleValueEvent(onDataChangedListener);
        foodItemDB.orderByChild("zipCodes").startAt(zipCode).endAt(zipCode+"\uF7FF");
        //
    }

    public void getFoodItemsByUser(String userID){
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<FoodItem> result = new ArrayList<FoodItem>();
                        //
                        if (snapshot.getChildrenCount() > 1){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                FoodItem foodItem = userSnapshot.getValue(FoodItem.class);
                                result.add(foodItem);
                            }
                        }else{
                            FoodItem foodItem = snapshot.getValue(FoodItem.class);
                            result.add(foodItem);
                        }
                        //raise event
                        for (FoodItemUpdatedHandler listener : foodItemsFetched.listeners()) {
                            listener.invoke(result);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (FoodItemUpdatedHandler listener : foodItemsNotFound.listeners()) {
                    List<FoodItem> result = new ArrayList<FoodItem>();
                    listener.invoke(result);
                }
            }
        };
        //
        foodItemDB.addListenerForSingleValueEvent(onDataChangedListener);
        foodItemDB.orderByChild("userID").equalTo(userID);
        //
    }

    public void getFoodItemsByResturant(String resturantID){
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<FoodItem> result = new ArrayList<FoodItem>();
                        //
                        if (snapshot.getChildrenCount() > 1){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                FoodItem foodItem = userSnapshot.getValue(FoodItem.class);
                                result.add(foodItem);
                            }
                        }else{
                            FoodItem foodItem = snapshot.getValue(FoodItem.class);
                            result.add(foodItem);
                        }
                        //raise event
                        for (FoodItemUpdatedHandler listener : foodItemsFetched.listeners()) {
                            listener.invoke(result);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (FoodItemUpdatedHandler listener : foodItemsNotFound.listeners()) {
                    List<FoodItem> result = new ArrayList<FoodItem>();
                    listener.invoke(result);
                }
            }
        };
        //
        foodItemDB.addListenerForSingleValueEvent(onDataChangedListener);
        foodItemDB.orderByChild("resturantID").equalTo(resturantID);
        //
    }

    public void getFoodItemByFoodID(String foodID){
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<FoodItem> result = new ArrayList<FoodItem>();
                        //
                        if (snapshot.getChildrenCount() > 1){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                FoodItem foodItem = userSnapshot.getValue(FoodItem.class);
                                result.add(foodItem);
                            }
                        }else{
                            FoodItem foodItem = snapshot.getValue(FoodItem.class);
                            result.add(foodItem);
                        }
                        //raise event
                        for (FoodItemUpdatedHandler listener : foodItemsFetched.listeners()) {
                            listener.invoke(result);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (FoodItemUpdatedHandler listener : foodItemsNotFound.listeners()) {
                    List<FoodItem> result = new ArrayList<FoodItem>();
                    listener.invoke(result);
                }
            }
        };
        //
        foodItemDB.addListenerForSingleValueEvent(onDataChangedListener);
        foodItemDB.orderByChild("foodID").equalTo(foodID);
        //
    }


}
