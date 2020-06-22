package com.finwin.brahmagiri.fooddelivery.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseInvoiceGen {

    @SerializedName("tax_amount")
    @Expose
    private Double taxAmount;
    @SerializedName("total_amount")
    @Expose
    private Double totalAmount;
    @SerializedName("invoice_id")
    @Expose
    private Integer invoiceId;
    @SerializedName("products")
    @Expose
    private List<Products> products = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("invoice_no")
    @Expose
    private String invoiceNo;
    @SerializedName("subtotal")
    @Expose
    private Double subtotal;

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}
