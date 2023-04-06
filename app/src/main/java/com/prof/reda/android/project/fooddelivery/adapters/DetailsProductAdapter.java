package com.prof.reda.android.project.fooddelivery.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prof.reda.android.project.fooddelivery.databinding.DetailsProductItemBinding;
import com.prof.reda.android.project.fooddelivery.models.Menu;
import com.prof.reda.android.project.fooddelivery.models.Restaurants;

import java.util.List;

public class DetailsProductAdapter extends RecyclerView.Adapter<DetailsProductAdapter.DetailsProductHolder> {

    private List<Menu> MenuList;

    public DetailsProductAdapter(List<Menu> menus){
        MenuList = menus;
    }
    @NonNull
    @Override
    public DetailsProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailsProductHolder(DetailsProductItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsProductHolder holder, int position) {
        Menu menu = MenuList.get(position);
        holder.binding.menuImgView.setImageResource(menu.getMenuImg());
        holder.binding.menuNameTv.setText(menu.getName());
        holder.binding.priceTV.setText("$ ");
        holder.binding.priceTV.append(String.valueOf(menu.getPrice()));
    }

    @Override
    public int getItemCount() {
        return MenuList.size();
    }

    public class DetailsProductHolder extends RecyclerView.ViewHolder {
        private DetailsProductItemBinding binding;
        public DetailsProductHolder(@NonNull DetailsProductItemBinding detailsProductItemBinding) {
            super(detailsProductItemBinding.getRoot());
            binding = detailsProductItemBinding;
        }
    }
}
