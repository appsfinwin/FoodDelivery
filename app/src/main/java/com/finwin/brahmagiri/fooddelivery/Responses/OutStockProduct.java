package com.finwin.brahmagiri.fooddelivery.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OutStockProduct {
    @SerializedName("avl_qty")
    @Expose
    private Double avlQty;
    @SerializedName("outlet_id")
    @Expose
    private Integer outletId;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product_name")
    @Expose
    private String productName;

    public Double getAvlQty() {
        return avlQty;
    }

    public void setAvlQty(Double avlQty) {
        this.avlQty = avlQty;
    }

    public Integer getOutletId() {
        return outletId;
    }

    public void setOutletId(Integer outletId) {
        this.outletId = outletId;
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
