package com.finwin.brahmagiri.fooddelivery.Responses.HomePage;

import com.finwin.brahmagiri.fooddelivery.Responses.Itemlisting.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeData {
    @SerializedName("Table")
    @Expose
    private List<CustDetails> table = null;
    @SerializedName("Table1")
    @Expose
    private List<HomeTopselling> table1 = null;
    @SerializedName("Table2")
    @Expose
    private List<HomePageCat> table2 = null;

    public List<CustDetails> getTable() {
        return table;
    }

    public void setTable(List<CustDetails> table) {
        this.table = table;
    }

    public List<HomeTopselling> getTable1() {
        return table1;
    }

    public void setTable1(List<HomeTopselling> table1) {
        this.table1 = table1;
    }

    public List<HomePageCat> getTable2() {
        return table2;
    }

    public void setTable2(List<HomePageCat> table2) {
        this.table2 = table2;
    }
}
