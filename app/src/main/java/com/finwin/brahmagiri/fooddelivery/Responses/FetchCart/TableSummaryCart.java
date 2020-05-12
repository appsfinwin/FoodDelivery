package com.finwin.brahmagiri.fooddelivery.Responses.FetchCart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TableSummaryCart {
    @SerializedName("Total")
    @Expose
    private Double total;
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;
    @SerializedName("Tax")
    @Expose
    private Double tax;
    @SerializedName("GrandTotal")
    @Expose
    private Double grandTotal;
    @SerializedName("DeliveryCharge")
    @Expose
    private Integer deliveryCharge;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Integer getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(Integer deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }


}
