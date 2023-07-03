package com.prof.reda.android.project.fooddelivery.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.RestaurantItemsBinding;
import com.prof.reda.android.project.fooddelivery.models.Restro;
import com.prof.reda.android.project.fooddelivery.utils.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestroAdapter extends RecyclerView.Adapter<RestroAdapter.RestroViewHolder> {

    private Context mContext;
    private List<Restro> restroList;
    private OnClickItemListener onClickItemListener;
    private String imageUrl;

    public RestroAdapter(Context context, List<Restro> restaurantsList, OnClickItemListener onClickItemListener){
        mContext = context;
        restroList = restaurantsList;
        this.onClickItemListener = onClickItemListener;
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

            getPicture(holder.binding.restaurantImgView, restaurant);

            Log.d(Constants.TAG, "Image url is: " + restaurant.getPic());
            holder.binding.restroNameTv.setText(restaurant.getName());
            holder.binding.distanceInMinute.setText(restaurant.getDeliveryTime());

            holder.itemView.setOnClickListener(view -> {
                onClickItemListener.onClickRestroItem(restaurant);
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

    private void getPicture(ImageView imageView, Restro restro){

        StringRequest request = new StringRequest(Request.Method.GET, Constants.HOME + restro.getPic(),
                response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("status")){
                    JSONArray jsonArray = new JSONArray(object.getString("data"));
                    if (jsonArray.length() < 0){
                        imageView.setImageResource(R.drawable.restaurant1);
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
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(request);
    }

    public interface OnClickItemListener{
        void onClickRestroItem(Restro restro);
    }
}
