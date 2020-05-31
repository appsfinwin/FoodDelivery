package com.finwin.brahmagiri.fooddelivery.Activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("avl_qty")
    @Expose
    private Double avlQty;
    @SerializedName("out_id")
    @Expose
    private String outId;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product_name")
    @Expose
    private String productName;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAvlQty() {
        return avlQty;
    }

    public void setAvlQty(Double avlQty) {
        this.avlQty = avlQty;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
