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
import com.mickleentityltdnigeria.resturantapp.data.model.Country;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.extensions.AddressChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.extensions.UserUpdatedHandler;

import java.util.ArrayList;
import java.util.List;

public class AddressDalc {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference addressDB = database.getReference("addresses");

    public Event<AddressChangedHandler> addressesFetched = new Event<AddressChangedHandler>();
    public Event<AddressChangedHandler> addressNotFound = new Event<AddressChangedHandler>();
    public Event<AddressChangedHandler> addressAdded = new Event<AddressChangedHandler>();
    public Event<AddressChangedHandler> addressUpdated = new Event<AddressChangedHandler>();
    public Event<AddressChangedHandler> addressDeleted = new Event<AddressChangedHandler>();

    public AddressDalc() {
    }

    public void AddAddress(Address address) {
        //Get ID from the system.
        String addressID = addressDB.push().getKey();
        //Update model with acquired data.
        address.setAddressID(addressID);
        //save user to the system.
        assert addressID != null;
        addressDB.child(addressID).setValue(address).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //raise event
                for (AddressChangedHandler listener : addressAdded.listeners()) {
                    List<Address> result = new ArrayList<>();
                    result.add(address);
                    listener.invoke(result);
                }
            }
        });

    }

    public void UpdateAddress(Address address) {
        //save user to the system.
        addressDB.child(address.getAddressID()).setValue(address).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //raise event
                for (AddressChangedHandler listener : addressUpdated.listeners()) {
                    List<Address> result = new ArrayList<Address>();
                    result.add(address);
                    listener.invoke(result);
                }
            }
        });
    }

    public void DeleteAddress(Address address) {
        //save user to the system.
        addressDB.child(address.getAddressID()).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //raise event
                for (AddressChangedHandler listener : addressDeleted.listeners()) {
                    List<Address> result = new ArrayList<Address>();
                    result.add(address);
                    listener.invoke(result);
                }
            }
        });

    }

    public void getAddresses(String userID) {
        ValueEventListener onDataChangedListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<Address> result = new ArrayList<Address>();
                        //
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                Address address = userSnapshot.getValue(Address.class);
                                result.add(address);
                            }
                        } else {
                            Address address = snapshot.getValue(Address.class);
                            result.add(address);
                        }
                        //raise event
                        for (AddressChangedHandler listener : addressesFetched.listeners()) {
                            listener.invoke(result);
                        }
                    }
                } else {
                    //raise event
                    for (AddressChangedHandler listener : addressNotFound.listeners()) {
                        List<Address> result = new ArrayList<Address>();
                        listener.invoke(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (AddressChangedHandler listener : addressNotFound.listeners()) {
                    List<Address> result = new ArrayList<Address>();
                    listener.invoke(result);
                }
            }
        };
        //
        Query query = FirebaseDatabase.getInstance().getReference("addresses")
                .orderByChild("userID")
                .equalTo(userID);
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }

    public List<String> getAddressList(List<Address> addresses) {
        List<String> result = new ArrayList<String>();
        for (Address a : addresses) {
            result.add(a.contactAddress);
        }
        return result;
    }

}
