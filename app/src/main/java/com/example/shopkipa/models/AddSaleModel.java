package com.example.shopkipa.models;

public class AddSaleModel {
    String quantitysold,costprice,purchaseId;

    public AddSaleModel() {
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchase_id(String purchase_id) {
        this.purchaseId = purchase_id;
    }

    public String getQuantitysold() {
        return quantitysold;
    }

    public void setQuantitysold(String quantitysold) {
        this.quantitysold = quantitysold;
    }

    public String getCostprice() {
        return costprice;
    }

    public void setCostprice(String costprice) {
        this.costprice = costprice;
    }
}
