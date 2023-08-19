package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.FoodAdapter;
import com.prof.reda.android.project.fooddelivery.database.FoodDatabase;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentPopularMenuBinding;
import com.prof.reda.android.project.fooddelivery.models.Food;
import com.prof.reda.android.project.fooddelivery.models.Restro;
import com.prof.reda.android.project.fooddelivery.ui.activities.DetailMenuActivity;
import com.prof.reda.android.project.fooddelivery.utils.Constants;
import com.prof.reda.android.project.fooddelivery.utils.OnClickFoodItemListener;
import com.prof.reda.android.project.fooddelivery.viewModel.FoodViewModel;
import com.prof.reda.android.project.fooddelivery.viewModel.FoodViewModelFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopularMenuFragment extends Fragment implements OnClickFoodItemListener {

    private FragmentPopularMenuBinding binding;
    private ArrayList<String> resultsList;
    private FirebaseFirestore db;
    private StorageReference foodRef;
    private FoodViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_popular_menu, container, false);

        FoodViewModelFactory factory = new FoodViewModelFactory(getActivity());
        viewModel = new ViewModelProvider(this, factory).get(FoodViewModel.class);

        viewModel.getFood().observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foodList) {
                prepareMenuRV(foodList);
            }
        });

        db = FirebaseFirestore.getInstance();
        foodRef = FirebaseStorage.getInstance().getReference();


        return binding.getRoot();
    }

    private void prepareMenuRV(List<Food> foodList) {

        binding.rvPopularViewMoreMenu.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));

        binding.rvPopularViewMoreMenu.setHasFixedSize(true);
        binding.rvPopularViewMoreMenu.setItemAnimator(new DefaultItemAnimator());
        FoodAdapter foodAdapter = new FoodAdapter(foodList, this);
        binding.rvPopularViewMoreMenu.setAdapter(foodAdapter);





    }

    //prepare recycler view of all restaurants
    private void prepareMenuRVAfterFiltering() {

        binding.rvPopularViewMoreMenu.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));

        binding.rvPopularViewMoreMenu.setHasFixedSize(true);
        binding.rvPopularViewMoreMenu.setItemAnimator(new DefaultItemAnimator());
    }

    private void addChip(Context context){
        binding.foodChipGroupFilter.removeAllViews();
        binding.foodChipGroupFilter.setVisibility(View.VISIBLE);
        int index = 1;
        for (String label : resultsList){
            Chip chip = new Chip(context);
            chip.setId(index);
            chip.setText(label);
            chip.setTextColor(getResources().getColor(R.color.orange));
            chip.setCheckedIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.baseline_close_24, null));
            chip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.light_orange)));
            chip.setCheckable(true);
            chip.setChecked(true);

            chip.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if (!isChecked){
                    binding.foodChipGroupFilter.removeView(chip);
                }
            });

            binding.foodChipGroupFilter.addView(chip);
            index++;
        }
        binding.foodChipGroupFilter.invalidate();
    }
    @Override
    public void onClickFoodItem(Food food) {
        Intent intent = new Intent(getActivity(),DetailMenuActivity.class);
        intent.putExtra(Constants.KEY_IMAGE, food.getImage());
        intent.putExtra(Constants.KEY_FOOD_NAME, food.getFoodName());
        intent.putExtra(Constants.KEY_PRICE, food.getPrice());
        intent.putExtra(Constants.KEY_RESTRO_NAME, food.getRestroName());

        startActivity(intent);
    }
}