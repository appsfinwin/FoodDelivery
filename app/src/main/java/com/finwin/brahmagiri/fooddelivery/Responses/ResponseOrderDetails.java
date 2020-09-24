package com.finwin.brahmagiri.fooddelivery.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseOrderDetails {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("line_items")
    @Expose
    private List<LineItem> lineItems = null;
    @SerializedName("tax_amount")
    @Expose
    private Double taxAmount;
    @SerializedName("total_amount")
    @Expose
    private Double totalAmount;
    @SerializedName("Invoice_Number")
    @Expose
    private String invoiceNumber;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("subtotal")
    @Expose
    private Double subtotal;

    @SerializedName("payment_mode")
    @Expose
    private String payment_mode;
    @SerializedName("bill_status")
    @Expose
    private String bill_status;
    @SerializedName("collecting_option")
    @Expose
    private String  collecting_option;
    @SerializedName("delivery_charges")
    @Expose
    private Double   delivery_charges;
    @SerializedName("Outlet_name")
    @Expose
    private String outletName;

    @SerializedName("Outlet_mobile")
    @Expose
    private String outletMobile;
    @SerializedName("consumer_name")
    @Expose
    private String consumer_name;
    @SerializedName("date_time")
    @Expose
    private String date_time;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
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


    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getBill_status() {
        return bill_status;
    }

    public void setBill_status(String bill_status) {
        this.bill_status = bill_status;
    }

    public String getCollecting_option() {
        return collecting_option;
    }

    public void setCollecting_option(String collecting_option) {
        this.collecting_option = collecting_option;
    }

    public Double getDelivery_charges() {
        return delivery_charges;
    }

    public void setDelivery_charges(Double delivery_charges) {
        this.delivery_charges = delivery_charges;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getOutletMobile() {
        return outletMobile;
    }

    public void setOutletMobile(String outletMobile) {
        this.outletMobile = outletMobile;
    }

    public String getConsumer_name() {
        return consumer_name;
    }

    public void setConsumer_name(String consumer_name) {
        this.consumer_name = consumer_name;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
