package com.prof.reda.android.project.fooddelivery.models;

public class Order {
    private int image;
    private String menuName;
    private String restroName;
    private int price;
    private int mQuantity;



    public Order(int image, String menuName, String restroName, int price, int quantity) {
        this.image = image;
        this.menuName = menuName;
        this.restroName = restroName;
        this.price = price;
        this.mQuantity = quantity;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
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

    public int getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

}
