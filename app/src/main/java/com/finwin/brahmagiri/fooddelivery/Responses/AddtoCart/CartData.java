package com.finwin.brahmagiri.fooddelivery.Responses.AddtoCart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartData {
    @SerializedName("Table1")
    @Expose
    private List<TableCart> table1 = null;

    public List<TableCart> getTable1() {
        return table1;
    }

    public void setTable1(List<TableCart> table1) {
        this.table1 = table1;
    }
}
