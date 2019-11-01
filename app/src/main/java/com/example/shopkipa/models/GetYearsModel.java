package com.example.shopkipa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetYearsModel {
    @SerializedName("year")
    @Expose
    private String year;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
