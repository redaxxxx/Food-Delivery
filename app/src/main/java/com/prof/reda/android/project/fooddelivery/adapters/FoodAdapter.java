package com.prof.reda.android.project.fooddelivery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.prof.reda.android.project.fooddelivery.databinding.MenuItemsBinding;
import com.prof.reda.android.project.fooddelivery.models.Food;
import com.prof.reda.android.project.fooddelivery.utils.GetFoodImageFromStorage;
import com.prof.reda.android.project.fooddelivery.utils.OnClickFoodItemListener;
import com.prof.reda.android.project.fooddelivery.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.PopularViewHolder> {

    private Context mContext;
    private final List<Food> foods;
    private final OnClickFoodItemListener onClickFoodItemListener;

    private StorageReference foodImgRef;

    public FoodAdapter(Context mContext, List<Food> foods, OnClickFoodItemListener onClickFoodItemListener){
        this.foods = foods;
        this.onClickFoodItemListener = onClickFoodItemListener;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularViewHolder(MenuItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent
                , false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {

        holder.binding.priceTV.setText("$");

        if (foods.size() > 0){
            Food food = foods.get(position);

            holder.binding.menuNameTV.setText(food.getFoodName());
            holder.binding.restroName.setText(food.getRestroName());

            GetFoodImageFromStorage foodImage = new GetFoodImageFromStorage(mContext, holder.binding.menuImgView,
                    food.getImage());

            foodImage.getFoodImage();

            holder.binding.priceTV.append(food.getPrice());

            holder.itemView.setOnClickListener(view -> {
                onClickFoodItemListener.onClickFoodItem(food);
            });
        }
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {
        private MenuItemsBinding binding;
        public PopularViewHolder(@NonNull MenuItemsBinding menuItemsBinding) {
            super(menuItemsBinding.getRoot());
            binding = menuItemsBinding;
        }
    }
}
