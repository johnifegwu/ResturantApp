package com.mickleentityltdnigeria.resturantapp.data.model;

import java.io.Serializable;
import java.util.Date;

public class FoodOrderDetail implements Serializable {

    private int ID;
    private int oderID;
    private int foodID;
    private int resturantID;
    private double foodPrice;
    private String currency;
    private double foodQty;
    private boolean isPaid;
    private boolean isShipped;
    private Date dateShipped;
    private String shippedBy;
    private boolean isDelivered;
    private Date dateDelivered;
    private String deliveredBy;

    public FoodOrderDetail(int ID, int oderID, int foodID, int resturantID, double foodPrice, String currency, double foodQty, boolean isPaid, boolean isShipped, Date dateShipped, String shippedBy, boolean isDelivered, Date dateDelivered, String deliveredBy) {
        this.ID = ID;
        this.oderID = oderID;
        this.foodID = foodID;
        this.resturantID = resturantID;
        this.foodPrice = foodPrice;
        this.currency = currency;
        this.foodQty = foodQty;
        this.isPaid = isPaid;
        this.isShipped = isShipped;
        this.dateShipped = dateShipped;
        this.shippedBy = shippedBy;
        this.isDelivered = isDelivered;
        this.dateDelivered = dateDelivered;
        this.deliveredBy = deliveredBy;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getOderID() {
        return oderID;
    }

    public void setOderID(int oderID) {
        this.oderID = oderID;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public int getResturantID() {
        return resturantID;
    }

    public void setResturantID(int resturantID) {
        this.resturantID = resturantID;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getFoodQty() {
        return foodQty;
    }

    public void setFoodQty(double foodQty) {
        this.foodQty = foodQty;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isShipped() {
        return isShipped;
    }

    public void setShipped(boolean shipped) {
        isShipped = shipped;
    }

    public Date getDateShipped() {
        return dateShipped;
    }

    public void setDateShipped(Date dateShipped) {
        this.dateShipped = dateShipped;
    }

    public String getShippedBy() {
        return shippedBy;
    }

    public void setShippedBy(String shippedBy) {
        this.shippedBy = shippedBy;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public Date getDateDelivered() {
        return dateDelivered;
    }

    public void setDateDelivered(Date dateDelivered) {
        this.dateDelivered = dateDelivered;
    }

    public String getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(String deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

}
