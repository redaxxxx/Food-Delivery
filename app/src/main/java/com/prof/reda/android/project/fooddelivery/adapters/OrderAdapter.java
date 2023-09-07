package com.prof.reda.android.project.fooddelivery.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prof.reda.android.project.fooddelivery.databinding.OrderItemsBinding;
import com.prof.reda.android.project.fooddelivery.models.Order;
import com.prof.reda.android.project.fooddelivery.models.OrderEntity;
import com.prof.reda.android.project.fooddelivery.utils.GetFoodImageFromStorage;
import com.prof.reda.android.project.fooddelivery.utils.OrderSelectedListener;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context mContext;
    private final List<Order> orderList;
    private int price;
    private Order order;
    private Intent intent;
    private int subTotal = 0;
    public OrderAdapter(Context mContext, List<Order> orders){
        this.mContext = mContext;
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
        order = orderList.get(position);

        holder.binding.orderName.setText(order.getFoodName());
        holder.binding.RestroName.setText(order.getRestroName());
        holder.binding.priceTextView.setText(order.getPrice());

        holder.binding.addImageButton.setTag(position);
        holder.binding.minusImageButton.setTag(position);

        holder.binding.addImageButton.setOnClickListener(view -> {
            int quantity = 1;
            int price = Integer.parseInt(holder.binding.priceTextView.getText().toString());
            try{
                quantity = Integer.parseInt(holder.binding.quantityTv.getText().toString());
            }catch (Exception e){
                quantity = 1;
            }
            quantity++;
//            subTotal += (quantity * price) - ((quantity - 1) * price);
            subTotal += ((quantity -(quantity - 1)) * price);
            holder.binding.quantityTv.setText(String.valueOf(quantity));

            intent = new Intent("Custom-Message");

            intent.putExtra("subTotal", subTotal);

            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

        });

        holder.binding.minusImageButton.setOnClickListener(view -> {
            int quantity = 1;
            int price = Integer.parseInt(holder.binding.priceTextView.getText().toString());
            try{
                quantity = Integer.parseInt(holder.binding.quantityTv.getText().toString());

            }catch (Exception e){
                quantity = 1;
            }
            if (quantity > 1){
                quantity--;
                holder.binding.quantityTv.setText(String.valueOf(quantity));
                subTotal -= ((quantity - (quantity - 1)) * price);

                intent = new Intent("Custom-Message");
                intent.putExtra("subTotal", subTotal);
                intent.putExtra("quantity", holder.binding.quantityTv.getText().toString());
                intent.putExtra("price", holder.binding.priceTextView.getText().toString());
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            }

        });

        GetFoodImageFromStorage getImageFromStorage = new GetFoodImageFromStorage(mContext, holder.binding.orderImgView,
                order.getFoodImage());

        getImageFromStorage.getFoodImage();
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