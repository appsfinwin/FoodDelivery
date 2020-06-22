package com.finwin.brahmagiri.fooddelivery.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Products {
     @SerializedName("price")
     @Expose
     private Double price;
     @SerializedName("quantity")
     @Expose
     private Double quantity;
     @SerializedName("sub_total")
     @Expose
     private Double subTotal;
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

     public Double getQuantity() {
         return quantity;
     }

     public void setQuantity(Double quantity) {
         this.quantity = quantity;
     }

     public Double getSubTotal() {
         return subTotal;
     }

     public void setSubTotal(Double subTotal) {
         this.subTotal = subTotal;
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
