package com.prof.reda.android.project.fooddelivery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prof.reda.android.project.fooddelivery.databinding.OrderItemsBinding;
import com.prof.reda.android.project.fooddelivery.models.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;
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
        holder.binding.orderImgView.setImageResource(order.getImage());
        holder.binding.orderName.setText(order.getMenuName());
        holder.binding.RestroName.setText(order.getRestroName());
        holder.binding.priceTextView.setText("$ ");
        holder.binding.priceTextView.append(String.valueOf(order.getPrice()));

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
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private OrderItemsBinding binding;
        public OrderViewHolder(@NonNull OrderItemsBinding orderItemsBinding) {
            super(orderItemsBinding.getRoot());
            binding = orderItemsBinding;
        }
    }
}