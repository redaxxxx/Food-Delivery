package com.prof.reda.android.project.fooddelivery.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.database.FoodDatabase;
import com.prof.reda.android.project.fooddelivery.databinding.ActivityDetailMenuBinding;
import com.prof.reda.android.project.fooddelivery.models.EntityOrder;
import com.prof.reda.android.project.fooddelivery.utils.AppExecutors;
import com.prof.reda.android.project.fooddelivery.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailMenuActivity extends AppCompatActivity {

    private ActivityDetailMenuBinding binding;
    private String pic;
    private String name;
    private int id;
    private String description;
    private String price;
    private FoodDatabase mDB;
    private EntityOrder order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_menu);

        mDB = FoodDatabase.getInstance(this);

        getInfoFromIntent();

        getPicture(binding.imgMealDetail);
        binding.foodName.setText(name);
        order = new EntityOrder();



        binding.addToCardBtn.setOnClickListener(view -> {
            order.setId(id);
            order.setImage(pic);
            order.setFoodName(name);
            order.setRestroName("Vegan Resto");
            order.setPrice(Integer.parseInt(price));

            addToCart(order);
        });
    }

    private void addToCart(EntityOrder order){
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                mDB.foodDao().insertOrder(order);
            }
        });
    }

    private void getInfoFromIntent(){
        Intent intent = getIntent();
        pic = intent.getStringExtra("pic");
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        id = intent.getIntExtra("id", 0);

    }

    private void getPicture(ImageView imageView){
        StringRequest request = new StringRequest(Request.Method.GET, Constants.HOME + pic,
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
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

}