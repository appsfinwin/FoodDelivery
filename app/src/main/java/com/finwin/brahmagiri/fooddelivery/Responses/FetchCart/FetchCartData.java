package com.finwin.brahmagiri.fooddelivery.Responses.FetchCart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchCartData {
    @SerializedName("Table")
    @Expose
    private List<TableFetchCart> table = null;
    @SerializedName("Table1")
    @Expose
    private List<TableSummaryCart> table1 = null;

    public List<TableFetchCart> getTable() {
        return table;
    }

    public void setTable(List<TableFetchCart> table) {
        this.table = table;
    }

    public List<TableSummaryCart> getTable1() {
        return table1;
    }

    public void setTable1(List<TableSummaryCart> table1) {
        this.table1 = table1;
    }

}
