package com.finwin.brahmagiri.fooddelivery.Responses.FetchCart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseFetchCart {

    @SerializedName("data")
    @Expose
    private FetchCartData data;

    public FetchCartData getData() {
        return data;
    }

    public void setData(FetchCartData data) {
        this.data = data;
    }

}
