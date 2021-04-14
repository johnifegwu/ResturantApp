package com.mickleentityltdnigeria.resturantapp.dalc;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mickleentityltdnigeria.resturantapp.data.model.Address;
import com.mickleentityltdnigeria.resturantapp.data.model.ApplicationData;
import com.mickleentityltdnigeria.resturantapp.data.model.Country;
import com.mickleentityltdnigeria.resturantapp.extensions.AddressChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.ApplicationDataEvenHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.CountryChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;

import java.util.ArrayList;
import java.util.List;

public class ApplicationDataDalc {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference applicationDataDB = database.getReference("applicationData");

    public Event<ApplicationDataEvenHandler> onDataFetchedHandler = new Event<>();
    public Event<ApplicationDataEvenHandler> onDataNotFoundHandler = new Event<>();
    public Event<ApplicationDataEvenHandler> onDataAddedHandler = new Event<>();
    public Event<ApplicationDataEvenHandler> onDataUpdatedHandler = new Event<>();
    public Event<ApplicationDataEvenHandler> onDataDeletedHandler = new Event<>();


    public void addApplicationData(ApplicationData applicationData) {
        //Get ID from the system.
        String ID = applicationDataDB.push().getKey();
        //Update model with acquired data.
        applicationData.setID(ID);
        //save user to the system.
        assert ID != null;
        applicationDataDB.child(ID).setValue(applicationData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //raise event
                for (ApplicationDataEvenHandler listener : onDataAddedHandler.listeners()) {
                    List<ApplicationData> result = new ArrayList<>();
                    result.add(applicationData);
                    listener.invoke(result);
                }
            }
        });

    }

    public void updateApplicationData(ApplicationData applicationData) {
        applicationDataDB.child(applicationData.getID()).setValue(applicationData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //raise event
                for (ApplicationDataEvenHandler listener : onDataUpdatedHandler.listeners()) {
                    List<ApplicationData> result = new ArrayList<>();
                    result.add(applicationData);
                    listener.invoke(result);
                }
            }
        });
    }

    public void deleteApplicationData(ApplicationData applicationData) {
        applicationDataDB.child(applicationData.getID()).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //raise event
                for (ApplicationDataEvenHandler listener : onDataDeletedHandler.listeners()) {
                    List<ApplicationData> result = new ArrayList<>();
                    result.add(applicationData);
                    listener.invoke(result);
                }
            }
        });
    }

    public void getApplicationData(){
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<ApplicationData> result = new ArrayList<>();
                        //
                        if (snapshot.hasChildren()){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                ApplicationData applicationData = userSnapshot.getValue(ApplicationData.class);
                                result.add(applicationData);
                            }
                            //raise event
                            for (ApplicationDataEvenHandler listener : onDataFetchedHandler.listeners()) {
                                listener.invoke(result);
                            }
                        }
                    }
                }else{
                    //raise event
                    for (ApplicationDataEvenHandler listener : onDataNotFoundHandler.listeners()) {
                        List<ApplicationData> result = new ArrayList<>();
                        listener.invoke(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (ApplicationDataEvenHandler listener : onDataNotFoundHandler.listeners()) {
                    List<ApplicationData> result = new ArrayList<>();
                    listener.invoke(result);
                }
            }
        };
        //
        Query query = FirebaseDatabase.getInstance().getReference("applicationData")
                .orderByChild("companyName");
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }
}
