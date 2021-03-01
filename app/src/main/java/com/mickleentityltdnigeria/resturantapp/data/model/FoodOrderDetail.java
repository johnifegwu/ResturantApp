package com.mickleentityltdnigeria.resturantapp.data.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Date;

@IgnoreExtraProperties
public class FoodOrderDetail implements Serializable {

    public String ID;
    public String oderID;
    public String userID;
    public String foodID;
    public String resturantID;
    public double foodPrice;
    public String currency;
    public double foodQty;
    public boolean isPaid;
    public double amountPaid;
    public double changeGiven;
    public String paymentDescription;
    public boolean isShipped;
    public Date dateShipped;
    public String shippedBy;
    public boolean isDelivered;
    public Date dateDelivered;
    public String deliveredBy;
    public String collectedBy;
    public boolean isCanceled;
    public double markUp = 0.01;
    public double markUpValue = 0.0;
    public boolean isMarkUpPaid = false;
    public double markUpValuePaid = 0.0;
    public Date datePaid = new Date(-10000);
    public String paymentMedium = "";
    public String paymentNote = "";
    public String queryString;

    public FoodOrderDetail() {
    }

    public FoodOrderDetail(String ID, String oderID, String userID, String foodID, String resturantID, double foodPrice, String currency, double foodQty, boolean isPaid, double amountPaid, double changeGiven, String paymentDescription, boolean isShipped, Date dateShipped, String shippedBy, boolean isDelivered, Date dateDelivered, String deliveredBy, String collectedBy, boolean isCanceled) {
        this.ID = ID;
        this.oderID = oderID;
        this.userID = userID;
        this.foodID = foodID;
        this.resturantID = resturantID;
        this.foodPrice = foodPrice;
        this.currency = currency;
        this.foodQty = foodQty;
        this.isPaid = isPaid;
        this.amountPaid = amountPaid;
        this.changeGiven = changeGiven;
        this.paymentDescription = paymentDescription;
        this.isShipped = isShipped;
        this.dateShipped = dateShipped;
        this.shippedBy = shippedBy;
        this.isDelivered = isDelivered;
        this.dateDelivered = dateDelivered;
        this.deliveredBy = deliveredBy;
        this.collectedBy = collectedBy;
        this.isCanceled = isCanceled;
        this.markUpValue = ((foodPrice * foodQty) * this.markUp);
        this.queryString = this.getQueryString(this.resturantID,this.isCanceled,this.isShipped,this.isDelivered);
    }

    @Exclude
    public String getQueryString(String resturantID, boolean isCanceled, boolean isShipped, boolean isDelivered){
        return "resturantID=" + resturantID + "+isCanceled=" + isCanceled + "+isShipped=" + isShipped
                + "isDelivered=" + isDelivered;
    }

    @Exclude
    public double getSubTotal(){
        return ((this.foodPrice * this.foodQty) + ((this.foodPrice * this.foodQty) * this.markUp));
    }

    @Exclude
    public boolean isMarkUpPaid() {
        return isMarkUpPaid;
    }

    @Exclude
    public void setMarkUpPaid(boolean markUpPaid) {
        isMarkUpPaid = markUpPaid;
    }

    @Exclude
    public double getMarkUpValuePaid() {
        return markUpValuePaid;
    }

    @Exclude
    public void setMarkUpValuePaid(double markUpValuePaid) {
        this.markUpValuePaid = markUpValuePaid;
    }

    @Exclude
    public Date getDatePaid() {
        return datePaid;
    }

    @Exclude
    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }

    @Exclude
    public String getPaymentMedium() {
        return paymentMedium;
    }

    @Exclude
    public void setPaymentMedium(String paymentMedium) {
        this.paymentMedium = paymentMedium;
    }

    @Exclude
    public String getPaymentNote() {
        return paymentNote;
    }

    @Exclude
    public void setPaymentNote(String paymentNote) {
        this.paymentNote = paymentNote;
    }

    @Exclude
    public double getMarkUp() {
        return markUp;
    }

    @Exclude
    public void setMarkUp(double markUp) {
        this.markUp = markUp;
    }

    @Exclude
    public double getMarkUpValue() {
        return markUpValue;
    }

    @Exclude
    public void setMarkUpValue(double markUpValue) {
        this.markUpValue = markUpValue;
    }

    @Exclude
    public String getID() {
        return ID;
    }

    @Exclude
    public void setID(String ID) {
        this.ID = ID;
    }

    @Exclude
    public String getOderID() {
        return oderID;
    }

    @Exclude
    public void setOderID(String oderID) {
        this.oderID = oderID;
    }

    @Exclude
    public String getUserID() {
        return userID;
    }

    @Exclude
    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Exclude
    public String getFoodID() {
        return foodID;
    }

    @Exclude
    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

    @Exclude
    public String getResturantID() {
        return resturantID;
    }

    @Exclude
    public void setResturantID(String resturantID) {
        this.resturantID = resturantID;
    }

    @Exclude
    public double getFoodPrice() {
        return foodPrice;
    }

    @Exclude
    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    @Exclude
    public String getCurrency() {
        return currency;
    }

    @Exclude
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Exclude
    public double getFoodQty() {
        return foodQty;
    }

    @Exclude
    public void setFoodQty(double foodQty) {
        this.foodQty = foodQty;
    }

    @Exclude
    public boolean isPaid() {
        return isPaid;
    }

    @Exclude
    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Exclude
    public double getAmountPaid() {
        return amountPaid;
    }

    @Exclude
    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    @Exclude
    public double getChangeGiven() {
        return changeGiven;
    }

    @Exclude
    public void setChangeGiven(double changeGiven) {
        this.changeGiven = changeGiven;
    }

    @Exclude
    public String getPaymentDescription() {
        return paymentDescription;
    }

    @Exclude
    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    @Exclude
    public boolean isShipped() {
        return isShipped;
    }

    @Exclude
    public void setShipped(boolean shipped) {
        this.isShipped = shipped;
        this.queryString = this.getQueryString(this.resturantID,this.isCanceled,this.isShipped,this.isDelivered);
    }

    @Exclude
    public Date getDateShipped() {
        return dateShipped;
    }

    @Exclude
    public void setDateShipped(Date dateShipped) {
        this.dateShipped = dateShipped;
    }

    @Exclude
    public String getShippedBy() {
        return shippedBy;
    }

    @Exclude
    public void setShippedBy(String shippedBy) {
        this.shippedBy = shippedBy;
    }

    @Exclude
    public boolean isDelivered() {
        return isDelivered;
    }

    @Exclude
    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
        this.queryString = this.getQueryString(this.resturantID,this.isCanceled,this.isShipped,this.isDelivered);
    }

    @Exclude
    public Date getDateDelivered() {
        return dateDelivered;
    }

    @Exclude
    public void setDateDelivered(Date dateDelivered) {
        this.dateDelivered = dateDelivered;
    }

    @Exclude
    public String getDeliveredBy() {
        return deliveredBy;
    }

    @Exclude
    public void setDeliveredBy(String deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    @Exclude
    public String getCollectedBy() {
        return collectedBy;
    }

    @Exclude
    public void setCollectedBy(String collectedBy) {
        this.collectedBy = collectedBy;
    }

    @Exclude
    public boolean isCanceled() {
        return isCanceled;
    }

    @Exclude
    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
        this.queryString = this.getQueryString(this.resturantID,this.isCanceled,this.isShipped,this.isDelivered);
    }
}
