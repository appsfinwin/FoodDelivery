package com.finwin.brahmagiri.fooddelivery.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartItem {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("outlet")
    @Expose
    private Integer outlet;
    @SerializedName("making_time")
    @Expose
    private String makingTime;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getOutlet() {
        return outlet;
    }

    public void setOutlet(Integer outlet) {
        this.outlet = outlet;
    }

    public String getMakingTime() {
        return makingTime;
    }

    public void setMakingTime(String makingTime) {
        this.makingTime = makingTime;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}

