package com.prof.reda.android.project.fooddelivery.models;

public class Order {
    private String foodName;
    private String restroName;
    private String price;
    private int foodImage;

    public Order(){

    }
    public Order(String foodName, String restroName, String price, int foodImage) {
        this.foodName = foodName;
        this.restroName = restroName;
        this.price = price;
        this.foodImage = foodImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getRestroName() {
        return restroName;
    }

    public void setRestroName(String restroName) {
        this.restroName = restroName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(int foodImage) {
        this.foodImage = foodImage;
    }
}
