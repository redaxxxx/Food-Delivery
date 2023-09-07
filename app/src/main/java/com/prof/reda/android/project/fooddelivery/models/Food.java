package com.prof.reda.android.project.fooddelivery.models;

import com.prof.reda.android.project.fooddelivery.R;

import java.util.ArrayList;

public class Food {
//    private int id;
    private String foodName;
    private String price;
    private String restroName;
    private String image;

    public Food(){

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

//    public static ArrayList<Food> createFoodList(){
//        ArrayList<Food> foods = new ArrayList<>();
//        foods.add(new Food("Herbal Pancake", "Warung Herbal", "7", R.drawable.menu2));
//        foods.add(new Food("Fruit Salad", "Wijie Resto", "5", R.drawable.menu3));
//        foods.add(new Food("Green Noddle", "Noodle Home", "15", R.drawable.menu1));
//
//        return foods;
//    }
}