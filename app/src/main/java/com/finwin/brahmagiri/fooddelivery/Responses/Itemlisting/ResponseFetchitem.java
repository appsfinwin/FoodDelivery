package com.finwin.brahmagiri.fooddelivery.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseFetchitem {
    @SerializedName("data")
    @Expose
    private ItemData data;

    public ItemData getData() {
        return data;
    }

    public void setData(ItemData data) {
        this.data = data;
    }
}
