package com.mickleentityltdnigeria.resturantapp.dalc;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.exceptions.DuplicateUserException;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserCredentialsException;
import com.mickleentityltdnigeria.resturantapp.exceptions.RequiredFiledException;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.extensions.UserUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.idGen;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDalc {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userDB = database.getReference("users");

    public Event<UserUpdatedHandler> userDataFetched = new Event<>();
    public Event<UserUpdatedHandler> newUserAdded = new Event<>();
    public Event<UserUpdatedHandler> newUserAppended = new Event<>();
    public Event<UserUpdatedHandler> userUnAppended = new Event<>();
    public Event<UserUpdatedHandler> userDataUpdated = new Event<>();
    public Event<UserUpdatedHandler> userDataDeleted = new Event<>();
    public Event<UserUpdatedHandler> userNotFound = new Event<>();
    public Event<UserUpdatedHandler> userTypeChanged = new Event<>();
    public Event<UserUpdatedHandler> duplicateUserEvent = new Event<>();

    private List<User> users = new ArrayList<User>();

    public UserDalc() {
        // Write a message to the database
    }

    public void AddUser(User user, FirebaseUser fbUser)  {
        //Get ID from the system.
        String userID = fbUser.getUid();
        //Update model with acquired data.
        user.setUserID(userID);
        //save user to the system.
        userDB.child(userID).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //raise event
                for (UserUpdatedHandler listener : newUserAdded.listeners()) {
                    List<User> result = new ArrayList<>();
                    result.add(user);
                    listener.invoke(result);
                }
            }
        });

    }

    public void AppendUser(String RestaurantID, String userRegisteredEmail, String userType){
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        if (snapshot.hasChildren()){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                User user = userSnapshot.getValue(User.class);
                                //Append user to the system.
                                userDB.child(user.getUserID()).child("resturantID").setValue(RestaurantID);
                                userDB.child(user.getUserID()).child("userType").setValue(userType);
                                //raise event
                                for (UserUpdatedHandler listener : newUserAppended.listeners()) {
                                    List<User> result = new ArrayList<>();
                                    listener.invoke(result);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        //
        Query query = FirebaseDatabase.getInstance().getReference("users")
                .orderByChild("userName")
                .equalTo(userRegisteredEmail);
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }

    public void unAppendUser(String userRegisteredEmail){
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        if (snapshot.hasChildren()){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                User user = userSnapshot.getValue(User.class);
                                //Append user to the system.
                                userDB.child(user.getUserID()).child("resturantID").setValue(null);
                                userDB.child(user.getUserID()).child("userType").setValue(module.UserTypeCUSTOMER);
                                //raise event
                                for (UserUpdatedHandler listener : userUnAppended.listeners()) {
                                    List<User> result = new ArrayList<>();
                                    listener.invoke(result);
                                }
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        //
        Query query = FirebaseDatabase.getInstance().getReference("users")
                .orderByChild("userName")
                .equalTo(userRegisteredEmail);
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }

    public void UpdateUser(User user) {
        //Get ID from the system.
        String userID = user.getUserID();
        //save user to the system.
        userDB.child(userID).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //raise event
                for (UserUpdatedHandler listener : userDataUpdated.listeners()) {
                    List<User> result = new ArrayList<User>();
                    result.add(user);
                    listener.invoke(result);
                }
            }
        });

    }

    public void UpdateUserType(String userID, String userType) {
        //save user to the system.
        userDB.child(userID).child("userType").setValue(userType).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //raise event
                for (UserUpdatedHandler listener : userTypeChanged.listeners()) {
                    List<User> result = new ArrayList<User>();
                    listener.invoke(result);
                }
            }
        });

    }

    public void DeleteUser(String userID) {
        //save user to the system.
        userDB.child(userID).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //raise event
                for (UserUpdatedHandler listener : userDataDeleted.listeners()) {
                    List<User> result = new ArrayList<User>();
                    listener.invoke(result);
                }
            }
        });

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
                        if (snapshot.hasChildren()){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                User user = userSnapshot.getValue(User.class);
                                result.add(user);
                            }
                            //raise event
                            for (UserUpdatedHandler listener : userDataFetched.listeners()) {
                                listener.invoke(result);
                            }
                        }
                    }
                }else{
                    //raise event
                    for (UserUpdatedHandler listener : userNotFound.listeners()) {
                        List<User> result = new ArrayList<User>();
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
        Query query = FirebaseDatabase.getInstance().getReference("users")
                .orderByChild("userName")
                .equalTo(userName);
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }

    public void getUsersByRestaurant(String resturantID) {
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<User> result = new ArrayList<User>();
                        //
                        if (snapshot.hasChildren()){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                User user = userSnapshot.getValue(User.class);
                                result.add(user);
                            }
                            //raise event
                            for (UserUpdatedHandler listener : userDataFetched.listeners()) {
                                listener.invoke(result);
                            }
                        }
                    }
                }else{
                    //raise event
                    for (UserUpdatedHandler listener : userNotFound.listeners()) {
                        List<User> result = new ArrayList<User>();
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
        Query query = FirebaseDatabase.getInstance().getReference("users")
                .orderByChild("resturantID")
                .equalTo(resturantID);
        query.addListenerForSingleValueEvent(onDataChangedListener);
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
                    if (snapshot.hasChildren()){
                        for(DataSnapshot userSnapshot:snapshot.getChildren()){
                            User user = userSnapshot.getValue(User.class);
                            result.add(user);
                        }
                        //raise event
                        for (UserUpdatedHandler listener : userDataFetched.listeners()) {
                            listener.invoke(result);
                        }
                    }
                }else {
                    //raise event
                    for (UserUpdatedHandler listener : userNotFound.listeners()) {
                        List<User> result = new ArrayList<User>();
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
        Query query = FirebaseDatabase.getInstance().getReference("users")
                .orderByChild("userID")
                .equalTo(userID);
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }

}
