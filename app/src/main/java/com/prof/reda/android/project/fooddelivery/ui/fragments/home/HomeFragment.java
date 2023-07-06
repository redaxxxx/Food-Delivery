package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.FoodAdapter;
import com.prof.reda.android.project.fooddelivery.adapters.RestroAdapter;
import com.prof.reda.android.project.fooddelivery.database.FoodDatabase;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentHomeBinding;
import com.prof.reda.android.project.fooddelivery.models.Food;
import com.prof.reda.android.project.fooddelivery.models.Restaurants;
import com.prof.reda.android.project.fooddelivery.models.Restro;
import com.prof.reda.android.project.fooddelivery.ui.activities.DetailMenuActivity;
import com.prof.reda.android.project.fooddelivery.ui.activities.DetailsProductActivity;
import com.prof.reda.android.project.fooddelivery.ui.activities.FilterActivity;
import com.prof.reda.android.project.fooddelivery.utils.Constants;
import com.prof.reda.android.project.fooddelivery.viewModel.FoodViewModel;
import com.prof.reda.android.project.fooddelivery.viewModel.FoodViewModelFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment implements RestroAdapter.OnClickItemListener,
        FoodAdapter.OnItemClickListener{
    private FragmentHomeBinding binding;
    private RestroAdapter restroAdapter;
    private FoodAdapter foodAdapter;
    private List<Restro> restroList;
    private List<Food> foodList;
    private SharedPreferences sharedPreferences;
    private String token;

    public static final String RESTRO_NAME = "com.prof.reda.android.project.fooddelivery.views.fragments.home";

    private FoodViewModel viewModel;

    private FoodDatabase mDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container, false);

        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        token = sharedPreferences.getString("token", null);

        mDB = FoodDatabase.getInstance(getContext());

        FoodViewModelFactory factory = new FoodViewModelFactory(mDB);
        viewModel = new ViewModelProvider(this, factory).get(FoodViewModel.class);

        viewModel.getFood(getContext(), token).observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                prepareFoodRV(foods);
            }
        });

        viewModel.getRestaurant(getContext(), token).observe(getViewLifecycleOwner(), new Observer<List<Restro>>() {
            @Override
            public void onChanged(List<Restro> restroList) {
                prepareRestaurantRV(restroList);
            }
        });

        //button view more Restaurant
        binding.viewMoreRestroTV.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_popularRestroFragment);
        });

        //button view more menu
        binding.viewMoreMenuTV.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_popularMenuFragment);
        });

        // button filter
        binding.filterBtn.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_filterFragment);
        });

        // notification button
        binding.iconBtn.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_notificationFragment);
        });


        return binding.getRoot();
    }

    private void prepareRestaurantRV(List<Restro> restroList){
        binding.rvNearestRestaurant.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL
        , false));

        binding.rvNearestRestaurant.setHasFixedSize(true);
        binding.rvNearestRestaurant.setItemAnimator(new DefaultItemAnimator());
        binding.rvNearestRestaurant.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        restroAdapter = new RestroAdapter(getContext(), restroList, this);
        binding.rvNearestRestaurant.setAdapter(restroAdapter);
    }

    private void prepareFoodRV(List<Food> foodList){
        sharedPreferences = getContext().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        binding.rvPopularMenu.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));

        binding.rvPopularMenu.setHasFixedSize(true);
        binding.rvPopularMenu.setItemAnimator(new DefaultItemAnimator());

        foodAdapter = new FoodAdapter(getContext(), foodList, this);
        binding.rvPopularMenu.setAdapter(foodAdapter);
    }

    @Override
    public void onClickRestroItem(Restro restro) {
        Intent intent = new Intent(getActivity(), DetailsProductActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClickItem(Food food) {
        Intent intent = new Intent(getActivity(),DetailMenuActivity.class);
        intent.putExtra("pic", food.getImage());
        intent.putExtra("name", food.getName());
        intent.putExtra("price", food.getPrice());
        intent.putExtra("id", food.getId());
        startActivity(intent);
    }
}