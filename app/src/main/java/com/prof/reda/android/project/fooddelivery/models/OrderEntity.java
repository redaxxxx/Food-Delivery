package com.prof.reda.android.project.fooddelivery.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orderInfo")
public class OrderEntity {

    @NonNull
    @PrimaryKey
    private String orderId;
    private String foodName;
    private String restroName;
    private int price;
    private int quantity;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
