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
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("making_time")
    @Expose
    private String makingTime;
    @SerializedName("outlet")
    @Expose
    private Integer outlet;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMakingTime() {
        return makingTime;
    }

    public void setMakingTime(String makingTime) {
        this.makingTime = makingTime;
    }

    public Integer getOutlet() {
        return outlet;
    }

    public void setOutlet(Integer outlet) {
        this.outlet = outlet;
    }
}

