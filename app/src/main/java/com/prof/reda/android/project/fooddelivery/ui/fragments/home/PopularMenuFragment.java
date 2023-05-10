package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

import android.content.Intent;
import android.os.Bundle;

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
import com.prof.reda.android.project.fooddelivery.models.Menu;
import com.prof.reda.android.project.fooddelivery.ui.activities.DetailMenuActivity;

import java.util.ArrayList;
import java.util.List;

public class PopularMenuFragment extends Fragment implements PopularMenuAdapter.OnItemClickListener{

    private FragmentPopularMenuBinding binding;
    private List<Menu> menus;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_popular_menu, container, false);
        menus = new ArrayList<>();
        menus.add(new Menu(R.drawable.menu2, "Herbal Pancake", "Warung Herbal", 7));
        menus.add(new Menu(R.drawable.menu3, "Fruit Salad", "Wijie Resto", 5));
        menus.add(new Menu(R.drawable.menu1, "Green Noddle", "Noodle Home", 15));
        prepareMenuRV(menus);
        return binding.getRoot();
    }

    private void prepareMenuRV(List<Menu> menusList) {
        binding.rvPopularViewMoreMenu.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));

        binding.rvPopularViewMoreMenu.setHasFixedSize(true);
        binding.rvPopularViewMoreMenu.setItemAnimator(new DefaultItemAnimator());
        PopularMenuAdapter menuAdapter = new PopularMenuAdapter(menusList,this);
        binding.rvPopularViewMoreMenu.setAdapter(menuAdapter);
    }

    @Override
    public void onClickItem(Menu menu) {
        startActivity(new Intent(getActivity(), DetailMenuActivity.class));
    }
}