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
    @SerializedName("no_of_orders")
    @Expose
    private Integer noOfOrders;
    @SerializedName("total_page")
    @Expose
    private Integer totalPage;

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

    public Integer getNoOfOrders() {
        return noOfOrders;
    }

    public void setNoOfOrders(Integer noOfOrders) {
        this.noOfOrders = noOfOrders;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
