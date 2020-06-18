package com.finwin.brahmagiri.fooddelivery.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMyOrder {

    @SerializedName("Previous_Sales")
    @Expose
    private List<PreviousSale> previousSales = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<PreviousSale> getPreviousSales() {
        return previousSales;
    }

    public void setPreviousSales(List<PreviousSale> previousSales) {
        this.previousSales = previousSales;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
