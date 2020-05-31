package com.finwin.brahmagiri.fooddelivery.Responses;

import com.finwin.brahmagiri.fooddelivery.Activity.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseFetchProducts {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("id")
    @Expose
    private String id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
