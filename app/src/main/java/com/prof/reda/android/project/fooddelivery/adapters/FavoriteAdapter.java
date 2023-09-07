package com.prof.reda.android.project.fooddelivery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prof.reda.android.project.fooddelivery.databinding.FavoriteItemBinding;
import com.prof.reda.android.project.fooddelivery.models.Menu;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {

    private List<Menu> menuList;

    public FavoriteAdapter(List<Menu> menus){
        menuList = menus;
    }
    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteHolder(FavoriteItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder holder, int position) {
        Menu menu = menuList.get(position);
        holder.binding.menuImgView.setImageResource(menu.getMenuImg());
        holder.binding.menuNameTV.setText(menu.getName());
        holder.binding.priceTV.setText("$ ");
        holder.binding.priceTV.append(String.valueOf(menu.getPrice()));
        holder.binding.restroName.setText(menu.getRestroName());
        holder.binding.buyAgainBtn.setTag(position);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class FavoriteHolder extends RecyclerView.ViewHolder {
        private FavoriteItemBinding binding;
        public FavoriteHolder(FavoriteItemBinding favoriteItemBinding) {
            super(favoriteItemBinding.getRoot());
            binding = favoriteItemBinding;
        }
    }
}
