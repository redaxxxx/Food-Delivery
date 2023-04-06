package com.prof.reda.android.project.fooddelivery.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prof.reda.android.project.fooddelivery.databinding.MenuItemsBinding;
import com.prof.reda.android.project.fooddelivery.models.Menu;
import com.prof.reda.android.project.fooddelivery.models.Restaurants;

import java.util.List;

public class PopularMenuAdapter extends RecyclerView.Adapter<PopularMenuAdapter.PopularViewHolder> {

    private List<Menu> menus;
    private OnItemClickListener onItemClickListener;
    public PopularMenuAdapter(List<Menu> menus, OnItemClickListener onItemClickListener){
        this.menus = menus;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularViewHolder(MenuItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent
                , false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        Menu menu = menus.get(position);
        holder.binding.menuImgView.setImageResource(menu.getMenuImg());
        holder.binding.menuNameTV.setText(menu.getName());
        holder.binding.restroName.setText(menu.getRestroName());
        holder.binding.priceTV.setText("$");
        holder.binding.priceTV.append(String.valueOf(menu.getPrice()));

        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onClickItem(menu);
        });
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {
        private MenuItemsBinding binding;
        public PopularViewHolder(@NonNull MenuItemsBinding menuItemsBinding) {
            super(menuItemsBinding.getRoot());
            binding = menuItemsBinding;
        }
    }

    public interface OnItemClickListener{
        void onClickItem(Menu menu);
    }
}
