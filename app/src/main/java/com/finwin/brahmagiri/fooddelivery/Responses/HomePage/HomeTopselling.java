package com.finwin.brahmagiri.fooddelivery.Responses.HomePage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeTopselling {
    @SerializedName("ItemCode")
    @Expose
    private String itemCode;
    @SerializedName("ItemName")
    @Expose
    private String itemName;
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
    @SerializedName("CartValue")
    @Expose
    private String cartValue;
    @SerializedName("value_occurrence")
    @Expose
    private Double valueOccurrence;

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

    public String getCartValue() {
        return cartValue;
    }

    public void setCartValue(String cartValue) {
        this.cartValue = cartValue;
    }

    public Double getValueOccurrence() {
        return valueOccurrence;
    }

    public void setValueOccurrence(Double valueOccurrence) {
        this.valueOccurrence = valueOccurrence;
    }
}
