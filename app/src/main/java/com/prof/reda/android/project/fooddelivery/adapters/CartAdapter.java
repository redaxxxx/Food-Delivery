package com.prof.reda.android.project.fooddelivery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prof.reda.android.project.fooddelivery.databinding.CartItemBinding;
import com.prof.reda.android.project.fooddelivery.models.EntityOrder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    private Context mContext;
    private List<EntityOrder> orders;

    public CartAdapter(Context context, List<EntityOrder> orderList){
        mContext = context;
        orders = orderList;
    }
    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartHolder(CartItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        EntityOrder order = orders.get(position);
        Picasso.with(mContext).load(order.getImage()).into(holder.binding.menuImgView);
        holder.binding.menuNameTV.setText(order.getFoodName());
        holder.binding.priceTV.setText("$ ");
        holder.binding.priceTV.append(String.valueOf(order.getPrice()));
        holder.binding.processBtn.setTag(position);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder {
        private CartItemBinding binding;
        public CartHolder(@NonNull CartItemBinding cartItemBinding) {
            super(cartItemBinding.getRoot());
            binding = cartItemBinding;
        }
    }
}
