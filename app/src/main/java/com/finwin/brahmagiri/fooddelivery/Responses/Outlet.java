package com.finwin.brahmagiri.fooddelivery.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Outlet {

    @SerializedName("outlet_name")
    @Expose
    private String outletName;
     @SerializedName("cluster")
     @Expose
     private Integer cluster;
     @SerializedName("outlet")
     @Expose
     private Integer outlet;
     @SerializedName("zone_id")
     @Expose
     private Integer zoneId;

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

     public Integer getZoneId() {
         return zoneId;
     }

     public void setZoneId(Integer zoneId) {
         this.zoneId = zoneId;
     }
    @Override
    public String toString() {
        return outletName;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }
}
