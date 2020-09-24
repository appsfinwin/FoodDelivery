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
    @SerializedName("Tax")
    @Expose
    private List<Tax> tax = null;
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

    @SerializedName("delivery_charge")
    @Expose
    private Double delivery_charge;

    @SerializedName("outlet_mobile")
    @Expose
    private String outlet_mobile;

    @SerializedName("outlet_name")
    @Expose
    private String outlet_name;

    @SerializedName("outlet_addr")
    @Expose
    private String outlet_addr;

    @SerializedName("date_time")
    @Expose
    private String date_time;

    @SerializedName("consumer_delivery_loc")
    @Expose
    private String consumer_delivery_loc;

    @SerializedName("remaining_time")
    @Expose
    private String remaining_time;





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

    public List<Tax> getTax() {
        return tax;
    }

    public void setTax(List<Tax> tax) {
        this.tax = tax;
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

    public Double getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(Double delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public String getOutlet_mobile() {
        return outlet_mobile;
    }

    public void setOutlet_mobile(String outlet_mobile) {
        this.outlet_mobile = outlet_mobile;
    }

    public String getOutlet_name() {
        return outlet_name;
    }

    public void setOutlet_name(String outlet_name) {
        this.outlet_name = outlet_name;
    }

    public String getOutlet_addr() {
        return outlet_addr;
    }

    public void setOutlet_addr(String outlet_addr) {
        this.outlet_addr = outlet_addr;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getConsumer_delivery_loc() {
        return consumer_delivery_loc;
    }

    public void setConsumer_delivery_loc(String consumer_delivery_loc) {
        this.consumer_delivery_loc = consumer_delivery_loc;
    }

    public String getRemaining_time() {
        return remaining_time;
    }

    public void setRemaining_time(String remaining_time) {
        this.remaining_time = remaining_time;
    }
}
