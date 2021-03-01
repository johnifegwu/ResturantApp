package com.mickleentityltdnigeria.resturantapp.dalc;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.data.model.CurrentLocation;
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.CurrentLocationChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;

import java.util.ArrayList;
import java.util.List;

public class CurrentLocationDalc {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference currentLocationDB = database.getReference("currentLocations");

    public Event<CurrentLocationChangedHandler> locationsFetched = new Event<CurrentLocationChangedHandler>();
    public Event<CurrentLocationChangedHandler> locationsNotFound = new Event<CurrentLocationChangedHandler>();
    public Event<CurrentLocationChangedHandler> locationsAdded = new Event<CurrentLocationChangedHandler>();
    public Event<CurrentLocationChangedHandler> locationsUpdated = new Event<CurrentLocationChangedHandler>();

    public CurrentLocationDalc() {
    }

    public void AddCurrentLocation(CurrentLocation location){
        //Get ID from the system.
        String locationID = currentLocationDB.push().getKey();
        //Update model with acquired data.
        location.setLocationID(locationID);
        //save location to the system.
        currentLocationDB.child(locationID).setValue(location);
        //raise event
        for (CurrentLocationChangedHandler listener : locationsAdded.listeners()) {
            List<CurrentLocation> result = new ArrayList<CurrentLocation>();
            result.add(location);
            listener.invoke(result);
        }
    }

    public void UpdateCurrentLocation(CurrentLocation location){
        //Get ID from the system.
        String locationID = location.getLocationID();
        //save location to the system.
        currentLocationDB.child(locationID).setValue(location);
        //raise event
        for (CurrentLocationChangedHandler listener : locationsUpdated.listeners()) {
            List<CurrentLocation> result = new ArrayList<CurrentLocation>();
            result.add(location);
            listener.invoke(result);
        }
    }

    public void GetCurrentLocation(String userID){
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<CurrentLocation> result = new ArrayList<CurrentLocation>();
                        //
                        if (snapshot.getChildrenCount() > 1){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                CurrentLocation location = userSnapshot.getValue(CurrentLocation.class);
                                result.add(location);
                            }
                        }else{
                            CurrentLocation location = snapshot.getValue(CurrentLocation.class);
                            result.add(location);
                        }
                        //raise event
                        for (CurrentLocationChangedHandler listener : locationsFetched.listeners()) {
                            listener.invoke(result);
                        }
                    }
                }else{
                    //raise event
                    for (CurrentLocationChangedHandler listener : locationsNotFound.listeners()) {
                        List<CurrentLocation> result = new ArrayList<CurrentLocation>();
                        listener.invoke(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (CurrentLocationChangedHandler listener : locationsNotFound.listeners()) {
                    List<CurrentLocation> result = new ArrayList<CurrentLocation>();
                    listener.invoke(result);
                }
            }
        };
        //
        currentLocationDB.addListenerForSingleValueEvent(onDataChangedListener);
        currentLocationDB.orderByChild("userID").equalTo(userID);
        //
    }

}
