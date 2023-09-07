package com.prof.reda.android.project.fooddelivery.adapters;

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
import com.prof.reda.android.project.fooddelivery.databinding.RestaurantItemsBinding;
import com.prof.reda.android.project.fooddelivery.models.Restro;
import com.prof.reda.android.project.fooddelivery.utils.Constants;
import com.prof.reda.android.project.fooddelivery.utils.OnClickRestaurantItemListener;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RestroAdapter extends RecyclerView.Adapter<RestroAdapter.RestroViewHolder> {

    private final List<Restro> restroList;
    private OnClickRestaurantItemListener onClickRestroItem;
    private StorageReference restroImgRef;
    public RestroAdapter( List<Restro> restaurantsList, OnClickRestaurantItemListener onClickRestroItem){
        restroList = restaurantsList;
        this.onClickRestroItem = onClickRestroItem;
    }
    @NonNull
    @Override
    public RestroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RestroViewHolder(RestaurantItemsBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RestroViewHolder holder, int position) {
        if (restroList.size() > 0){
            Restro restaurant = restroList.get(position);

            holder.binding.restroNameTv.setText(restaurant.getName());
            holder.binding.distanceInMinute.setText(restaurant.getDeliveryTime());

            restroImgRef = FirebaseStorage.getInstance().getReference(Constants.RESTAURANTS_STORAGE_REF
                    + "/" + restaurant.getPic());

            try {
                File file = File.createTempFile("restaurant", "png");
                restroImgRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Glide.with(holder.itemView)
                                .load(file)
                                .into(holder.binding.restaurantImgView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            holder.itemView.setOnClickListener(view -> {
                onClickRestroItem.onClickRestaurantItem(restaurant);
            });
        }
    }

    @Override
    public int getItemCount() {
        return restroList.size();
    }

    public class RestroViewHolder extends RecyclerView.ViewHolder {
        private RestaurantItemsBinding binding;
        public RestroViewHolder(@NonNull RestaurantItemsBinding restaurantItemsBinding) {
            super(restaurantItemsBinding.getRoot());
            binding = restaurantItemsBinding;
        }
    }
}
