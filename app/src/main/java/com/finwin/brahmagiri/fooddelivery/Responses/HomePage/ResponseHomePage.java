package com.finwin.brahmagiri.fooddelivery.Responses.HomePage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseHomePage {
    @SerializedName("data")
    @Expose
    private HomeData data;

    public HomeData getData() {
        return data;
    }

    public void setData(HomeData data) {
        this.data = data;
    }
}
