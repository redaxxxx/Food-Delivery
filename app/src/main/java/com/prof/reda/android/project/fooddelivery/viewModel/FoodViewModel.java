package com.prof.reda.android.project.fooddelivery.viewModel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prof.reda.android.project.fooddelivery.adapters.FoodAdapter;
import com.prof.reda.android.project.fooddelivery.adapters.RestroAdapter;
import com.prof.reda.android.project.fooddelivery.database.FoodDao;
import com.prof.reda.android.project.fooddelivery.database.FoodDatabase;
import com.prof.reda.android.project.fooddelivery.models.Food;
import com.prof.reda.android.project.fooddelivery.models.Order;
import com.prof.reda.android.project.fooddelivery.models.Restro;
import com.prof.reda.android.project.fooddelivery.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodViewModel extends ViewModel {

    private final MutableLiveData<List<Food>> foodMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<List<Restro>> restaurantLiveData = new MutableLiveData<>();
    private FoodDatabase mDB;

    public FoodViewModel(FoodDatabase database){
        this.mDB = database;
    }

    public MutableLiveData<List<Food>> getFood(Context context, String token){

        StringRequest request = new StringRequest(Request.Method.GET, Constants.FOOD, response -> {
            try {
                List<Food> foodList = new ArrayList<>();
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("status")){
                    JSONArray array = new JSONArray(object.getString("data"));
                    for (int i = 0; i < array.length(); i++){
                        JSONObject foodObject = array.getJSONObject(i);
                        Food food = new Food();
                        food.setId(foodObject.getInt("id"));
                        food.setName(foodObject.getString("name"));
                        food.setPrice(foodObject.getString("price"));
                        food.setImage(foodObject.getString("pic"));
                        foodList.add(food);

                        Log.d(Constants.TAG, "Pic of food:" + foodObject.getString("pic"));
                    }

                    foodMutableLiveData.setValue(foodList);


                }else{
                    Log.d(Constants.TAG, "condition return false");
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        },error -> {
        }){
            //provide token in header
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String,String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + token);
                map.put("Content-Type", "application/json");
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);

        return foodMutableLiveData;
    }

    public MutableLiveData<List<Restro>> getRestaurant(Context context, String token){
        List<Restro> restroList = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, Constants.RESTRO, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("status")){
                    JSONArray array = new JSONArray(object.getString("data"));
                    for (int i = 0; i < array.length(); i++){
                        JSONObject restroObject = array.getJSONObject(i);
                        Restro restro = new Restro();
                        restro.setId(restroObject.getInt("id"));
                        restro.setPic(restroObject.getString("pic"));
                        restro.setName(restroObject.getString("name"));
                        restro.setDeliveryTime(restroObject.getString("delivery_time"));
                        restroList.add(restro);
                    }

                    restaurantLiveData.setValue(restroList);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        },error -> {
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + token);
                map.put("Content-Type", "text/plain");
                map.put("Accept", "application/json");
                map.put("Accept-Encoding", "gzip, deflate, br");
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);

        return restaurantLiveData;
    }


}
