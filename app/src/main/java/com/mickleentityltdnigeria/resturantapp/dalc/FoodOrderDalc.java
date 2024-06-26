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
import com.mickleentityltdnigeria.resturantapp.data.ReportIndex;
import com.mickleentityltdnigeria.resturantapp.data.ReportIndexEventHandler;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FoodOrderDalc {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference foodOrderDB = database.getReference("foodorders");
    DatabaseReference foodOrderDetailDB = database.getReference("foodorderdetails");

    public Event<FoodOrderEventHandler> foodOrdersFetched = new Event<>();
    public Event<FoodOrderEventHandler> foodOrdersNotFound = new Event<>();
    public Event<FoodOrderEventHandler> foodOrdersAdded = new Event<>();
    public Event<FoodOrderEventHandler> foodOrdersUpdated = new Event<>();

    public Event<FoodOrderDetailsEventHandler> foodOrderDetailsFetched = new Event<>();
    public Event<FoodOrderDetailsEventHandler> foodOrderDetailsNotFound = new Event<>();
    public Event<FoodOrderDetailsEventHandler> foodOrderDetailsAdded = new Event<>();
    public Event<FoodOrderDetailsEventHandler> foodOrderDetailsUpdated = new Event<>();

    public Event<ReportIndexEventHandler> reportIndexFetched = new Event<>();
    public Event<ReportIndexEventHandler> reportIndexNotFound = new Event<>();

    public FoodOrderDalc() {
    }

    //Adds new order to the system and returns the order tracking Code.
    public void PlaceOrder(List<CartItem> cart, Address paymentAddress, Address shippingAddress) throws Exception {
        //
        if(!shippingAddress.getCountry().equals(module.country) && !shippingAddress.getZipCode().equals(module.zipCode)){
            throw new Exception("Shipping address Country and ZipCode must be the same with your current location.");
        }
        String guid = idGen.getInstance().getUUID();
        String orderID = foodOrderDB.push().getKey();
        //
        FoodOrder order = new FoodOrder(orderID, module.userID, new Date(),guid,paymentAddress.getContactAddress(),
                paymentAddress.getCity(),paymentAddress.getZipCode(),paymentAddress.getState(),paymentAddress.getCountry(),shippingAddress.getContactAddress(),
                shippingAddress.getCity(), shippingAddress.getZipCode(),shippingAddress.getState(),shippingAddress.getCountry(), shippingAddress.getContactPerson(), shippingAddress.getContactPhone());
        //save cart to the system.
        foodOrderDB.child(orderID).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Save Order details here.
                List<FoodOrderDetail> orderDetails = new ArrayList<>();
                //Initialise CartDalc
                CartDalc cartX = new CartDalc();
                for (CartItem c:cart) {
                    String ID = foodOrderDetailDB.push().getKey();
                    //
                    FoodOrderDetail orderDetail = new FoodOrderDetail(ID,orderID,module.userID,c.getFoodID(),c.getResturantID(),c.getFoodPrice(), c.getFoodDesc(),c.getCurrency(),
                            c.getFoodQty(),false,0.0,0.0,"",false, new Date(),"",false,
                            new Date(),"", "",false,false);
                    //
                    foodOrderDetailDB.child(ID).setValue(orderDetail);
                    orderDetails.add(orderDetail);
                    //delete cartItem
                    cartX.DeleteCart(c.getCartID());
                    //
                }
                //raise event
                for (FoodOrderEventHandler listener : foodOrdersAdded.listeners()) {
                    List<FoodOrder> result = new ArrayList<>();
                    result.add(order);
                    listener.invoke(result);
                }
                //raise event
                for (FoodOrderDetailsEventHandler listener : foodOrderDetailsAdded.listeners()) {
                    listener.invoke(orderDetails);
                }
            }
        });

    }

    public void updateFoodOrder(FoodOrder order){
        //save cart to the system.
        foodOrderDB.child(order.orderID).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //raise event
                for (FoodOrderEventHandler listener : foodOrdersUpdated.listeners()) {
                    List<FoodOrder> result = new ArrayList<FoodOrder>();
                    result.add(order);
                    listener.invoke(result);
                }
            }
        });

    }

    public void updateFoodOrderDetails(FoodOrderDetail orderDetail){
        //
        foodOrderDetailDB.child(orderDetail.ID).setValue(orderDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //raise event
                for (FoodOrderDetailsEventHandler listener : foodOrderDetailsUpdated.listeners()) {
                    List<FoodOrderDetail> result = new ArrayList<>();
                    result.add(orderDetail);
                    listener.invoke(result);
                }
            }
        });

    }

    public void cancelFoodOrder(String orderDetailID){
        //
        foodOrderDetailDB.child(orderDetailID).child("isCanceled").setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //raise event
                for (FoodOrderDetailsEventHandler listener : foodOrderDetailsUpdated.listeners()) {
                    List<FoodOrderDetail> result = new ArrayList<>();
                    listener.invoke(result);
                }
            }
        });

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
                        if (snapshot.hasChildren()){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                FoodOrder foodOrder = userSnapshot.getValue(FoodOrder.class);
                                result.add(foodOrder);
                            }
                            //raise event
                            for (FoodOrderEventHandler listener : foodOrdersFetched.listeners()) {
                                listener.invoke(result);
                            }
                        }
                    }
                }else{
                    //raise event
                    for (FoodOrderEventHandler listener : foodOrdersNotFound.listeners()) {
                        List<FoodOrder> result = new ArrayList<FoodOrder>();
                        listener.invoke(result);
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
        Query query = FirebaseDatabase.getInstance().getReference("foodorders")
                .orderByChild("orderID")
                .equalTo(orderID);
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }

    public void getFoodOrderDetailsByUserID(String userID){
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<FoodOrderDetail> result = new ArrayList<>();
                        //
                        if (snapshot.hasChildren()){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                FoodOrderDetail foodOrderDetail = userSnapshot.getValue(FoodOrderDetail.class);
                                result.add(foodOrderDetail);
                            }
                            //sort data
                            Collections.sort(result, Collections.reverseOrder());
                            //raise event
                            for (FoodOrderDetailsEventHandler listener : foodOrderDetailsFetched.listeners()) {
                                listener.invoke(result);
                            }
                        }
                    }
                }else{
                    //raise event
                    for (FoodOrderDetailsEventHandler listener : foodOrderDetailsNotFound.listeners()) {
                        List<FoodOrderDetail> result = new ArrayList<>();
                        listener.invoke(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (FoodOrderDetailsEventHandler listener : foodOrderDetailsNotFound.listeners()) {
                    List<FoodOrderDetail> result = new ArrayList<>();
                    listener.invoke(result);
                }
            }
        };
        //
        Query query = FirebaseDatabase.getInstance().getReference("foodorderdetails")
                .orderByChild("userID")
                .equalTo(userID);
        query.addValueEventListener(onDataChangedListener);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //
                Query query = FirebaseDatabase.getInstance().getReference("foodorderdetails")
                        .orderByChild("userID")
                        .equalTo(userID);
                query.addListenerForSingleValueEvent(onDataChangedListener);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //
    }

    public void getFoodOrderDetailsByID(String ID){
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<FoodOrderDetail> result = new ArrayList<>();
                        //
                        if (snapshot.hasChildren()){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                FoodOrderDetail foodOrderDetail = userSnapshot.getValue(FoodOrderDetail.class);
                                result.add(foodOrderDetail);
                            }
                            //raise event
                            for (FoodOrderDetailsEventHandler listener : foodOrderDetailsFetched.listeners()) {
                                listener.invoke(result);
                            }
                        }
                    }
                }else{
                    //raise event
                    for (FoodOrderDetailsEventHandler listener : foodOrderDetailsNotFound.listeners()) {
                        List<FoodOrderDetail> result = new ArrayList<>();
                        listener.invoke(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (FoodOrderDetailsEventHandler listener : foodOrderDetailsNotFound.listeners()) {
                    List<FoodOrderDetail> result = new ArrayList<>();
                    listener.invoke(result);
                }
            }
        };
        //
        Query query = FirebaseDatabase.getInstance().getReference("foodorderdetails")
                .orderByChild("ID")
                .equalTo(ID);
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }

    public void getFoodOrderDetailsByQueryString(String resturantID, boolean isCanceled,boolean isPrinted, boolean isShipped, boolean isDelivered){
        String queryString = FoodOrderDetail.getQueryString(resturantID, isCanceled,isPrinted, isShipped, isDelivered);
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        List<FoodOrderDetail> result = new ArrayList<>();
                        //
                        if (snapshot.hasChildren()){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                FoodOrderDetail foodOrderDetail = userSnapshot.getValue(FoodOrderDetail.class);
                                result.add(foodOrderDetail);
                            }
                            //raise event
                            for (FoodOrderDetailsEventHandler listener : foodOrderDetailsFetched.listeners()) {
                                listener.invoke(result);
                            }
                        }
                    }
                }else{
                    //raise event
                    for (FoodOrderDetailsEventHandler listener : foodOrderDetailsNotFound.listeners()) {
                        List<FoodOrderDetail> result = new ArrayList<>();
                        listener.invoke(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (FoodOrderDetailsEventHandler listener : foodOrderDetailsNotFound.listeners()) {
                    List<FoodOrderDetail> result = new ArrayList<>();
                    listener.invoke(result);
                }
            }
        };
        //
        Query query = FirebaseDatabase.getInstance().getReference("foodorderdetails")
                .orderByChild("queryString")
                .equalTo(queryString);
        query.addValueEventListener(onDataChangedListener);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //
                Query query = FirebaseDatabase.getInstance().getReference("foodorderdetails")
                        .orderByChild("queryString")
                        .equalTo(queryString);
                query.addListenerForSingleValueEvent(onDataChangedListener);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //
    }

    public void getFoodOrderDetailsByReportQuery(String resturantID, int month, int year, boolean isCanceled,boolean isPrinted, boolean isShipped, boolean isDelivered){
        String reportQuery = FoodOrderDetail.getReportQuery(resturantID,month,year, isCanceled,isPrinted, isShipped, isDelivered);
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                       ReportIndex result = new ReportIndex(0,0);
                        //
                        if (snapshot.hasChildren()){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                FoodOrderDetail foodOrderDetail = userSnapshot.getValue(FoodOrderDetail.class);
                                assert foodOrderDetail != null;
                                result.setTotalSales(result.getTotalSales() + foodOrderDetail.getFoodQty());
                                result.setTotalRevenue(result.getTotalRevenue() + (foodOrderDetail.getFoodQty() * foodOrderDetail.getFoodPrice()));
                            }
                            //raise event
                            for (ReportIndexEventHandler listener : reportIndexFetched.listeners()) {
                                listener.invoke(result);
                            }
                        }
                    }
                }else{
                    //raise event
                    for (ReportIndexEventHandler listener : reportIndexNotFound.listeners()) {
                        ReportIndex result = new ReportIndex(0,0);
                        listener.invoke(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (FoodOrderDetailsEventHandler listener : foodOrderDetailsNotFound.listeners()) {
                    List<FoodOrderDetail> result = new ArrayList<>();
                    listener.invoke(result);
                }
            }
        };
        //
        Query query = FirebaseDatabase.getInstance().getReference("foodorderdetails")
                .orderByChild("reportQuery")
                .equalTo(reportQuery);
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }

    public void getFoodOrderDetailsByReportQueryForAdmin(String resturantID, int month, int year, boolean isCanceled,boolean isPrinted, boolean isShipped, boolean isDelivered){
        String reportQuery = FoodOrderDetail.getReportQuery(resturantID, month, year, isCanceled, isPrinted, isShipped, isDelivered);
        ValueEventListener onDataChangedListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        //
                        ReportIndex result = new ReportIndex(0,0);
                        //
                        if (snapshot.hasChildren()){
                            for(DataSnapshot userSnapshot:snapshot.getChildren()){
                                FoodOrderDetail foodOrderDetail = userSnapshot.getValue(FoodOrderDetail.class);
                                assert foodOrderDetail != null;
                                result.setTotalSales(result.getTotalSales() + foodOrderDetail.getFoodQty());
                                result.setTotalRevenue(result.getTotalRevenue() + foodOrderDetail.getMarkUpValuePaid());
                            }
                            //raise event
                            for (ReportIndexEventHandler listener : reportIndexFetched.listeners()) {
                                listener.invoke(result);
                            }
                        }
                    }
                }else{
                    //raise event
                    for (ReportIndexEventHandler listener : reportIndexNotFound.listeners()) {
                        ReportIndex result = new ReportIndex(0,0);
                        listener.invoke(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //raise event
                for (FoodOrderDetailsEventHandler listener : foodOrderDetailsNotFound.listeners()) {
                    List<FoodOrderDetail> result = new ArrayList<>();
                    listener.invoke(result);
                }
            }
        };
        //
        Query query = FirebaseDatabase.getInstance().getReference("foodorderdetails")
                .orderByChild("reportQuery")
                .equalTo(reportQuery);
        query.addListenerForSingleValueEvent(onDataChangedListener);
        //
    }

}
