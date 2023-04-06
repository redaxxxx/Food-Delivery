package com.prof.reda.android.project.fooddelivery.models;

public class Menu {
    private String name;
    private int menuImg;
    private int price;
    private String restroName;

    public Menu(int menuImg,String name, String restroName, int price ) {
        this.name = name;
        this.menuImg = menuImg;
        this.price = price;
        this.restroName = restroName;
    }

    public Menu(int menuImg, String name, int price) {
        this.name = name;
        this.menuImg = menuImg;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMenuImg() {
        return menuImg;
    }

    public void setMenuImg(int menuImg) {
        this.menuImg = menuImg;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRestroName() {
        return restroName;
    }

    public void setRestroName(String restroName) {
        this.restroName = restroName;
    }
}
