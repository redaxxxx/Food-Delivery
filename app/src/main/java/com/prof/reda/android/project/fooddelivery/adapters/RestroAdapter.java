package com.prof.reda.android.project.fooddelivery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prof.reda.android.project.fooddelivery.databinding.RestaurantItemsBinding;
import com.prof.reda.android.project.fooddelivery.models.Restaurants;

import java.util.ArrayList;
import java.util.List;

public class RestroAdapter extends RecyclerView.Adapter<RestroAdapter.RestroViewHolder> {

    private List<Restaurants> restaurants;

    public RestroAdapter(List<Restaurants> restaurantsList){
        restaurants = restaurantsList;
    }
    @NonNull
    @Override
    public RestroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RestroViewHolder(RestaurantItemsBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RestroViewHolder holder, int position) {
        Restaurants restaurant = restaurants.get(position);
        holder.binding.restaurantImgView.setImageResource(restaurant.getRestroImg());
        holder.binding.restroNameTv.setText(restaurant.getRestroName());
        holder.binding.distanceInMinute.setText(restaurant.getDistanceInMins());
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class RestroViewHolder extends RecyclerView.ViewHolder {
        private RestaurantItemsBinding binding;
        public RestroViewHolder(@NonNull RestaurantItemsBinding restaurantItemsBinding) {
            super(restaurantItemsBinding.getRoot());
            binding = restaurantItemsBinding;
        }
    }
}
