package com.example.shopkipa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSalesModel {


    @SerializedName("totalProfit")
    @Expose
    private int totalProfit;
    @SerializedName("totalExpense")
    @Expose
    private int totalExpense;

    public int getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(int totalProfit) {
        this.totalProfit = totalProfit;
    }

    public int getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(int totalExpense) {
        this.totalExpense = totalExpense;
    }
}
