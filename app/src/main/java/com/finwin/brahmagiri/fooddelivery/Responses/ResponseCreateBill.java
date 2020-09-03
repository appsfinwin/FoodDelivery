package com.finwin.brahmagiri.fooddelivery.Responses;

import com.finwin.brahmagiri.fooddelivery.Activity.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCreateBill {
    @SerializedName("tax_amount")
    @Expose
    private Double taxAmount;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("subtotal")
    @Expose
    private Double subtotal;
    @SerializedName("total_amount")
    @Expose
    private Double totalAmount;
    @SerializedName("bill_id")
    @Expose
    private Integer billId;
    @SerializedName("products")
    @Expose
    private List<OutStockProduct> products = null;
    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public List<OutStockProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OutStockProduct> products) {
        this.products = products;
    }
}
