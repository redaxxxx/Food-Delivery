package com.prof.reda.android.project.fooddelivery.models;

import com.prof.reda.android.project.fooddelivery.R;

import java.util.ArrayList;

public class Restro {
    private int id;
    private int pic;
    private String name;
    private String deliveryTime;
    private String cover_photo;
    private String latitude,longitude;

    public Restro(String name, int pic, String deliveryTime){
        this.name = name;
        this.pic = pic;
        this.deliveryTime = deliveryTime;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public static ArrayList<Restro> createRestroList(){

        ArrayList<Restro> restroList = new ArrayList<>();

        restroList.add(new Restro("Vegan Resto", R.drawable.restaurant1, "12 Mins"));
        restroList.add(new Restro("Healthy Food", R.drawable.restaurant2, "8 Mins"));
        restroList.add(new Restro("Good Food", R.drawable.restaurant3, "12 Mins"));
        restroList.add(new Restro("Smart Resto", R.drawable.restaurant4, "8 Mins"));
        restroList.add(new Restro("Vegan Resto", R.drawable.restaurant5, "12 Mins"));
        restroList.add(new Restro("Healthy Food", R.drawable.restaurant6, "8 Mins"));

        return restroList;
    }
}
