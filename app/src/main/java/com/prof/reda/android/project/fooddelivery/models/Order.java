package com.prof.reda.android.project.fooddelivery.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orderInfo")
public class Order {
    @PrimaryKey
    private int id;
    private String image;
    private String foodName;
    private String restroName;
    private int price;
    private int mQuantity;


    public Order(){

    }

    public Order(String image, String menuName, String restroName, int price, int quantity) {
        this.image = image;
        this.foodName = menuName;
        this.restroName = restroName;
        this.price = price;
        this.mQuantity = quantity;
    }

    public Order(String image, String menuName, String restroName, int price) {
        this.image = image;
        this.foodName = menuName;
        this.restroName = restroName;
        this.price = price;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
        return mQuantity;
    }

    public void setQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

}
