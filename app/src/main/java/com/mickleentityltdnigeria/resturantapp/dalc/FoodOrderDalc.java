package com.mickleentityltdnigeria.resturantapp.dalc;

import com.mickleentityltdnigeria.resturantapp.data.model.Address;
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrder;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FoodOrderDalc {

    public FoodOrderDalc() {
    }

    //Adds new order to the system and returns the order tracking Code.
    public String PlaceOrder(List<CartItem> cart, Address paymentAddress, Address shippingAddress){
        //
        String guid = UUID.randomUUID().toString();
        FoodOrder order = new FoodOrder(-1, module.userID,new Date(),guid,paymentAddress.getContactAddress(),
                paymentAddress.getCity(),paymentAddress.getZipCode(),paymentAddress.getState(),paymentAddress.getCountry(),shippingAddress.getContactAddress(),
                shippingAddress.getCity(), shippingAddress.getZipCode(),shippingAddress.getState(),shippingAddress.getCountry());
        //save order and return the Order ID
        //TODO save order to the system here.
        //Save Order here.

        int orderID = getFoodOrderByTrackCode(guid).getOrderID();
        //
        for (CartItem c:cart) {
            FoodOrderDetail orderDetail = new FoodOrderDetail(-1,orderID,c.getUserID(),c.getFoodID(),c.getResturantID(),c.getFoodPrice(),c.getCurrency(),
                    c.getCartQty(),false,0.0,0.0,"",false,new Date(),"",false,
                    new Date(),"", "",false);
            //
            addOrderDetails(orderDetail);
            //
        }
        return guid;
    }

    private void addOrderDetails(FoodOrderDetail orderDetail){
        //TODO save order detail to the system here.

    }

    public void CancelOrder(List<Integer> orderDetailIDs){
        for (Integer f:orderDetailIDs) {

            //TODO save f to the
            String query = "UPDATE FoodOrderDetail SET (isCanceled = true) WHERE ((ID = " + f + ") && (isPaid = false) && (isShipped = false) && (isDelivered = false))";
            //database.Save()
        }
    }

    public void ShipOrder(List<Integer> orderDetailIDs){
        for (Integer f:orderDetailIDs) {

            //TODO save f to the
            String query = "UPDATE FoodOrderDetail SET (isShipped = true, dateShipped = " + new Date() + ", shippedBy = " + module.userName +") WHERE (ID = " + f + ")";
        }
    }

    public void DeliverOrder(List<Integer> orderDetailIDs, double amountCollected, double changeGiven, String paymentDescription, String collectedBy){
        for (Integer f:orderDetailIDs) {

            //TODO save f to the
            String query = "UPDATE FoodOrderDetail SET (isDelivered = true, dateDelivered = " + new Date() + ", deliveredBy = " + module.userName +"," +
                    "amountPaid = "+ amountCollected +", changeGiven = "+ changeGiven +", paymentDescription = "+ paymentDescription + ", collectedBy = "+ collectedBy + ") WHERE (ID = " + f + ")";
            //database.Save(f);
        }
    }

    public FoodOrder getFoodOrderByTrackCode(String trackCode){
        FoodOrder result = null;
        //TODO get order from the system here

        return result;
    }

    public FoodOrder getFoodOrderByOderID(int orderID){
        FoodOrder result = null;
        //TODO get order from the system here

        return result;
    }

    public List<FoodOrderDetail> getUndeliveredFoodOrderDetailsByResturant(int resturantID, boolean isDelivered){
        List<FoodOrderDetail> result = new ArrayList<FoodOrderDetail>();
        //TODO get order details from the system here

        return result;
    }

    public List<FoodOrderDetail> getShippedFoodOrderDetailsByResturant(int resturantID, boolean isShipped){
        List<FoodOrderDetail> result = new ArrayList<FoodOrderDetail>();
        //TODO get order details from the system here

        return result;
    }

    public List<FoodOrderDetail> getFoodOrderDetailsByUserID(int userID){
        List<FoodOrderDetail> result = new ArrayList<FoodOrderDetail>();
        //TODO get order details from the system here

        return result;
    }


}
