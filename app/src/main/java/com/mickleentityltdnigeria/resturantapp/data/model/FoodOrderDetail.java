package com.mickleentityltdnigeria.resturantapp.data.model;

import androidx.annotation.Nullable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.mickleentityltdnigeria.resturantapp.utils.DateHelper;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@IgnoreExtraProperties
public class FoodOrderDetail implements Serializable, Comparable<FoodOrderDetail> {

    public String ID;
    public String oderID;
    public String userID;
    public String foodID;
    public String resturantID;
    public double foodPrice;
    public String foodDesc;
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
    public boolean isPrinted;
    public Date orderDate;
    public String reportQuery;

    @Exclude
    private String foodImg = null;

    @Exclude
    private FoodOrder foodOrder = null;

    public FoodOrderDetail() {
        this.orderDate = new Timestamp(new Date().getTime());
    }

    public FoodOrderDetail(String ID, String oderID, String userID, String foodID, String resturantID, double foodPrice, String foodDesc, String currency, double foodQty, boolean isPaid, double amountPaid, double changeGiven, String paymentDescription, boolean isShipped, Date dateShipped, String shippedBy, boolean isDelivered, Date dateDelivered, String deliveredBy, String collectedBy, boolean isCanceled, boolean isPrinted) {
        this.ID = ID;
        this.oderID = oderID;
        this.userID = userID;
        this.foodID = foodID;
        this.resturantID = resturantID;
        this.foodPrice = foodPrice;
        this.foodDesc = foodDesc;
        this.currency = currency;
        this.foodQty = foodQty;
        this.isPaid = isPaid;
        this.amountPaid = amountPaid;
        this.changeGiven = changeGiven;
        this.paymentDescription = paymentDescription;
        this.isShipped = isShipped;
        this.dateShipped = dateShipped ;
        this.shippedBy = shippedBy;
        this.isDelivered = isDelivered;
        this.dateDelivered = dateDelivered;
        this.deliveredBy = deliveredBy;
        this.collectedBy = collectedBy;
        this.isCanceled = isCanceled;
        this.markUpValue = ((foodPrice * foodQty) * this.markUp);
        this.queryString = getQueryString(this.resturantID,this.isCanceled, this.isPrinted,this.isShipped,this.isDelivered);
        this.orderDate = new Date();
        DateHelper dt = new DateHelper(this.orderDate);
        this.reportQuery = getReportQuery(this.resturantID, dt.getMonth(),dt.getYear(),this.isCanceled,this.isPrinted,this.isShipped,this.isDelivered);
    }

    @Exclude
    public FoodOrder getFoodOrder() {
        return foodOrder;
    }

    @Exclude
    public void setFoodOrder(FoodOrder foodOrder) {
        this.foodOrder = foodOrder;
    }

    @Exclude
    public String getFoodImg() {
        return foodImg;
    }

    @Exclude
    public void setFoodImg(String foodImg) {
        this.foodImg = foodImg;
    }

    @Exclude
    public static String getQueryString(String resturantID, boolean isCanceled, boolean isPrinted, boolean isShipped, boolean isDelivered){
        return "resturantID=" + resturantID + "+isCanceled=" + isCanceled + "+isPrinted=" + isPrinted +"+isShipped=" + isShipped
                + "isDelivered=" + isDelivered;
    }

    @Exclude
    public static String getReportQuery(String resturantID, int month, int year,  boolean isCanceled, boolean isPrinted, boolean isShipped, boolean isDelivered){
        String result = resturantID + String.valueOf(month) + String.valueOf(year) + String.valueOf(isCanceled)+String.valueOf(isDelivered)+String.valueOf(isPrinted)+String.valueOf(isShipped);
        return result;
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
        this.datePaid = new Timestamp(datePaid.getTime()) ;
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
    public String getFoodDesc() {
        return foodDesc;
    }

    @Exclude
    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
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
        this.queryString = getQueryString(this.resturantID,this.isCanceled,this.isPrinted,this.isShipped,this.isDelivered);
        this.reportQuery = getReportQuery(this.resturantID,this.orderDate.getMonth(),this.orderDate.getYear(),this.isCanceled,this.isPrinted,this.isShipped,this.isDelivered);
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
        DateHelper dt = new DateHelper(this.orderDate);
        this.queryString = getQueryString(this.resturantID,this.isCanceled,this.isPrinted,this.isShipped,this.isDelivered);
        this.reportQuery = getReportQuery(this.resturantID,dt.getMonth(),dt.getYear(),this.isCanceled,this.isPrinted,this.isShipped,this.isDelivered);
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
        this.isCanceled = canceled;
        DateHelper dt = new DateHelper(this.orderDate);
        this.queryString = getQueryString(this.resturantID,this.isCanceled,this.isPrinted,this.isShipped,this.isDelivered);
        this.reportQuery = getReportQuery(this.resturantID,dt.getMonth(),dt.getYear(),this.isCanceled,this.isPrinted,this.isShipped,this.isDelivered);
    }

    @Exclude
    public String getQueryString() {
        return queryString;
    }

    @Exclude
    public String getReportQuery() {
        return reportQuery;
    }

    @Exclude
    public boolean isPrinted() {
        return isPrinted;
    }

    @Exclude
    public void setPrinted(boolean printed) {
        isPrinted = printed;
        DateHelper dt = new DateHelper(this.orderDate);
        this.queryString = getQueryString(this.resturantID,this.isCanceled,this.isPrinted,this.isShipped,this.isDelivered);
        this.reportQuery = getReportQuery(this.resturantID,dt.getMonth(),dt.getYear(),this.isCanceled,this.isPrinted,this.isShipped,this.isDelivered);
    }

    @Exclude
    public Date getOrderDate() {
        return orderDate;
    }

    @Exclude
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Exclude
    @Override
    public String toString() {
        return "FoodOrderDetail{" +
                "dateDelivered=" + dateDelivered +
                '}';
    }

    @Exclude
    @Override
    public int compareTo(FoodOrderDetail o){
        return this.getDateDelivered().compareTo(o.getDateDelivered());
    }

    @Exclude
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodOrderDetail that = (FoodOrderDetail) o;
        return Objects.equals(getID(), that.getID());
    }

    @Exclude
    @Override
    public int hashCode() {
        return Objects.hash(getID());
    }
}
