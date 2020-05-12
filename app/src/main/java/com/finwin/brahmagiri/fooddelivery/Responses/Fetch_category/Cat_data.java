package com.finwin.brahmagiri.fooddelivery.Responses.Fetch_category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Locale;

public class Cat_data {
    @SerializedName("Table")
    @Expose
    private List<ItemCat> table = null;
    @SerializedName("Table1")
    @Expose
    private List<Tablecattotal> table1 = null;

    public List<ItemCat> getTable() {
        return table;
    }

    public void setTable(List<ItemCat> table) {
        this.table = table;
    }

    public List<Tablecattotal> getTable1() {
        return table1;
    }

    public void setTable1(List<Tablecattotal> table1) {
        this.table1 = table1;
    }
}
