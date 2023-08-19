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
import com.prof.reda.android.project.fooddelivery.databinding.OrderItemsBinding;
import com.prof.reda.android.project.fooddelivery.models.Order;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final List<Order> orderList;
    private int quantity = 1;

    public OrderAdapter(List<Order> orders){
        orderList = orders;
    }
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(OrderItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        holder.binding.orderName.setText(order.getFoodName());
        holder.binding.RestroName.setText(order.getRestroName());
        holder.binding.priceTextView.setText(order.getPrice());

        holder.binding.addImageButton.setTag(position);
        holder.binding.minusImageButton.setTag(position);


        holder.binding.addImageButton.setOnClickListener(view -> {
            quantity += 1;
            holder.binding.quantityTv.setText(String.valueOf(quantity));
        });

        holder.binding.minusImageButton.setOnClickListener(view -> {
            if (quantity > 1){
                quantity -= 1;
                holder.binding.quantityTv.setText(String.valueOf(quantity));
            }
        });

        StorageReference foodImgRef = FirebaseStorage.getInstance().getReference("food/" + order.getFoodImage());
        try {
            File file = File.createTempFile("food", "png");
            foodImgRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Glide.with(holder.itemView).load(file)
                            .into(holder.binding.orderImgView);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private final OrderItemsBinding binding;
        public OrderViewHolder(@NonNull OrderItemsBinding orderItemsBinding) {
            super(orderItemsBinding.getRoot());
            binding = orderItemsBinding;
        }
    }

}