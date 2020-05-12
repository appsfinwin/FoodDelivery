package com.finwin.brahmagiri.fooddelivery.Responses.FetchCart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TableFetchCart {
    @SerializedName("ImageUrl")
    @Expose
    private String ImageUrl;
    @SerializedName("ItemCode")
    @Expose
    private String itemCode;
    @SerializedName("ItemName")
    @Expose
    private String itemName;
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;
    @SerializedName("SellingRate")
    @Expose
    private Double sellingRate;
    @SerializedName("TaxPerc")
    @Expose
    private Double taxPerc;
    @SerializedName("Total")
    @Expose
    private Double total;
    @SerializedName("Tax")
    @Expose
    private Double tax;
    @SerializedName("GrandTotal")
    @Expose
    private Double grandTotal;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSellingRate() {
        return sellingRate;
    }

    public void setSellingRate(Double sellingRate) {
        this.sellingRate = sellingRate;
    }

    public Double getTaxPerc() {
        return taxPerc;
    }

    public void setTaxPerc(Double taxPerc) {
        this.taxPerc = taxPerc;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
