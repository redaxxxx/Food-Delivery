package com.prof.reda.android.project.fooddelivery.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.CartItemBinding;
import com.prof.reda.android.project.fooddelivery.models.Cart;
import com.prof.reda.android.project.fooddelivery.utils.GetFoodImageFromStorage;
import com.prof.reda.android.project.fooddelivery.utils.OnProcessOrderItemListener;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    private final List<Cart> cartList;

    private Context mContext;
    private OnProcessOrderItemListener onProcess;
    private SharedPreferences preferences;

    public CartAdapter(Context context, List<Cart> cartList, OnProcessOrderItemListener onProcess){
        this.cartList = cartList;
        mContext = context;
        this.onProcess = onProcess;
        preferences = mContext.getSharedPreferences("ProcessButton" , Context.MODE_PRIVATE);
    }
    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartHolder(CartItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        Cart cart = cartList.get(position);
        holder.binding.foodNameTV.setText(cart.getFoodName());
        holder.binding.priceTV.setText("$ ");
        holder.binding.priceTV.append(cart.getPrice());
        holder.binding.restroNameTV.setText(cart.getRestroName());

        GetFoodImageFromStorage foodImage = new GetFoodImageFromStorage(mContext, holder.binding.menuImgView,
                cart.getFoodImage());

        foodImage.getFoodImage();

        SharedPreferences.Editor editor = preferences.edit();
        holder.binding.processBtn.setTag(position);
        holder.binding.processBtn.setOnClickListener(view -> {
            onProcess.processOrder(cart);
            editor.putBoolean(cart.getCartId(), true);
            editor.apply();
            view.setBackgroundColor(Color.GRAY);
            view.setClickable(false);
        });

        if (preferences.getBoolean(cart.getCartId() , false)){
            holder.binding.processBtn.setBackgroundColor(Color.GRAY);
            holder.binding.processBtn.setClickable(false);
        }

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder {
        private final CartItemBinding binding;
        public CartHolder(@NonNull CartItemBinding cartItemBinding) {
            super(cartItemBinding.getRoot());
            binding = cartItemBinding;
        }
    }
}
