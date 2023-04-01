package com.prof.reda.android.project.fooddelivery.models;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

public class PlaceDataModel {
    private String placeId;
    private String address;
    private String name;
    private Uri websiteUri;
    private String phoneNumber;
    private LatLng latLng;
    private String attributions;


    public PlaceDataModel(String placeId, String address, String name, Uri websiteUri, String phoneNumber,
                          LatLng latLng, String attributions) {
        this.placeId = placeId;
        this.address = address;
        this.name = name;
        this.websiteUri = websiteUri;
        this.phoneNumber = phoneNumber;
        this.latLng = latLng;
        this.attributions = attributions;
    }

    public PlaceDataModel(){

    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getWebsiteUri() {
        return websiteUri;
    }

    public void setWebsiteUri(Uri websiteUri) {
        this.websiteUri = websiteUri;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getAttributions() {
        return attributions;
    }

    public void setAttributions(String attributions) {
        this.attributions = attributions;
    }

    @Override
    public String toString() {
        return "PlaceDataModel{" +
                "placeId='" + placeId + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", websiteUri=" + websiteUri +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", latLng=" + latLng +
                ", attributions='" + attributions + '\'' +
                '}';
    }
}
