package com.mickleentityltdnigeria.resturantapp.dalc;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mickleentityltdnigeria.resturantapp.data.model.Resturant;
import com.mickleentityltdnigeria.resturantapp.data.model.ResturantZipCodes;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.extensions.ResturantUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.UserUpdatedHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResturantDalc {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference resturantDB = database.getReference("Resturants");

    public Event<ResturantUpdatedHandler> resturantDataFetched = new Event<ResturantUpdatedHandler>();
    public Event<ResturantUpdatedHandler> newResturantAdded = new Event<ResturantUpdatedHandler>();
    public Event<ResturantUpdatedHandler> resturantUpdated = new Event<ResturantUpdatedHandler>();
    public Event<ResturantUpdatedHandler> resturantDeleted = new Event<ResturantUpdatedHandler>();
    public Event<ResturantUpdatedHandler> resturantNotFound = new Event<ResturantUpdatedHandler>();
    public Event<ResturantUpdatedHandler> duplicateResturantEvent = new Event<ResturantUpdatedHandler>();

    private List<Resturant> resturants = new ArrayList<Resturant>();

    public ResturantDalc() {
    }


    public void AddResturant(Resturant resturant){
        //Get ID from the system.
        String resturantID = resturantDB.push().getKey();//idGen.getInstance().getUUID();
        //Update model with acquired data.
        resturant.setResturantID(resturantID);
        //save data to the system.
        resturantDB.child(resturantID).setValue(resturant);
        //raise event
        for (ResturantUpdatedHandler listener : newResturantAdded.listeners()) {
            List<Resturant> result = new ArrayList<Resturant>();
            result.add(resturant);
            listener.invoke(result);
        }
    }

    public void UpdateResturant(Resturant resturant){
        //Get ID from the system.
        String resturantID =resturant.getResturantID();
        //save data to the system.
        resturantDB.child(resturantID).setValue(resturant);
        //raise event
        for (ResturantUpdatedHandler listener : resturantUpdated.listeners()) {
            List<Resturant> result = new ArrayList<Resturant>();
            result.add(resturant);
            listener.invoke(result);
        }
    }

    public void DeleteResturant(String resturantID){
        //save data to the system.
        resturantDB.child(resturantID).setValue(null);
        //raise event
        for (ResturantUpdatedHandler listener : resturantDeleted.listeners()) {
            List<Resturant> result = new ArrayList<Resturant>();
            result.add(null);
            listener.invoke(result);
        }
    }

    public Resturant getResturantByUserID(String userID){
        Resturant result = new Resturant();

        return result;
    }

    public Resturant getResturantByResturantID(String resturantID){
        Resturant result = new Resturant();

        return result;
    }

    public ResturantZipCodes getResturantZipCodes(String resturantID){
        ResturantZipCodes result = new ResturantZipCodes();

        return result;
    }


}
