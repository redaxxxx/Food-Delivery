package com.prof.reda.android.project.fooddelivery.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

    private final Context context;
    private List<Food> foods;
    private OnItemClickListener onItemClickListener;
    public FoodAdapter(Context context, List<Food> foods, OnItemClickListener onItemClickListener){
        this.context = context;
        this.foods = foods;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularViewHolder(MenuItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent
                , false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {

        if (foods.size() > 0){
            Food food = foods.get(position);

            getPicture(holder.binding.menuImgView, food);

            holder.binding.menuNameTV.setText(food.getName());
            holder.binding.restroName.setText(food.getName());
            holder.binding.priceTV.setText("$" + food.getPrice());

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

    private void getPicture(ImageView imageView, Food food){
        StringRequest request = new StringRequest(Request.Method.GET, Constants.HOME + food.getImage(),
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getBoolean("status")){
                            JSONArray jsonArray = new JSONArray(object.getString("data"));
                            if (jsonArray.length() < 0){
                                imageView.setImageResource(R.drawable.menu1);
                            }else {

                            }
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }, error -> {
                    error.printStackTrace();
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("Authorization", "Bearer 610|NlAqHfcHkLiGtRVFW9Li7wDmdfCm5dl3CCcwccYM");
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    public interface OnItemClickListener{
        void onClickItem(Food food);
    }
}
