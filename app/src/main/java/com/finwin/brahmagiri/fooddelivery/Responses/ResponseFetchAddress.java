package com.finwin.brahmagiri.fooddelivery.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseFetchAddress {
    @SerializedName("delivery_location")
    @Expose
    private String deliveryLocation;
    @SerializedName("delivery_charge")
    @Expose
    private Double deliveryCharge;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("total_amnt")
    @Expose
    private Double totalAmnt;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("message")
    @Expose
    private String message;

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public Double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(Double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Double getTotalAmnt() {
        return totalAmnt;
    }

    public void setTotalAmnt(Double totalAmnt) {
        this.totalAmnt = totalAmnt;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
