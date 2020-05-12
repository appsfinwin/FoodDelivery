package com.finwin.brahmagiri.fooddelivery.Responses.Itemlisting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Table {

    @SerializedName("ItemName")
    @Expose
    private String itemName;
    @SerializedName("ItemCode")
    @Expose
    private String itemCode;
    @SerializedName("MRP")
    @Expose
    private Double mRP;
    @SerializedName("Rate")
    @Expose
    private Double rate;
    @SerializedName("SellingRate")
    @Expose
    private Double sellingRate;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("ImageDescription")
    @Expose
    private String imageDescription;
    @SerializedName("Favorite")
    @Expose
    private String favorite;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("CartValue")
    @Expose
    private Integer CartValue;


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Double getMRP() {
        return mRP;
    }

    public void setMRP(Double mRP) {
        this.mRP = mRP;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getSellingRate() {
        return sellingRate;
    }

    public void setSellingRate(Double sellingRate) {
        this.sellingRate = sellingRate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Integer getCartValue() {
        return CartValue;
    }

    public void setCartValue(Integer cartValue) {
        CartValue = cartValue;
    }
}