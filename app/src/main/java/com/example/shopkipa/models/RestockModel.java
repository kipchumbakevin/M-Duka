package com.example.shopkipa.models;

public class RestockModel {
    String quantity,buyingp,item_id;
    public RestockModel() {
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBuyingp() {
        return buyingp;
    }

    public void setBuyingp(String buyingp) {
        this.buyingp = buyingp;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }
}
