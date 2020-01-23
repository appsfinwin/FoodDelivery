package com.finwin.brahmagiri.fooddelivery.Adapter;

public class CartModel {
    String itemID, itemName, itemCount, Amount;

    public CartModel(String _itemID, String _itemName, String _itemCount, String _Amount) {
        this.itemID = _itemID;
        this.itemName = _itemName;
        this.itemCount = _itemCount;
        this.Amount = _Amount;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String _itemID) {
        this.itemID = _itemID;
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String _itemName) {
        this.itemName = _itemName;
    }


    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String _itemCount) {
        this.itemCount = _itemCount;
    }


    public String getItemAmount() {
        return Amount;
    }

    public void setItemAmount(String _Amount) {
        this.Amount = _Amount;
    }

}
