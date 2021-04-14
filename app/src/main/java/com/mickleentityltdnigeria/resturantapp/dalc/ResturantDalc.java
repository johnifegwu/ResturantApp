package com.mickleentityltdnigeria.resturantapp.dalc;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItemChild;
import com.mickleentityltdnigeria.resturantapp.data.model.Resturant;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.extensions.ResturantUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.ArrayList;
import java.util.List;

public class ResturantDalc {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference resturantDB = database.getReference("Resturants");

    public Event<ResturantUpdatedHandler> resturantDataFetched = new Event<>();
    public Event<ResturantUpdatedHandler> newResturantAdded = new Event<>();
    public Event<ResturantUpdatedHandler> resturantUpdated = new Event<>();
    public Event<ResturantUpdatedHandler> resturantDeleted = new Event<>();
    public Event<ResturantUpdatedHandler> resturantNotFound = new Event<>();

    public ResturantDalc() {
    }


    public void AddResturant(Resturant resturant){
        //Get ID from the system.
        String resturantID = resturantDB.push().getKey();//idGen.getInstance().getUUID();
        //Update model with acquired data.
        resturant.setResturantID(resturantID);
        //save data to the system.
        resturantDB.child(resturantID).setValue(resturant).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //raise event
                for (ResturantUpdatedHandler listener : newResturantAdded.listeners()) {
                    List<Resturant> result = new ArrayList<Resturant>();
                    result.add(resturant);
                    listener.invoke(result);
                }
            }
        });

    }

    public void UpdateResturant(Resturant resturant){
        //Get ID from the system.
        String resturantID =resturant.getResturantID();
        //save data to the system.
        resturantDB.child(resturantID).setValue(resturant).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //raise event
                for (ResturantUpdatedHandler listener : resturantUpdated.listeners()) {
                    List<Resturant> result = new ArrayList<Resturant>();
                    result.add(resturant);
                    listener.invoke(result);
                }
            }
        });

    }

    public void DeleteResturant(Resturant resturant){
        //save data to the system.
        resturantDB.child(resturant.getResturantID()).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //update userType
                UserDalc user = new UserDalc();
                user.UpdateUserType(resturant.getUserID(), module.UserTypeCUSTOMER);
                //raise event
                for (ResturantUpdatedHandler listener : resturantDeleted.listeners()) {
                    List<Resturant> result = new ArrayList<Resturant>();
                    result.add(null);
                    listener.invoke(result);
                }
            }
        });

    }

    public void getResturantByUserID(String userID){
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<Resturant> result = new ArrayList<Resturant>();
                        //
                        if (snapshot.hasChildren()){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                Resturant resturant = userSnapshot.getValue(Resturant.class);
                                result.add(resturant);
                            }
                            //raise event
                            for (ResturantUpdatedHandler listener : resturantDataFetched.listeners()) {
                                listener.invoke(result);
                            }
                        }
                    }
                }else{
                    //raise event
                    for (ResturantUpdatedHandler listener : resturantNotFound.listeners()) {
                        List<Resturant> result = new ArrayList<Resturant>();
                        listener.invoke(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (ResturantUpdatedHandler listener : resturantNotFound.listeners()) {
                    List<Resturant> result = new ArrayList<Resturant>();
                    listener.invoke(result);
                }
            }
        };
        //
        Query query = FirebaseDatabase.getInstance().getReference("Resturants")
                .orderByChild("userID")
                .equalTo(userID);
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }

    public void getResturantByResturantID(String resturantID){
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<Resturant> result = new ArrayList<Resturant>();
                        //
                        if (snapshot.hasChildren()){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                Resturant resturant = userSnapshot.getValue(Resturant.class);
                                result.add(resturant);
                            }
                            //raise event
                            for (ResturantUpdatedHandler listener : resturantDataFetched.listeners()) {
                                listener.invoke(result);
                            }
                        }
                    }
                }else{
                    //raise event
                    for (ResturantUpdatedHandler listener : resturantNotFound.listeners()) {
                        List<Resturant> result = new ArrayList<Resturant>();
                        listener.invoke(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (ResturantUpdatedHandler listener : resturantNotFound.listeners()) {
                    List<Resturant> result = new ArrayList<Resturant>();
                    listener.invoke(result);
                }
            }
        };
        //
        Query query = FirebaseDatabase.getInstance().getReference("Resturants")
                .orderByChild("resturantID")
                .equalTo(resturantID);
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }

    public void getRestaurantByIsApproved(boolean isApproved){
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<Resturant> result = new ArrayList<Resturant>();
                        //
                        if (snapshot.hasChildren()){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                Resturant resturant = userSnapshot.getValue(Resturant.class);
                                result.add(resturant);
                            }
                            //raise event
                            for (ResturantUpdatedHandler listener : resturantDataFetched.listeners()) {
                                listener.invoke(result);
                            }
                        }
                    }
                }else{
                    //raise event
                    for (ResturantUpdatedHandler listener : resturantNotFound.listeners()) {
                        List<Resturant> result = new ArrayList<Resturant>();
                        listener.invoke(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (ResturantUpdatedHandler listener : resturantNotFound.listeners()) {
                    List<Resturant> result = new ArrayList<Resturant>();
                    listener.invoke(result);
                }
            }
        };
        //
        Query query = FirebaseDatabase.getInstance().getReference("Resturants")
                .orderByChild("approved")
                .equalTo(isApproved);
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }

    public void getRestaurantByQueryString(String queryString){
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<Resturant> result = new ArrayList<Resturant>();
                        //
                        if (snapshot.hasChildren()){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                Resturant resturant = userSnapshot.getValue(Resturant.class);
                                result.add(resturant);
                            }
                            //raise event
                            for (ResturantUpdatedHandler listener : resturantDataFetched.listeners()) {
                                listener.invoke(result);
                            }
                        }
                    }
                }else{
                    //raise event
                    for (ResturantUpdatedHandler listener : resturantNotFound.listeners()) {
                        List<Resturant> result = new ArrayList<Resturant>();
                        listener.invoke(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (ResturantUpdatedHandler listener : resturantNotFound.listeners()) {
                    List<Resturant> result = new ArrayList<Resturant>();
                    listener.invoke(result);
                }
            }
        };
        //
        Query query = FirebaseDatabase.getInstance().getReference("Resturants")
                .orderByChild("queryString")
                .equalTo(queryString);
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }

    public void getResturantByCountry(String country){
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<Resturant> result = new ArrayList<Resturant>();
                        //
                        if (snapshot.hasChildren()){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                Resturant resturant = userSnapshot.getValue(Resturant.class);
                                result.add(resturant);
                            }
                            //raise event
                            for (ResturantUpdatedHandler listener : resturantDataFetched.listeners()) {
                                listener.invoke(result);
                            }
                        }
                    }
                }else{
                    //raise event
                    for (ResturantUpdatedHandler listener : resturantNotFound.listeners()) {
                        List<Resturant> result = new ArrayList<Resturant>();
                        listener.invoke(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (ResturantUpdatedHandler listener : resturantNotFound.listeners()) {
                    List<Resturant> result = new ArrayList<Resturant>();
                    listener.invoke(result);
                }
            }
        };
        //
        Query query = FirebaseDatabase.getInstance().getReference("Resturants")
                .orderByChild("country")
                .equalTo(country);
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }

}
