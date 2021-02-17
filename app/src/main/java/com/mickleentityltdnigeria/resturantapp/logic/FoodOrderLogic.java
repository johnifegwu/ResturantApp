package com.mickleentityltdnigeria.resturantapp.logic;

import com.mickleentityltdnigeria.resturantapp.dalc.Dalc;
import com.mickleentityltdnigeria.resturantapp.data.model.Address;
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrder;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail;
import com.mickleentityltdnigeria.resturantapp.exceptions.InvalidUserException;
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.extensions.OrderDetailChangedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.List;

public class FoodOrderLogic {

    public Event<OrderDetailChangedHandler> orderDetailChanged = new Event<OrderDetailChangedHandler>();

    public FoodOrderLogic() {
    }

    //Adds new order to the system and returns the order tracking Code.
    public String PlaceOrder(List<CartItem> cart, Address paymentAddress, Address shippingAddress) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        return Dalc.Order().PlaceOrder(cart,paymentAddress,shippingAddress);
        //
    }

    public void CancelOrder(FoodOrderDetail orderDetail) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        Dalc.Order().CancelOrder(orderDetail);
        //
        for (OrderDetailChangedHandler listener : orderDetailChanged.listeners()) {
            listener.invoke(orderDetail.getID());
        }
    }

    public void ShipOrder(List<String> orderDetailIDs) throws InvalidUserException {
        if((!module.userType.equals(module.UserTypeSELLER)) || (module.isLoggedIn = false)){
            throw new InvalidUserException();
        }
        Dalc.Order().ShipOrder(orderDetailIDs);
    }

    public void DeliverOrder(List<String> orderDetailIDs, double amountCollected, double changeGiven, String paymentDescription, String collectedBy) throws InvalidUserException {
        if((!module.userType.equals(module.UserTypeSELLER)) || (module.isLoggedIn = false)){
            throw new InvalidUserException();
        }
        Dalc.Order().DeliverOrder(orderDetailIDs, amountCollected, changeGiven, paymentDescription, collectedBy);
    }

    public FoodOrder getFoodOrderByTrackCode(String trackCode) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        return Dalc.Order().getFoodOrderByTrackCode(trackCode);
    }

    public FoodOrder getFoodOrderByOderID(String orderID) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        return Dalc.Order().getFoodOrderByOderID(orderID);
    }

    public List<FoodOrderDetail> getUndeliveredFoodOrderDetailsByResturant(String resturantID, boolean isDelivered) throws InvalidUserException {
        if((!module.userType.equals(module.UserTypeSELLER)) || (module.isLoggedIn = false)){
            throw new InvalidUserException();
        }
        return Dalc.Order().getUndeliveredFoodOrderDetailsByResturant(resturantID, isDelivered);
    }

    public List<FoodOrderDetail> getShippedFoodOrderDetailsByResturant(String resturantID, boolean isShipped) throws InvalidUserException {
        if((!module.userType.equals(module.UserTypeSELLER)) || (module.isLoggedIn = false)){
            throw new InvalidUserException();
        }
        return Dalc.Order().getShippedFoodOrderDetailsByResturant(resturantID, isShipped);
    }

    public List<FoodOrderDetail> getFoodOrderDetailsByUserID(String userID) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        return Dalc.Order().getFoodOrderDetailsByUserID(userID);
    }

    public List<FoodOrderDetail> getUnProcessedFoodOrderDetailsByUserID(String userID, boolean canceled, boolean delivered) throws InvalidUserException {
        if(module.isLoggedIn = false){
            throw new InvalidUserException();
        }
        return Dalc.Order().getUnProcessedFoodOrderDetailsByUserID(userID,canceled,delivered);
    }

}
