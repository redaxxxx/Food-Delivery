package com.prof.reda.android.project.fooddelivery.models;

public class Cart {
    private String cartId;
    private String foodName;
    private String restroName;
    private String price;
    private String foodImage;

    public Cart(){

    }
    public Cart(String foodName, String restroName, String price, String foodImage) {
        this.foodName = foodName;
        this.restroName = restroName;
        this.price = price;
        this.foodImage = foodImage;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
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

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }
}
