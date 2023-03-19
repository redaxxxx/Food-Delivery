package com.prof.reda.android.project.fooddelivery.views.fragments.home;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.PopularMenuAdapter;
import com.prof.reda.android.project.fooddelivery.adapters.RestroAdapter;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentHomeBinding;
import com.prof.reda.android.project.fooddelivery.models.Restaurants;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private RestroAdapter restroAdapter;
    private PopularMenuAdapter menuAdapter;
    private List<Restaurants> restaurants;
    private List<Restaurants> menus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container, false);

        restaurants = new ArrayList<>();
        restaurants.add(new Restaurants(R.drawable.restaurant1, "Vegan Resto", "12 Min"));
        restaurants.add(new Restaurants(R.drawable.restaurant2, "Healthy Food", "8 Min"));
        restaurants.add(new Restaurants(R.drawable.restaurant3, "Good Food", "12 Min"));

        menus = new ArrayList<>();
        menus.add(new Restaurants(R.drawable.menu1, "Herbal Pancake", "Warung Herbal", 7));
        menus.add(new Restaurants(R.drawable.menu2, "Fruit Salad", "Wijie Resto", 5));

        prepareRestaurantRV(restaurants);
        prepareMenuRV(menus);
        return binding.getRoot();
    }

    private void prepareRestaurantRV(List<Restaurants> restaurantsList){
        binding.rvNearestRestaurant.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL
        , false));

        binding.rvNearestRestaurant.setHasFixedSize(true);
        binding.rvNearestRestaurant.setItemAnimator(new DefaultItemAnimator());
        binding.rvNearestRestaurant.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        restroAdapter = new RestroAdapter(restaurantsList);
        binding.rvNearestRestaurant.setAdapter(restroAdapter);
    }

    private void prepareMenuRV(List<Restaurants> menusList){
        binding.rvPopularMenu.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));

        binding.rvPopularMenu.setHasFixedSize(true);
        binding.rvPopularMenu.setItemAnimator(new DefaultItemAnimator());
        menuAdapter = new PopularMenuAdapter(menusList);
        binding.rvPopularMenu.setAdapter(menuAdapter);

    }
}