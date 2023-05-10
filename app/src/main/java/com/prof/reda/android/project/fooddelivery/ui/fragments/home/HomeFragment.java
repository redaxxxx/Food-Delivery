package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.PopularMenuAdapter;
import com.prof.reda.android.project.fooddelivery.adapters.RestroAdapter;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentHomeBinding;
import com.prof.reda.android.project.fooddelivery.models.Menu;
import com.prof.reda.android.project.fooddelivery.models.Restaurants;
import com.prof.reda.android.project.fooddelivery.ui.activities.DetailMenuActivity;
import com.prof.reda.android.project.fooddelivery.ui.activities.DetailsProductActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements RestroAdapter.OnClickItemListener,
        PopularMenuAdapter.OnItemClickListener{
    private FragmentHomeBinding binding;
    private RestroAdapter restroAdapter;
    private PopularMenuAdapter menuAdapter;
    private List<Restaurants> restaurants;
    private List<Menu> menus;

    public static final String RESTRO_NAME = "com.prof.reda.android.project.fooddelivery.views.fragments.home";


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
        menus.add(new Menu(R.drawable.menu1, "Herbal Pancake", "Warung Herbal", 7));
        menus.add(new Menu(R.drawable.menu2, "Fruit Salad", "Wijie Resto", 5));

        prepareRestaurantRV(restaurants);
        prepareMenuRV(menus);

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

    private void prepareRestaurantRV(List<Restaurants> restaurantsList){
        binding.rvNearestRestaurant.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL
        , false));

        binding.rvNearestRestaurant.setHasFixedSize(true);
        binding.rvNearestRestaurant.setItemAnimator(new DefaultItemAnimator());
        binding.rvNearestRestaurant.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        restroAdapter = new RestroAdapter(restaurantsList, this);
        binding.rvNearestRestaurant.setAdapter(restroAdapter);
    }

    private void prepareMenuRV(List<Menu> menusList){
        binding.rvPopularMenu.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));

        binding.rvPopularMenu.setHasFixedSize(true);
        binding.rvPopularMenu.setItemAnimator(new DefaultItemAnimator());
        menuAdapter = new PopularMenuAdapter(menusList,this);
        binding.rvPopularMenu.setAdapter(menuAdapter);

    }

    @Override
    public void onClickRestroItem(Restaurants restaurants) {
        Intent intent = new Intent(getActivity(), DetailsProductActivity.class);
        intent.putExtra(RESTRO_NAME, restaurants.getRestroName());
        startActivity(intent);
    }

    @Override
    public void onClickItem(Menu menu) {
        startActivity(new Intent(getActivity(), DetailMenuActivity.class));
    }
}