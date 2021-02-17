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
        String orderID = "";
        FoodOrder order = new FoodOrder(orderID, module.userID,new Date(),guid,paymentAddress.getContactAddress(),
                paymentAddress.getCity(),paymentAddress.getZipCode(),paymentAddress.getState(),paymentAddress.getCountry(),shippingAddress.getContactAddress(),
                shippingAddress.getCity(), shippingAddress.getZipCode(),shippingAddress.getState(),shippingAddress.getCountry());
        //save order and return the Order ID
        //TODO save order to the system here.
        //Save Order here.

        String ID = "";
        //
        for (CartItem c:cart) {
            FoodOrderDetail orderDetail = new FoodOrderDetail(ID,orderID,c.getUserID(),c.getFoodID(),c.getResturantID(),c.getFoodPrice(),c.getCurrency(),
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

    public void CancelOrder(FoodOrderDetail orderDetail){
        FoodOrderDetail f = orderDetail;
            //TODO save f to the
            String query = "UPDATE FoodOrderDetail SET (isCanceled = true) WHERE ((ID = " + f.getID() + ") && (isPaid = false) && (isShipped = false) && (isDelivered = false))";
            //database.Save()
    }

    public void ShipOrder(List<String> orderDetailIDs){
        for (String f:orderDetailIDs) {

            //TODO save f to the
            String query = "UPDATE FoodOrderDetail SET (isShipped = true, dateShipped = " + new Date() + ", shippedBy = " + module.userName +") WHERE (ID = " + f + ")";
        }
    }

    public void DeliverOrder(List<String> orderDetailIDs, double amountCollected, double changeGiven, String paymentDescription, String collectedBy){
        for (String f:orderDetailIDs) {

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

    public FoodOrder getFoodOrderByOderID(String orderID){
        FoodOrder result = null;
        //TODO get order from the system here

        return result;
    }

    public List<FoodOrderDetail> getUndeliveredFoodOrderDetailsByResturant(String resturantID, boolean isDelivered){
        List<FoodOrderDetail> result = new ArrayList<FoodOrderDetail>();
        //TODO get order details from the system here

        return result;
    }

    public List<FoodOrderDetail> getShippedFoodOrderDetailsByResturant(String resturantID, boolean isShipped){
        List<FoodOrderDetail> result = new ArrayList<FoodOrderDetail>();
        //TODO get order details from the system here

        return result;
    }

    public List<FoodOrderDetail> getFoodOrderDetailsByUserID(String userID){
        List<FoodOrderDetail> result = new ArrayList<FoodOrderDetail>();
        //TODO get order details from the system here

        return result;
    }

    public List<FoodOrderDetail> getUnProcessedFoodOrderDetailsByUserID(String userID, boolean canceled, boolean delivered){
        List<FoodOrderDetail> result = new ArrayList<FoodOrderDetail>();
        //TODO get order details from the system here
        String query = "SELECT * FROM FoodOrderDetail WHERE (userID = " + userID + " && isCanceled = " + canceled + " isDelivered = " + delivered + ")";

        return result;
    }


}
