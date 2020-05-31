package com.finwin.brahmagiri.fooddelivery.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseInvoiceGen {
    @SerializedName("invoice_id")
    @Expose
    private Integer invoiceId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("subtotal")
    @Expose
    private Double subtotal;
    @SerializedName("tax_amount")
    @Expose
    private Double taxAmount;
    @SerializedName("total_amount")
    @Expose
    private Double totalAmount;

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
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
}
