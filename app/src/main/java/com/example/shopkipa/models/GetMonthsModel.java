package com.example.shopkipa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMonthsModel {
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("year")
    @Expose
    private String year;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
