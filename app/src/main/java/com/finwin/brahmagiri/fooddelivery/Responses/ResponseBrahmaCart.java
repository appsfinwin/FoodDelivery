package com.finwin.brahmagiri.fooddelivery.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBrahmaCart {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("delivery_charge")
    @Expose
    private Double deliveryCharge;
    @SerializedName("Cart_items")
    @Expose
    private List<CartItem> cartItems = null;
    @SerializedName("total_amnt")
    @Expose
    private Double totalAmnt;

    @SerializedName("distance")
    @Expose
    private Double distance;







    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(Double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Double getTotalAmnt() {
        return totalAmnt;
    }

    public void setTotalAmnt(Double totalAmnt) {
        this.totalAmnt = totalAmnt;
    }


    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
