package com.mickleentityltdnigeria.resturantapp.service;

import com.mickleentityltdnigeria.resturantapp.data.model.Address;
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrder;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserException;
import com.mickleentityltdnigeria.resturantapp.logic.FoodOrderLogic;

import java.util.List;

public class FoodOrderService {

    public FoodOrderLogic Food = new FoodOrderLogic();

    public FoodOrderService() {
    }

    //Adds new order to the system and returns the order tracking Code.
    public String PlaceOrder(List<CartItem> cart, Address paymentAddress, Address shippingAddress) throws InvalidUserException {
        return Food.PlaceOrder(cart, paymentAddress, shippingAddress);
    }

    public void CancelOrder(FoodOrderDetail orderDetail) throws InvalidUserException {
        Food.CancelOrder(orderDetail);
    }

    public void ShipOrder(List<Integer> orderDetailIDs) throws InvalidUserException {
        Food.ShipOrder(orderDetailIDs);
    }

    public void DeliverFood(List<Integer> orderDetailIDs, double amountCollected, double changeGiven, String paymentDescription, String collectedBy) throws InvalidUserException {
        Food.DeliverOrder(orderDetailIDs, amountCollected, changeGiven, paymentDescription, collectedBy);
    }

    public FoodOrder getFoodOrderByTrackCode(String trackCode) throws InvalidUserException {
        return Food.getFoodOrderByTrackCode(trackCode);
    }

    public FoodOrder getFoodOrderByOderID(int orderID) throws InvalidUserException {
        return Food.getFoodOrderByOderID(orderID);
    }

    public List<FoodOrderDetail> getUndeliveredFoodOrderDetailsByResturant(int resturantID, boolean isDelivered) throws InvalidUserException {
        return Food.getUndeliveredFoodOrderDetailsByResturant(resturantID, isDelivered);
    }

    public List<FoodOrderDetail> getShippedFoodOrderDetailsByResturant(int resturantID, boolean isShipped) throws InvalidUserException {
        return Food.getShippedFoodOrderDetailsByResturant(resturantID, isShipped);
    }

    public List<FoodOrderDetail> getFoodOrderDetailsByUserID(int userID) throws InvalidUserException {
        return Food.getFoodOrderDetailsByUserID(userID);
    }

    public List<FoodOrderDetail> getUnProcessedFoodOrderDetailsByUserID(int userID, boolean canceled, boolean delivered) throws InvalidUserException {
        return Food.getUnProcessedFoodOrderDetailsByUserID(userID,canceled,delivered);
    }

}
