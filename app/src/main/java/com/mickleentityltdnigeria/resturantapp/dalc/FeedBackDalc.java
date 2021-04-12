package com.mickleentityltdnigeria.resturantapp.dalc;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mickleentityltdnigeria.resturantapp.data.model.FeedBack;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.extensions.FeedBackEventHandler;

import java.util.ArrayList;
import java.util.List;

public class FeedBackDalc {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference feedbackDB = database.getReference("feedback");

    public Event<FeedBackEventHandler> newFeedBackAdded = new Event<>();
    public Event<FeedBackEventHandler> feedBackUpdated = new Event<>();
    public Event<FeedBackEventHandler> feedBackFetched = new Event<>();
    public Event<FeedBackEventHandler> feedBackNotFound = new Event<>();

    public FeedBackDalc() {
    }

    public void addFeedBack(FeedBack feedBack){
        //Get ID from the system.
        String feedBackID = feedbackDB.push().getKey();
        //Update model with acquired data.
        feedBack.setFeedBackID(feedBackID);
        //save to the system.
        feedbackDB.child(feedBackID).setValue(feedBack).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //raise event
                for(FeedBackEventHandler listener : newFeedBackAdded.listeners()){
                    List<FeedBack> result = new ArrayList<>();
                    result.add(feedBack);
                    listener.invoke(result);
                }
            }
        });

    }

    public void updateFeedBack(FeedBack feedBack){
        //save to the system.
        feedbackDB.child(feedBack.feedBackID).setValue(feedBack).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //raise event
                for(FeedBackEventHandler listener : feedBackUpdated.listeners()){
                    List<FeedBack> result = new ArrayList<>();
                    result.add(feedBack);
                    listener.invoke(result);
                }
            }
        });

    }

    public void getFeedBack(boolean isResolved){
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<FeedBack> result = new ArrayList<>();
                        //
                        if (snapshot.hasChildren()){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                FeedBack feedBack = userSnapshot.getValue(FeedBack.class);
                                result.add(feedBack);
                            }
                        }else{
                            FeedBack feedBack = snapshot.getValue(FeedBack.class);
                            result.add(feedBack);
                        }
                        //raise event
                        for (FeedBackEventHandler listener : feedBackFetched.listeners()) {
                            listener.invoke(result);
                        }
                    }
                }else{
                    //raise event
                    for (FeedBackEventHandler listener : feedBackNotFound.listeners()) {
                        List<FeedBack> result = new ArrayList<>();
                        listener.invoke(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (FeedBackEventHandler listener : feedBackNotFound.listeners()) {
                    List<FeedBack> result = new ArrayList<>();
                    listener.invoke(result);
                }
            }
        };
        //
        Query query = FirebaseDatabase.getInstance().getReference("feedback")
                .orderByChild("isResolved")
                .equalTo(isResolved);
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }

}
