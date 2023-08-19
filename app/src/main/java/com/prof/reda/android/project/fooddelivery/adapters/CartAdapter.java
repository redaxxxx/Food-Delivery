package com.prof.reda.android.project.fooddelivery.adapters;

import android.content.Context;
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
import com.prof.reda.android.project.fooddelivery.utils.OnProcessOrderItemListener;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    private final List<Cart> cartList;

    private Context mContext;
    private OnProcessOrderItemListener onProcess;

    public CartAdapter(Context context, List<Cart> cartList, OnProcessOrderItemListener onProcess){
        this.cartList = cartList;
        mContext = context;
        this.onProcess = onProcess;
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
        holder.binding.menuNameTV.setText(cart.getFoodName());
        holder.binding.priceTV.setText("$ ");
        holder.binding.priceTV.append(cart.getPrice());
        holder.binding.restroName.setText(cart.getRestroName());

        StorageReference foodImgRef = FirebaseStorage.getInstance().getReference("food/" + cart.getFoodImage());
        try {
            File file = File.createTempFile("food", "png");
            foodImgRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Glide.with(holder.itemView).load(file)
                            .into(holder.binding.menuImgView);
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

        holder.binding.processBtn.setTag(position);
        holder.binding.processBtn.setOnClickListener(view -> {
            onProcess.processOrder(cart);
            view.setBackgroundColor(Color.GRAY);
            view.setClickable(false);
        });

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
