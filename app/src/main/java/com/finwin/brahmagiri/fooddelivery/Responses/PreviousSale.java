package com.finwin.brahmagiri.fooddelivery.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class PreviousSale {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("delivery_boy_mobile")
    @Expose
    private Object deliveryBoyMobile;
    @SerializedName("invoice_date")
    @Expose
    private String invoiceDate;
    @SerializedName("Outlet_mobile")
    @Expose
    private String outletMobile;
    @SerializedName("pre_tax_amount")
    @Expose
    private Double preTaxAmount;
    @SerializedName("Outlet_name")
    @Expose
    private String outletName;
    @SerializedName("invoice_no")
    @Expose
    private String invoiceNo;
    @SerializedName("delivery_boy_name")
    @Expose
    private Object deliveryBoyName;
    @SerializedName("delivery_charges")
    @Expose
    private String deliveryCharges;
    @SerializedName("tax_amount")
    @Expose
    private Double taxAmount;
    @SerializedName("total_amount")
    @Expose
    private Double totalAmount;
    @SerializedName("invoice_id")
    @Expose
    private Integer invoiceId;
    @SerializedName("collecting_option")
    @Expose
    private String collectingOption;
    @SerializedName("Outlet")
    @Expose
    private Integer outlet;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getDeliveryBoyMobile() {
        return deliveryBoyMobile;
    }

    public void setDeliveryBoyMobile(Object deliveryBoyMobile) {
        this.deliveryBoyMobile = deliveryBoyMobile;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getOutletMobile() {
        return outletMobile;
    }

    public void setOutletMobile(String outletMobile) {
        this.outletMobile = outletMobile;
    }

    public Double getPreTaxAmount() {
        return preTaxAmount;
    }

    public void setPreTaxAmount(Double preTaxAmount) {
        this.preTaxAmount = preTaxAmount;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Object getDeliveryBoyName() {
        return deliveryBoyName;
    }

    public void setDeliveryBoyName(Object deliveryBoyName) {
        this.deliveryBoyName = deliveryBoyName;
    }

    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
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

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getCollectingOption() {
        return collectingOption;
    }

    public void setCollectingOption(String collectingOption) {
        this.collectingOption = collectingOption;
    }

    public Integer getOutlet() {
        return outlet;
    }

    public void setOutlet(Integer outlet) {
        this.outlet = outlet;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
