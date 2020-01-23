package com.finwin.brahmagiri.fooddelivery.Adapter;

public class MenuItemInListModel {
    Integer image;
    String itemID, itemName, itemDescp, itemAmount;

    public MenuItemInListModel(String _itemID, Integer _image, String _itemName, String _itemDescp, String _itemAmount) {
        this.itemID = _itemID;
        this.image = _image;
        this.itemName = _itemName;
        this.itemDescp = _itemDescp;
        this.itemAmount = _itemAmount;
    }

    public String getMenuItemID() {
        return itemID;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public Integer getImage() {
        return image;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescrptn() {
        return itemDescp;
    }

    public void setItemDescrptn(String itemDescp) {
        this.itemDescp = itemDescp;
    }

    public String getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(String itemAmount) {
        this.itemAmount = itemAmount;
    }

}
