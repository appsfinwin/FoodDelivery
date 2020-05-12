package com.finwin.brahmagiri.fooddelivery.Responses.Itemlisting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
