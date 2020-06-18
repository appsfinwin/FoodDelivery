package com.finwin.brahmagiri.fooddelivery.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class PreviousSale {
     @SerializedName("status")
     @Expose
     private String status;
     @SerializedName("tax_amount")
     @Expose
     private Double taxAmount;
     @SerializedName("Outlet")
     @Expose
     private Integer outlet;
     @SerializedName("pre_tax_amount")
     @Expose
     private Double preTaxAmount;
     @SerializedName("Outlet_name")
     @Expose
     private String outletName;
     @SerializedName("invoice_no")
     @Expose
     private String invoiceNo;
     @SerializedName("invoice_date")
     @Expose
     private String invoiceDate;
     @SerializedName("total_amount")
     @Expose
     private Double totalAmount;

     public String getStatus() {
         return status;
     }

     public void setStatus(String status) {
         this.status = status;
     }

     public Double getTaxAmount() {
         return taxAmount;
     }

     public void setTaxAmount(Double taxAmount) {
         this.taxAmount = taxAmount;
     }

     public Integer getOutlet() {
         return outlet;
     }

     public void setOutlet(Integer outlet) {
         this.outlet = outlet;
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

     public String getInvoiceDate() {
         return invoiceDate;
     }

     public void setInvoiceDate(String invoiceDate) {
         this.invoiceDate = invoiceDate;
     }

     public Double getTotalAmount() {
         return totalAmount;
     }

     public void setTotalAmount(Double totalAmount) {
         this.totalAmount = totalAmount;
     }
}
