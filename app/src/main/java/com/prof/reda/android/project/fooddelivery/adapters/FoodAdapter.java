package com.prof.reda.android.project.fooddelivery.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.MenuItemsBinding;
import com.prof.reda.android.project.fooddelivery.models.Food;
import com.prof.reda.android.project.fooddelivery.models.Menu;
import com.prof.reda.android.project.fooddelivery.models.Restro;
import com.prof.reda.android.project.fooddelivery.utils.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.PopularViewHolder> {

    private List<Food> foods = new ArrayList<>();
    private final OnItemClickListener onItemClickListener;

    private StorageReference foodImgRef;

    public FoodAdapter(List<Food> foods, OnItemClickListener onItemClickListener){
        this.foods = foods;
        this.onItemClickListener = onItemClickListener;
        foodImgRef = FirebaseStorage.getInstance().getReference("food");

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
            holder.binding.menuImgView.setImageResource(food.getImage());

            holder.binding.priceTV.append(food.getPrice());

            holder.itemView.setOnClickListener(view -> {
                onItemClickListener.onClickItem(food);
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

    public interface OnItemClickListener{
        void onClickItem(Food food);
    }
}
