package com.prof.reda.android.project.fooddelivery.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prof.reda.android.project.fooddelivery.databinding.MenuItemsBinding;
import com.prof.reda.android.project.fooddelivery.models.Restaurants;

import java.util.List;

public class PopularMenuAdapter extends RecyclerView.Adapter<PopularMenuAdapter.PopularViewHolder> {

    private List<Restaurants> restaurants;

    public PopularMenuAdapter(List<Restaurants> restaurants){
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularViewHolder(MenuItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent
                , false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        Restaurants restaurant = restaurants.get(position);
        holder.binding.menuImgView.setImageResource(restaurant.getMenuImg());
        holder.binding.menuNameTV.setText(restaurant.getMenuName());
        holder.binding.restroName.setText(restaurant.getRestroName());
        holder.binding.priceTV.setText("$");
        holder.binding.priceTV.append(String.valueOf(restaurant.getPrice()));
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {
        private MenuItemsBinding binding;
        public PopularViewHolder(@NonNull MenuItemsBinding menuItemsBinding) {
            super(menuItemsBinding.getRoot());
            binding = menuItemsBinding;
        }
    }
}
