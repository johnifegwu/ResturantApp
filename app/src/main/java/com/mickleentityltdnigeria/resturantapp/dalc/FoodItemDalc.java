package com.mickleentityltdnigeria.resturantapp.dalc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItemChild;
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.extensions.FoodItemUpdatedHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import kotlin.random.URandomKt;

public class FoodItemDalc {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference foodItemDB = database.getReference("fooditems");

    public Event<FoodItemUpdatedHandler> foodItemsFetched = new Event<>();
    public Event<FoodItemUpdatedHandler> foodItemsNotFound = new Event<>();
    public Event<FoodItemUpdatedHandler> foodItemsAdded = new Event<>();
    public Event<FoodItemUpdatedHandler> foodItemsUpdated = new Event<>();
    public Event<FoodItemUpdatedHandler> foodItemsDeleted = new Event<>();

    public FoodItemDalc() {
    }


    public void AddFoodItem(FoodItem foodItem) {
        //Get ID from the system.
        String foodID = foodItemDB.push().getKey();
        //Update model with acquired data.
        foodItem.setFoodID(foodID);
        for (FoodItemChild f : foodItem.getZipCodes().values()) {
            f.setFoodID(foodID);
        }
        //save cart to the system.
        foodItemDB.child(foodID).setValue(foodItem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //raise event
                for (FoodItemUpdatedHandler listener : foodItemsAdded.listeners()) {
                    List<FoodItem> result = new ArrayList<>();
                    result.add(foodItem);
                    listener.invoke(result);
                }
            }
        });

    }

    public void UpdateFoodItem(FoodItem foodItem) {
        //Get ID from the system.
        String foodID = foodItem.getFoodID();
        //save cart to the system.
        foodItemDB.child(foodID).setValue(foodItem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //raise event
                for (FoodItemUpdatedHandler listener : foodItemsUpdated.listeners()) {
                    List<FoodItem> result = new ArrayList<FoodItem>();
                    result.add(foodItem);
                    listener.invoke(result);
                }
            }
        });
    }

    public void DeleteFoodItem(String foodID) {
        //save cart to the system.
        foodItemDB.child(foodID).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //raise event
                for (FoodItemUpdatedHandler listener : foodItemsDeleted.listeners()) {
                    List<FoodItem> result = new ArrayList<>();
                    listener.invoke(result);
                }
            }
        });

    }

    public void SearchFoodItems(String SearchParam, String zipCode) {
        //
        ValueEventListener onDataChangedListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<FoodItem> result = new ArrayList<>();
                        //
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                FoodItem foodItem = userSnapshot.getValue(FoodItem.class);
                                assert foodItem != null;
                                String d = foodItem.foodDesc;
                                if (d.toLowerCase().contains(SearchParam.toLowerCase())) {
                                    result.add(foodItem);
                                }
                            }
                            //raise event
                            for (FoodItemUpdatedHandler listener : foodItemsFetched.listeners()) {
                                ShuffleFoodItems(result);
                                listener.invoke(result);
                            }
                        }
                    }
                } else {
                    //raise event
                    for (FoodItemUpdatedHandler listener : foodItemsNotFound.listeners()) {
                        List<FoodItem> result = new ArrayList<>();
                        result.add(null);
                        listener.invoke(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (FoodItemUpdatedHandler listener : foodItemsNotFound.listeners()) {
                    List<FoodItem> result = new ArrayList<>();
                    listener.invoke(result);
                }
            }
        };
        //
        Query query = FirebaseDatabase.getInstance().getReference("fooditems")
                .orderByChild("zipCodes/"+zipCode+"/zipCode").equalTo(zipCode);
        //
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }

    private void ShuffleFoodItems(List<FoodItem> foodItems){
        int max = foodItems.size()-1;
        int min, j;
        Random rnd;
        for(int i = 0; i < foodItems.size(); i++){
            rnd = new Random();
            min = i+1;
            if(min == max){
               min = max-1;
            }
            j = min + rnd.nextInt(max);
            SwapFoodItem(i, j, foodItems);
        }
    }
    private void SwapFoodItem(int i, int j, List<FoodItem> foodItems){
        FoodItem temp_i = foodItems.get(i);
        foodItems.set(i,foodItems.get(j));
        foodItems.set(j, temp_i);
    }

    private void getFoodItem(String foodID, String SearchParam) {
        ValueEventListener onDataChangedListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<FoodItem> result = new ArrayList<>();
                        //
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                FoodItem foodItem = userSnapshot.getValue(FoodItem.class);
                                assert foodItem != null;
                                String d = foodItem.foodDesc;
                                if (d.toLowerCase().contains(SearchParam.toLowerCase())) {
                                    result.add(foodItem);
                                }
                            }
                            //raise event
                            for (FoodItemUpdatedHandler listener : foodItemsFetched.listeners()) {
                                listener.invoke(result);
                            }
                        }
                    }
                } else {
                    //raise event
                    for (FoodItemUpdatedHandler listener : foodItemsNotFound.listeners()) {
                        List<FoodItem> result = new ArrayList<FoodItem>();
                        result.add(null);
                        listener.invoke(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (FoodItemUpdatedHandler listener : foodItemsNotFound.listeners()) {
                    List<FoodItem> result = new ArrayList<>();
                    listener.invoke(result);
                }
            }
        };
        //
        Query query = FirebaseDatabase.getInstance().getReference("fooditems")
                .orderByChild("foodID")
                .equalTo(foodID);
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }

    public void getFoodItemsByUser(String userID) {
        ValueEventListener onDataChangedListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<FoodItem> result = new ArrayList<>();
                        //
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                FoodItem foodItem = userSnapshot.getValue(FoodItem.class);
                                result.add(foodItem);
                            }
                            //raise event
                            for (FoodItemUpdatedHandler listener : foodItemsFetched.listeners()) {
                                listener.invoke(result);
                            }
                        }
                    }
                } else {
                    //raise event
                    for (FoodItemUpdatedHandler listener : foodItemsNotFound.listeners()) {
                        List<FoodItem> result = new ArrayList<FoodItem>();
                        result.add(null);
                        listener.invoke(result);
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
        Query query = FirebaseDatabase.getInstance().getReference("fooditems")
                .orderByChild("userID")
                .equalTo(userID);
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }

    public void getFoodItemsByResturant(String resturantID) {
        ValueEventListener onDataChangedListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<FoodItem> result = new ArrayList<FoodItem>();
                        //
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                FoodItem foodItem = userSnapshot.getValue(FoodItem.class);
                                result.add(foodItem);
                            }
                            //raise event
                            for (FoodItemUpdatedHandler listener : foodItemsFetched.listeners()) {
                                listener.invoke(result);
                            }
                        }
                    }
                } else {
                    //raise event
                    for (FoodItemUpdatedHandler listener : foodItemsNotFound.listeners()) {
                        List<FoodItem> result = new ArrayList<FoodItem>();
                        result.add(null);
                        listener.invoke(result);
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
        Query query = FirebaseDatabase.getInstance().getReference("fooditems")
                .orderByChild("resturantID")
                .equalTo(resturantID);
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }

    public void getFoodItemByFoodID(String foodID) {
        ValueEventListener onDataChangedListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<FoodItem> result = new ArrayList<FoodItem>();
                        //
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                FoodItem foodItem = userSnapshot.getValue(FoodItem.class);
                                result.add(foodItem);
                            }
                            //raise event
                            for (FoodItemUpdatedHandler listener : foodItemsFetched.listeners()) {
                                listener.invoke(result);
                            }
                        }
                    }
                } else {
                    //raise event
                    for (FoodItemUpdatedHandler listener : foodItemsNotFound.listeners()) {
                        List<FoodItem> result = new ArrayList<FoodItem>();
                        result.add(null);
                        listener.invoke(result);
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
        Query query = FirebaseDatabase.getInstance().getReference("fooditems")
                .orderByChild("foodID")
                .equalTo(foodID);
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }


}
