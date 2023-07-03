package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.chip.Chip;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.RestroAdapter;
import com.prof.reda.android.project.fooddelivery.database.FoodDatabase;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentPopularRestaurantBinding;
import com.prof.reda.android.project.fooddelivery.models.Food;
import com.prof.reda.android.project.fooddelivery.models.Restro;
import com.prof.reda.android.project.fooddelivery.ui.activities.DetailsProductActivity;
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

public class PopularRestaurantFragment extends Fragment implements RestroAdapter.OnClickItemListener {

    private FragmentPopularRestaurantBinding binding;
    private ArrayList<Restro> restroList;
    private RestroAdapter restroAdapter;
    private SharedPreferences sharedPreferences;
    private String token;
    private ArrayList<String> resultsList;
    private Bundle bundle;

    private FoodViewModel viewModel;
    private FoodDatabase mDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_popular_restaurant, container, false);

        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        token = sharedPreferences.getString("token", null);

        mDB = FoodDatabase.getInstance(getContext());
        FoodViewModelFactory factory = new FoodViewModelFactory(mDB);
        viewModel = new ViewModelProvider(this, factory).get(FoodViewModel.class);

        viewModel.getRestaurant(getContext(), token).observe(getViewLifecycleOwner(), new Observer<List<Restro>>() {
            @Override
            public void onChanged(List<Restro> restroList) {
                bundle = getArguments();
                if (bundle != null){
                    resultsList = bundle.getStringArrayList("selectedChipData");
                    if (bundle.getBoolean("isFiltering")) {
                        addChip(getActivity());
                        prepareRvRestaurantAfterFiltering();

                    }
                } else {
                    prepareRestaurantRV(restroList);
                }
            }
        });



        return binding.getRoot();
    }

    // Add chips of filtering
    private void addChip(Context context) {
        binding.restroChipGroupFilter.removeAllViews();
        binding.restroChipGroupFilter.setVisibility(View.VISIBLE);
        int index = 1;
        for (String label : resultsList) {
            Chip chip = new Chip(context);
            chip.setId(index);
            chip.setText(label);
            chip.setTextColor(getResources().getColor(R.color.orange));
            chip.setCheckedIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.baseline_close_24, null));
            chip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.light_orange)));
            chip.setCheckable(true);
            chip.setChecked(true);

            chip.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if (!isChecked) {
                    binding.restroChipGroupFilter.removeView(chip);
                }
            });

            binding.restroChipGroupFilter.addView(chip);
            index++;
        }
        binding.restroChipGroupFilter.invalidate();
    }

    //prepare recycler view for restaurant after filtering
    private void prepareRvRestaurantAfterFiltering() {
        binding.rvPopularRestro.setLayoutManager(new GridLayoutManager(getActivity(), 2,
                GridLayoutManager.VERTICAL, false));
        binding.rvPopularRestro.setHasFixedSize(true);
        binding.rvPopularRestro.setItemAnimator(new DefaultItemAnimator());
        filterRestaurantWithName();
    }

    //prepare recycler view of all restaurants
    private void prepareRestaurantRV(List<Restro> restroList) {
        binding.rvPopularRestro.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));

        binding.rvPopularRestro.setHasFixedSize(true);
        binding.rvPopularRestro.setItemAnimator(new DefaultItemAnimator());

        restroAdapter = new RestroAdapter(getContext(), restroList, this);
        binding.rvPopularRestro.setAdapter(restroAdapter);

    }

    // fetch data of each restaurant when filtering from laravel api
    private void filterRestaurantWithName() {
        ArrayList<Restro> filteringResults = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, Constants.FILTER_FOOD + "/restaurant",
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getBoolean("status")) {
                            JSONArray array = new JSONArray(object.getString("data"));
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject restroWithName = array.getJSONObject(i);
                                Restro restro = new Restro();
                                restro.setId(restroWithName.getInt("id"));
                                restro.setName(restroWithName.getString("name"));
                                restro.setPic(restroWithName.getString("pic"));

                                Log.i(Constants.TAG, "name is: " + restroWithName.getString("name"));
                                filteringResults.add(restro);
                            }
                            restroAdapter = new RestroAdapter(getContext(), filteringResults, this);
                            binding.rvPopularRestro.setAdapter(restroAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            error.printStackTrace();
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + token);
                map.put("Content-Type", "text/plain");
                map.put("Accept", "application/json");
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(request);
    }

    @Override
    public void onClickRestroItem(Restro restaurants) {

        Intent intent = new Intent(getActivity(), DetailsProductActivity.class);
        startActivity(intent);
    }
}