package com.mickleentityltdnigeria.resturantapp.dalc;

import com.mickleentityltdnigeria.resturantapp.data.model.Address;
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrder;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FoodOrderDalc {

    public FoodOrderDalc() {
    }

    public void PlaceOrder(List<CartItem> cart, Address paymentAddress, Address shippingAddress){
        //TODO do the updates here
        String guid = UUID.randomUUID().toString();
        FoodOrder order = new FoodOrder(-1, module.userID,new Date(),guid,paymentAddress.getContactAddress(),
                paymentAddress.getCity(),paymentAddress.getZipCode(),paymentAddress.getState(),paymentAddress.getCountry(),shippingAddress.getContactAddress(),
                shippingAddress.getCity(), shippingAddress.getZipCode(),shippingAddress.getState(),shippingAddress.getCountry());
        //save order and return the Order ID

        int orderID = getFoodOrderByTrackCode(guid).getOrderID();
        
    }

    private void addOrderDetails(FoodOrderDetail orderDetail){

    }

    public FoodOrder getFoodOrderByTrackCode(String trackCode){
        FoodOrder f = null;

        return f;
    }

}
