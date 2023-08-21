package com.prof.reda.android.project.fooddelivery.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prof.reda.android.project.fooddelivery.databinding.OrderItemsBinding;
import com.prof.reda.android.project.fooddelivery.models.Order;
import com.prof.reda.android.project.fooddelivery.utils.GetFoodImageFromStorage;
import com.prof.reda.android.project.fooddelivery.utils.PriceOrderSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context mContext;
    private final List<Order> orderList;
    private int price;
    private int subTotal = 0;
//    private List<Integer> priceTotal;
    private Order order;
    private PriceOrderSelectedListener orderSelectedListener;
    public OrderAdapter(Context mContext, List<Order> orders, PriceOrderSelectedListener orderSelectedListener ){
        this.mContext = mContext;
        orderList = orders;
        this.orderSelectedListener = orderSelectedListener;
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

        price = Integer.parseInt(holder.binding.priceTextView.getText().toString());
        holder.binding.orderName.setText(order.getFoodName());
        holder.binding.RestroName.setText(order.getRestroName());
        holder.binding.priceTextView.setText(order.getPrice());

        holder.binding.addImageButton.setTag(position);
        holder.binding.minusImageButton.setTag(position);

        holder.binding.addImageButton.setOnClickListener(view -> {
            int quantity = 1;
            int price = Integer.parseInt(holder.binding.priceTextView.getText().toString());
            int totalPrice = price;
            try{
                quantity = Integer.parseInt(holder.binding.quantityTv.getText().toString());
                totalPrice = (quantity * price) + price;

            }catch (Exception e){
                quantity = 1;
            }
            quantity++;
            holder.binding.quantityTv.setText(String.valueOf(quantity));
            orderSelectedListener.orderPrice(totalPrice);
        });

        holder.binding.minusImageButton.setOnClickListener(view -> {
            int quantity = 1;
            int price = Integer.parseInt(holder.binding.priceTextView.getText().toString());
            int totalPrice = price;
            try{
                quantity = Integer.parseInt(holder.binding.quantityTv.getText().toString());
                totalPrice = (quantity * price) - price;

            }catch (Exception e){
                quantity = 1;
            }
            if (quantity > 1){
                quantity--;
                holder.binding.quantityTv.setText(String.valueOf(quantity));
                orderSelectedListener.orderPrice(totalPrice);
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