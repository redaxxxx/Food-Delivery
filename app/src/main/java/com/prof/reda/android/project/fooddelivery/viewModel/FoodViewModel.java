package com.prof.reda.android.project.fooddelivery.viewModel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prof.reda.android.project.fooddelivery.database.FoodDatabase;
import com.prof.reda.android.project.fooddelivery.models.EntityOrder;
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

    private final MutableLiveData<List<Order>> ordersMutableLiveData = new MutableLiveData<>();
    private LiveData<List<EntityOrder>> ordersInCartLiveData;
    private final FoodDatabase mDB;

    public FoodViewModel(FoodDatabase database){
        this.mDB = database;
    }


    public LiveData<List<EntityOrder>> getCartIfno(){
        ordersInCartLiveData = mDB.foodDao().getAllOrder();

        return ordersInCartLiveData;
    }


}
