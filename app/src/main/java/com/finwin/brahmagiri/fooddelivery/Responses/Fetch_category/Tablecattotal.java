package com.finwin.brahmagiri.fooddelivery.Responses.Fetch_category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tablecattotal {
    @SerializedName("TotalNo")
    @Expose
    private Integer totalNo;
    @SerializedName("PageSize")
    @Expose
    private Integer pageSize;
    @SerializedName("NoOfPages")
    @Expose
    private Integer noOfPages;

    public Integer getTotalNo() {
        return totalNo;
    }

    public void setTotalNo(Integer totalNo) {
        this.totalNo = totalNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getNoOfPages() {
        return noOfPages;
    }

    public void setNoOfPages(Integer noOfPages) {
        this.noOfPages = noOfPages;
    }

}
