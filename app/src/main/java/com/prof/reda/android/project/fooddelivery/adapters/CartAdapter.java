package com.prof.reda.android.project.fooddelivery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prof.reda.android.project.fooddelivery.databinding.CartItemBinding;
import com.prof.reda.android.project.fooddelivery.models.Menu;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    private List<Menu> menuList;

    public CartAdapter(List<Menu> menus){
        menuList = menus;
    }
    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartHolder(CartItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        Menu menu = menuList.get(position);
        holder.binding.menuImgView.setImageResource(menu.getMenuImg());
        holder.binding.menuNameTV.setText(menu.getName());
        holder.binding.priceTV.setText("$ ");
        holder.binding.priceTV.append(String.valueOf(menu.getPrice()));
        holder.binding.restroName.setText(menu.getRestroName());
        holder.binding.processBtn.setTag(position);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder {
        private CartItemBinding binding;
        public CartHolder(@NonNull CartItemBinding cartItemBinding) {
            super(cartItemBinding.getRoot());
            binding = cartItemBinding;
        }
    }
}
