package com.finwin.brahmagiri.fooddelivery.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Outlet {

    @SerializedName("outlet_address")
    @Expose
    private String outletAddress;
    @SerializedName("outlet_mobile")
    @Expose
    private String outletMobile;
    @SerializedName("zone_id")
    @Expose
    private Integer zoneId;
    @SerializedName("cluster")
    @Expose
    private Integer cluster;
    @SerializedName("outlet")
    @Expose
    private Integer outlet;
    @SerializedName("outlet_street")
    @Expose
    private String outletStreet;
    @SerializedName("outlet_image")
    @Expose
    private String outletImage;
    @SerializedName("outlet_name")
    @Expose
    private String outletName;
    @SerializedName("outlet_landmark")
    @Expose
    private String outletLandmark;
    @SerializedName("holiday_status")
    @Expose
    private String holiday_status;

    @SerializedName("distance")
    @Expose
    private Double distance;




    public String getOutletAddress() {
        return outletAddress;
    }

    public void setOutletAddress(String outletAddress) {
        this.outletAddress = outletAddress;
    }

    public String getOutletMobile() {
        return outletMobile;
    }

    public void setOutletMobile(String outletMobile) {
        this.outletMobile = outletMobile;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getCluster() {
        return cluster;
    }

    public void setCluster(Integer cluster) {
        this.cluster = cluster;
    }

    public Integer getOutlet() {
        return outlet;
    }

    public void setOutlet(Integer outlet) {
        this.outlet = outlet;
    }

    public String getOutletStreet() {
        return outletStreet;
    }

    public void setOutletStreet(String outletStreet) {
        this.outletStreet = outletStreet;
    }

    public String getOutletImage() {
        return outletImage;
    }

    public void setOutletImage(String outletImage) {
        this.outletImage = outletImage;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getOutletLandmark() {
        return outletLandmark;
    }

    public void setOutletLandmark(String outletLandmark) {
        this.outletLandmark = outletLandmark;
    }

    public String getHoliday_status() {
        return holiday_status;
    }

    public void setHoliday_status(String holiday_status) {
        this.holiday_status = holiday_status;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
