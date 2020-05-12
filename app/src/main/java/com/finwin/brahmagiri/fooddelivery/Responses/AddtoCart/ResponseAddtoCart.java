package com.finwin.brahmagiri.fooddelivery.Responses.AddtoCart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAddtoCart {
    @SerializedName("data")
    @Expose
    private CartData data;

    public CartData getData() {
        return data;
    }

    public void setData(CartData data) {
        this.data = data;
    }

}
