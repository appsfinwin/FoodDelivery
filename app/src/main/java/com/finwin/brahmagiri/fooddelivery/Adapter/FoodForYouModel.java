package com.finwin.brahmagiri.fooddelivery.Adapter;

public class FoodForYouModel {
    String ratings, restaurantName, restaurantCuisine, dliveryTime, amount, paymentMode;
    Integer foodImage;

    public FoodForYouModel(String ratings, String restaurantName, String restaurantCuisine, String dliveryTime, String amount, String paymentMode, Integer foodImage) {
        this.ratings = ratings;
        this.restaurantName = restaurantName;
        this.restaurantCuisine = restaurantCuisine;
        this.dliveryTime = dliveryTime;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.foodImage = foodImage;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantCuisine() {
        return restaurantCuisine;
    }

    public void setRestaurantCuisine(String restaurantCuisine) {
        this.restaurantCuisine = restaurantCuisine;
    }

    public String getDliveryTime() {
        return dliveryTime;
    }

    public void setDliveryTime(String dliveryTime) {
        this.dliveryTime = dliveryTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Integer getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(Integer foodImage) {
        this.foodImage = foodImage;
    }
}
