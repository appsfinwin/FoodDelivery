package com.finwin.brahmagiri.fooddelivery.Adapter;

public class ConfirmOrderModel {
    String dishName,quantity,rupees;

    public ConfirmOrderModel(String dishName, String quantity, String rupees) {
        this.dishName = dishName;
        this.quantity = quantity;
        this.rupees = rupees;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRupees() {
        return rupees;
    }

    public void setRupees(String rupees) {
        this.rupees = rupees;
    }
}

