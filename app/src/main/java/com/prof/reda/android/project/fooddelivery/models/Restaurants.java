package com.prof.reda.android.project.fooddelivery.models;

import android.graphics.drawable.Drawable;

public class Restaurants {
    private int restroImg;
    private int menuImg;
    private String menuName;
    private int price;

    private String restroName;
    private String distanceInMins;


    public Restaurants(int menuImg, String menuName, String restroName, int price){
        this.menuImg = menuImg;
        this.menuName = menuName;
        this.restroName = restroName;
        this.price = price;
    }
    public Restaurants(int restroImg, String restroName, String distanceInMins) {
        this.restroImg = restroImg;
        this.restroName = restroName;
        this.distanceInMins = distanceInMins;
    }

    public Restaurants(int menuImg, String menuName, int price){
        this.menuImg = menuImg;
        this.menuName = menuName;
        this.price = price;
    }

    public int getRestroImg() {
        return restroImg;
    }

    public void setRestroImg(int restroImg) {
        this.restroImg = restroImg;
    }

    public String getRestroName() {
        return restroName;
    }

    public void setRestroName(String restroName) {
        this.restroName = restroName;
    }

    public String getDistanceInMins() {
        return distanceInMins;
    }

    public void setDistanceInMins(String distanceInMins) {
        this.distanceInMins = distanceInMins;
    }

    public int getMenuImg() {
        return menuImg;
    }

    public void setMenuImg(int menuImg) {
        this.menuImg = menuImg;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
