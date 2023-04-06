package com.prof.reda.android.project.fooddelivery.views.fragments.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.RestroAdapter;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentPopularRestaurantBinding;
import com.prof.reda.android.project.fooddelivery.models.Restaurants;
import com.prof.reda.android.project.fooddelivery.views.activities.DetailsProductActivity;

import java.util.ArrayList;
import java.util.List;

public class PopularRestaurantFragment extends Fragment implements RestroAdapter.OnClickItemListener{

    private FragmentPopularRestaurantBinding binding;
    private List<Restaurants> restaurants;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_popular_restaurant, container, false);

        restaurants = new ArrayList<>();
        restaurants.add(new Restaurants(R.drawable.restaurant1, "Vegan Resto", "12 Min"));
        restaurants.add(new Restaurants(R.drawable.restaurant2, "Healthy Food", "8 Min"));
        restaurants.add(new Restaurants(R.drawable.restaurant3, "Good Food", "12 Min"));
        restaurants.add(new Restaurants(R.drawable.restaurant4, "Smart Resto", "8 Min"));
        restaurants.add(new Restaurants(R.drawable.restaurant5, "Vegan Resto", "12 Min"));
        restaurants.add(new Restaurants(R.drawable.restaurant6, "Healthy Food", "8 Min"));

        prepareRestaurantRV(restaurants);
        return binding.getRoot();
    }

    private void prepareRestaurantRV(List<Restaurants> restaurantsList){
        binding.rvPopularRestro.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));

        binding.rvPopularRestro.setHasFixedSize(true);
        binding.rvPopularRestro.setItemAnimator(new DefaultItemAnimator());
        RestroAdapter restroAdapter = new RestroAdapter(restaurantsList, this);
        binding.rvPopularRestro.setAdapter(restroAdapter);
    }

    @Override
    public void onClickRestroItem(Restaurants restaurants) {

        Intent intent = new Intent(getActivity(), DetailsProductActivity.class);
        intent.putExtra(HomeFragment.RESTRO_NAME, restaurants.getRestroName());
        startActivity(intent);
    }
}