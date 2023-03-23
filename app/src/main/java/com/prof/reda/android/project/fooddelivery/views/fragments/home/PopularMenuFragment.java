package com.prof.reda.android.project.fooddelivery.views.fragments.home;

import android.os.Bundle;

import androidx.appcompat.view.menu.MenuAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.PopularMenuAdapter;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentPopularMenuBinding;
import com.prof.reda.android.project.fooddelivery.models.Restaurants;

import java.util.ArrayList;
import java.util.List;

public class PopularMenuFragment extends Fragment {

    private FragmentPopularMenuBinding binding;
    private List<Restaurants> menus;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_popular_menu, container, false);
        menus = new ArrayList<>();
        menus.add(new Restaurants(R.drawable.menu2, "Herbal Pancake", "Warung Herbal", 7));
        menus.add(new Restaurants(R.drawable.menu3, "Fruit Salad", "Wijie Resto", 5));
        menus.add(new Restaurants(R.drawable.menu1, "Green Noddle", "Noodle Home", 15));
        prepareMenuRV(menus);
        return binding.getRoot();
    }

    private void prepareMenuRV(List<Restaurants> menusList) {
        binding.rvPopularViewMoreMenu.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));

        binding.rvPopularViewMoreMenu.setHasFixedSize(true);
        binding.rvPopularViewMoreMenu.setItemAnimator(new DefaultItemAnimator());
        PopularMenuAdapter menuAdapter = new PopularMenuAdapter(menusList);
        binding.rvPopularViewMoreMenu.setAdapter(menuAdapter);
    }

}