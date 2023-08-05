package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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
    private FirebaseFirestore db;
    private StorageReference foodRef;
    private List<Food> foods;
    private FoodAdapter foodAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container, false);

        db = FirebaseFirestore.getInstance();
        foodRef = FirebaseStorage.getInstance().getReference();

        prepareRestaurantRV();
        prepareFoodRV();

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

    private void prepareRestaurantRV(){
        List<Restro> restroList = Restro.createRestroList();

        binding.rvNearestRestaurant.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL
        , false));

        binding.rvNearestRestaurant.setHasFixedSize(true);
        binding.rvNearestRestaurant.setItemAnimator(new DefaultItemAnimator());
        binding.rvNearestRestaurant.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        RestroAdapter restroAdapter = new RestroAdapter(restroList, this);
        binding.rvNearestRestaurant.setAdapter(restroAdapter);
    }

    private void prepareFoodRV(){
        binding.rvPopularMenu.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));

        binding.rvPopularMenu.setHasFixedSize(true);
        binding.rvPopularMenu.setItemAnimator(new DefaultItemAnimator());

        foods = Food.createFoodList();
        foodAdapter = new FoodAdapter(foods, this);
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
        intent.putExtra("foodName", food.getFoodName());
        intent.putExtra("price", food.getPrice());
//        intent.putExtra("id", food.getId());
        intent.putExtra("restroName", food.getRestroName());
        startActivity(intent);
    }
}