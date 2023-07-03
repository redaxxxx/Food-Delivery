package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.google.android.material.chip.Chip;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.FoodAdapter;
import com.prof.reda.android.project.fooddelivery.database.FoodDatabase;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentPopularMenuBinding;
import com.prof.reda.android.project.fooddelivery.models.Food;
import com.prof.reda.android.project.fooddelivery.models.Restro;
import com.prof.reda.android.project.fooddelivery.ui.activities.DetailMenuActivity;
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

public class PopularMenuFragment extends Fragment implements FoodAdapter.OnItemClickListener{

    private FragmentPopularMenuBinding binding;
    private List<Food> foodList;
    private FoodAdapter foodAdapter;
    private SharedPreferences sharedPreferences;
    private String token;
    private ArrayList<String> resultsList;
    private FoodViewModel viewModel;
    private FoodDatabase mDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_popular_menu, container, false);

        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

        mDB = FoodDatabase.getInstance(getContext());

        FoodViewModelFactory factory = new FoodViewModelFactory(mDB);
        viewModel = new ViewModelProvider(this, factory).get(FoodViewModel.class);

        viewModel.getFood(getContext(),token).observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foodList) {
                Bundle bundle = getArguments();
                if (bundle != null){
                    resultsList = bundle.getStringArrayList("selectedChipData");
                    if (bundle.getBoolean("isFiltering")){
                        addChip(getActivity());
                        prepareMenuRVAfterFiltering();
                    }
                }else {
                    prepareMenuRV(foodList);
                }
            }
    });



        return binding.getRoot();
    }

    private void prepareMenuRV(List<Food> foodList) {
        sharedPreferences = getContext().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        binding.rvPopularViewMoreMenu.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));

        binding.rvPopularViewMoreMenu.setHasFixedSize(true);
        binding.rvPopularViewMoreMenu.setItemAnimator(new DefaultItemAnimator());

        foodAdapter = new FoodAdapter(getContext(), foodList, this);
        binding.rvPopularViewMoreMenu.setAdapter(foodAdapter);

    }

    //prepare recycler view of all restaurants
    private void prepareMenuRVAfterFiltering() {
        sharedPreferences = getContext().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        binding.rvPopularViewMoreMenu.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));

        binding.rvPopularViewMoreMenu.setHasFixedSize(true);
        binding.rvPopularViewMoreMenu.setItemAnimator(new DefaultItemAnimator());
        filterWithFood();
    }

    // fetch data of each restaurant when filtering from laravel api
    private void filterWithFood(){
        ArrayList<Food> filteringResult = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, Constants.FILTER_FOOD + "food", response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("status")){
                    JSONArray array = new JSONArray(object.getString("data"));
                    for (int i = 0; i < array.length(); i++){
                        JSONObject filteringWithFood = array.getJSONObject(i);
                        Food food = new Food();
                        food.setId(filteringWithFood.getInt("id"));
                        food.setName(filteringWithFood.getString("name"));
                        food.setImage(filteringWithFood.getString("pic"));
                        food.setPrice(filteringWithFood.getString("price"));

                        filteringResult.add(food);
                    }
                    foodAdapter = new FoodAdapter(requireContext(), filteringResult, this);
                    binding.rvPopularViewMoreMenu.setAdapter(foodAdapter);
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
                map.put("Authorization", "Bearer " + token);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(request);
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
    public void onClickItem(Food food) {
        startActivity(new Intent(getActivity(), DetailMenuActivity.class));
    }
}