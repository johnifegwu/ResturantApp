package com.mickleentityltdnigeria.resturantapp.extensions;

import java.io.Serializable;

public class CartItem implements Serializable {
    private int itemID;
    private Double itemPrice;
    private String itemDesc;
    private String itemImgUrl;
    private int itemQty;

    public CartItem(){

    }

    public CartItem(int itemId, Double itemPrice, String itemDesc, int itemQty, String itemImgUrl){
        this.itemID = itemId;
        this.itemDesc = itemDesc;
        this.itemPrice = itemPrice;
        this.itemQty = itemQty;
        this.itemImgUrl = itemImgUrl;
    }

    public void setItemImgUrl(String itemImgUrl){
        this.itemImgUrl = itemImgUrl;
    }

    public String getItemImgUrl(){
        return this.itemImgUrl;
    }

    public void setItemID(int itemId){
        this.itemID  = itemId;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }
    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public int getItemQty() {
        return itemQty;
    }
}
