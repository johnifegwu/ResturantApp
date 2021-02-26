package com.mickleentityltdnigeria.resturantapp.dalc;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.exceptions.DuplicateUserException;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserCredentialsException;
import com.mickleentityltdnigeria.resturantapp.exceptions.RequiredFiledException;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.extensions.UserUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.idGen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDalc {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userDB = database.getReference("users");

    public Event<UserUpdatedHandler> userDataFetched = new Event<UserUpdatedHandler>();
    public Event<UserUpdatedHandler> newUserAdded = new Event<UserUpdatedHandler>();
    public Event<UserUpdatedHandler> userDataUpdated = new Event<UserUpdatedHandler>();
    public Event<UserUpdatedHandler> userDataDeleted = new Event<UserUpdatedHandler>();
    public Event<UserUpdatedHandler> userNotFound = new Event<UserUpdatedHandler>();
    public Event<UserUpdatedHandler> duplicateUserEvent = new Event<UserUpdatedHandler>();

    private List<User> users = new ArrayList<User>();

    public UserDalc() {
        // Write a message to the database
    }

    public void AddUser(User user)  {
        //Get ID from the system.
        String userID = userDB.push().getKey();//idGen.getInstance().getUUID();
        //Update model with acquired data.
        user.setUserID(userID);
        //save user to the system.
        userDB.child(userID).setValue(user);
        //raise event
        for (UserUpdatedHandler listener : newUserAdded.listeners()) {
            List<User> result = new ArrayList<User>();
            result.add(user);
            listener.invoke(result);
        }
    }

    public void UpdateUser(User user) {
        //Get ID from the system.
        String userID = user.getUserID();
        //save user to the system.
        userDB.child(userID).setValue(user);
        //raise event
        for (UserUpdatedHandler listener : userDataUpdated.listeners()) {
            List<User> result = new ArrayList<User>();
            result.add(user);
            listener.invoke(result);
        }
    }

    public void DeleteUser(String userID) {
        //save user to the system.
        userDB.child(userID).setValue(null);
        //raise event
        for (UserUpdatedHandler listener : userDataDeleted.listeners()) {
            List<User> result = new ArrayList<User>();
            listener.invoke(result);
        }
    }

    public void getUserByName(String userName) {
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<User> result = new ArrayList<User>();
                        //
                        if (snapshot.getChildrenCount() > 1){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                User user = userSnapshot.getValue(User.class);
                                result.add(user);
                            }
                        }else{
                            User user = snapshot.getValue(User.class);
                            result.add(user);
                        }
                        //raise event
                        for (UserUpdatedHandler listener : userDataFetched.listeners()) {
                            listener.invoke(result);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (UserUpdatedHandler listener : userNotFound.listeners()) {
                    List<User> result = new ArrayList<User>();
                    listener.invoke(result);
                }
            }
        };
        //
        //removeListener(onDataChangedListener);
        userDB.addListenerForSingleValueEvent(onDataChangedListener);
        userDB.orderByChild("userName").equalTo(userName);
        //
    }

    public void getUserByID(String userID) {
        ValueEventListener onDataChangedListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    //
                    List<User> result = new ArrayList<User>();
                   //
                    if (snapshot.getChildrenCount() > 1){
                        for(DataSnapshot userSnapshot:snapshot.getChildren()){
                            User user = userSnapshot.getValue(User.class);
                            result.add(user);
                        }
                    }else{
                        User user = snapshot.getValue(User.class);
                        result.add(user);
                    }
                    //raise event
                    for (UserUpdatedHandler listener : userDataFetched.listeners()) {
                        listener.invoke(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (UserUpdatedHandler listener : userNotFound.listeners()) {
                    List<User> result = new ArrayList<User>();
                    listener.invoke(result);
                }
            }
        };
        //
        removeListener(onDataChangedListener);
        userDB.addListenerForSingleValueEvent(onDataChangedListener);
        userDB.orderByChild("userID").equalTo(userID);
        //
    }

    private void removeListener(ValueEventListener l){
        try{
            userDB.removeEventListener(l);
        }catch (Exception e){

        }

    }

}
