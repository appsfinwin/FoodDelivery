package com.finwin.brahmagiri.fooddelivery.Adapter;

public class MenuItemModel {
    Integer image;
    String foodName, totalRes, itemID;

    public MenuItemModel(String _itemID, Integer image, String foodName, String totalRes) {
        this.itemID = _itemID;
        this.image = image;
        this.foodName = foodName;
        this.totalRes = totalRes;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String item_ID) {
        this.itemID = item_ID;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getTotalRes() {
        return totalRes;
    }

    public void setTotalRes(String totalRes) {
        this.totalRes = totalRes;
    }
}
