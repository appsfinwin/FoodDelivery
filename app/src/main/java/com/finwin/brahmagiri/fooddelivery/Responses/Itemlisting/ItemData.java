package com.finwin.brahmagiri.fooddelivery.Responses.Itemlisting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemData {
    @SerializedName("Table")
    @Expose
    private List<Table> table = null;
    @SerializedName("Table1")
    @Expose
    private List<Table1> table1 = null;

    public List<Table> getTable() {
        return table;
    }

    public void setTable(List<Table> table) {
        this.table = table;
    }

    public List<Table1> getTable1() {
        return table1;
    }

    public void setTable1(List<Table1> table1) {
        this.table1 = table1;
    }

}
