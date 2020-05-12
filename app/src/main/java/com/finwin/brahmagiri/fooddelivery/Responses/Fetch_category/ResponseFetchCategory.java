package com.finwin.brahmagiri.fooddelivery.Responses.Fetch_category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseFetchCategory {
    @SerializedName("data")
    @Expose
    private Cat_data data;

    public Cat_data getData() {
        return data;
    }

    public void setData(Cat_data data) {
        this.data = data;
    }
}
