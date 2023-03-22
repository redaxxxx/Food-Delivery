package com.prof.reda.android.project.fooddelivery.models;

public class Chat {

    int Image;
    String name;
    String message;
    String time;


    public Chat(int image, String name, String chat, String time) {
        Image = image;
        this.name = name;
        this.message = chat;
        this.time = time;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
