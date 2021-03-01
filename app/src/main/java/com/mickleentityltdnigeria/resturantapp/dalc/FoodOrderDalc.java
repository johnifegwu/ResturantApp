package com.mickleentityltdnigeria.resturantapp.dalc;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mickleentityltdnigeria.resturantapp.data.model.Address;
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrder;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.extensions.FoodItemUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.FoodOrderDetailsEventHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.FoodOrderEventHandler;
import com.mickleentityltdnigeria.resturantapp.utils.idGen;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FoodOrderDalc {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference foodOrderDB = database.getReference("foodorders");
    DatabaseReference foodOrderDetailDB = database.getReference("foodorderdetails");

    public Event<FoodOrderEventHandler> foodOrdersFetched = new Event<FoodOrderEventHandler>();
    public Event<FoodOrderEventHandler> foodOrdersNotFound = new Event<FoodOrderEventHandler>();
    public Event<FoodOrderEventHandler> foodOrdersAdded = new Event<FoodOrderEventHandler>();
    public Event<FoodOrderEventHandler> foodOrdersUpdated = new Event<FoodOrderEventHandler>();

    public Event<FoodOrderDetailsEventHandler> foodOrderDetailsFetched = new Event<FoodOrderDetailsEventHandler>();
    public Event<FoodOrderDetailsEventHandler> foodOrderDetailsNotFound = new Event<FoodOrderDetailsEventHandler>();
    public Event<FoodOrderDetailsEventHandler> foodOrderDetailsAdded = new Event<FoodOrderDetailsEventHandler>();
    public Event<FoodOrderDetailsEventHandler> foodOrderDetailsUpdated = new Event<FoodOrderDetailsEventHandler>();

    public FoodOrderDalc() {
    }

    //Adds new order to the system and returns the order tracking Code.
    public void PlaceOrder(List<CartItem> cart, Address paymentAddress, Address shippingAddress) throws Exception {
        //
        if(shippingAddress.city != module.city || shippingAddress.country != module.country){
            throw new Exception("Shipping address Country and City must be the same with your current location.");
        }
        String guid = idGen.getInstance().getUUID();
        String orderID = foodOrderDB.push().getKey();
        //
        FoodOrder order = new FoodOrder(orderID, module.userID,new Date(),guid,paymentAddress.getContactAddress(),
                paymentAddress.getCity(),paymentAddress.getZipCode(),paymentAddress.getState(),paymentAddress.getCountry(),shippingAddress.getContactAddress(),
                shippingAddress.getCity(), shippingAddress.getZipCode(),shippingAddress.getState(),shippingAddress.getCountry());
        //save cart to the system.
        foodOrderDB.child(orderID).setValue(order);
        //Save Order details here.
        List<FoodOrderDetail> orderDetails = new ArrayList<FoodOrderDetail>();
        for (CartItem c:cart) {
            String ID = foodOrderDetailDB.push().getKey();
            //
            FoodOrderDetail orderDetail = new FoodOrderDetail(ID,orderID,c.getUserID(),c.getFoodID(),c.getResturantID(),c.getFoodPrice(),c.getCurrency(),
                    c.getFoodQty(),false,0.0,0.0,"",false,new Date(),"",false,
                    new Date(),"", "",false);
            //
            foodOrderDetailDB.child(ID).setValue(orderDetail);
            orderDetails.add(orderDetail);
            //
        }
        //raise event
        for (FoodOrderEventHandler listener : foodOrdersAdded.listeners()) {
            List<FoodOrder> result = new ArrayList<FoodOrder>();
            result.add(order);
            listener.invoke(result);
        }
        //raise event
        for (FoodOrderDetailsEventHandler listener : foodOrderDetailsAdded.listeners()) {
            listener.invoke(orderDetails);
        }
    }

    public void updateFoodOrder(FoodOrder order){
        //save cart to the system.
        foodOrderDB.child(order.orderID).setValue(order);
        //raise event
        for (FoodOrderEventHandler listener : foodOrdersUpdated.listeners()) {
            List<FoodOrder> result = new ArrayList<FoodOrder>();
            result.add(order);
            listener.invoke(result);
        }
    }

    public void updateFoodOrderDetails(FoodOrderDetail orderDetail){
        //
        foodOrderDetailDB.child(orderDetail.ID).setValue(orderDetail);
        //raise event
        for (FoodOrderDetailsEventHandler listener : foodOrderDetailsAdded.listeners()) {
            List<FoodOrderDetail> result = new ArrayList<FoodOrderDetail>();
            result.add(orderDetail);
            listener.invoke(result);
        }
    }

    public void getFoodOrderByOrderID(String orderID){
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<FoodOrder> result = new ArrayList<FoodOrder>();
                        //
                        if (snapshot.getChildrenCount() > 1){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                FoodOrder foodOrder = userSnapshot.getValue(FoodOrder.class);
                                result.add(foodOrder);
                            }
                        }else{
                            FoodOrder foodOrder = snapshot.getValue(FoodOrder.class);
                            result.add(foodOrder);
                        }
                        //raise event
                        for (FoodOrderEventHandler listener : foodOrdersFetched.listeners()) {
                            listener.invoke(result);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (FoodOrderEventHandler listener : foodOrdersNotFound.listeners()) {
                    List<FoodOrder> result = new ArrayList<FoodOrder>();
                    listener.invoke(result);
                }
            }
        };
        //
        foodOrderDB.addListenerForSingleValueEvent(onDataChangedListener);
        foodOrderDB.orderByChild("orderID").equalTo(orderID);
        //
    }

    public void getFoodOrderDetailsByUserID(String userID){
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<FoodOrderDetail> result = new ArrayList<FoodOrderDetail>();
                        //
                        if (snapshot.getChildrenCount() > 1){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                FoodOrderDetail foodOrderDetail = userSnapshot.getValue(FoodOrderDetail.class);
                                result.add(foodOrderDetail);
                            }
                        }else{
                            FoodOrderDetail foodOrderDetail = snapshot.getValue(FoodOrderDetail.class);
                            result.add(foodOrderDetail);
                        }
                        //raise event
                        for (FoodOrderDetailsEventHandler listener : foodOrderDetailsFetched.listeners()) {
                            listener.invoke(result);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (FoodOrderDetailsEventHandler listener : foodOrderDetailsNotFound.listeners()) {
                    List<FoodOrderDetail> result = new ArrayList<FoodOrderDetail>();
                    listener.invoke(result);
                }
            }
        };
        //
        foodOrderDB.addListenerForSingleValueEvent(onDataChangedListener);
        foodOrderDB.orderByChild("userID").equalTo(userID);
        //
    }

    public void getFoodOrderDetailsByQueryString(String resturantID, boolean isCanceled, boolean isShipped, boolean isDelivered){
        String queryString = module.getQueryString(resturantID, isCanceled, isShipped, isDelivered);
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<FoodOrderDetail> result = new ArrayList<FoodOrderDetail>();
                        //
                        if (snapshot.getChildrenCount() > 1){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                FoodOrderDetail foodOrderDetail = userSnapshot.getValue(FoodOrderDetail.class);
                                result.add(foodOrderDetail);
                            }
                        }else{
                            FoodOrderDetail foodOrderDetail = snapshot.getValue(FoodOrderDetail.class);
                            result.add(foodOrderDetail);
                        }
                        //raise event
                        for (FoodOrderDetailsEventHandler listener : foodOrderDetailsFetched.listeners()) {
                            listener.invoke(result);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (FoodOrderDetailsEventHandler listener : foodOrderDetailsNotFound.listeners()) {
                    List<FoodOrderDetail> result = new ArrayList<FoodOrderDetail>();
                    listener.invoke(result);
                }
            }
        };
        //
        foodOrderDB.addListenerForSingleValueEvent(onDataChangedListener);
        foodOrderDB.orderByChild("queryString").equalTo(queryString);
        //
    }
}
