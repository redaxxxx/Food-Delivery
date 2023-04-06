package com.prof.reda.android.project.fooddelivery.views.fragments.home;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.FavoriteAdapter;
import com.prof.reda.android.project.fooddelivery.adapters.OrderAdapter;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentProfileBinding;
import com.prof.reda.android.project.fooddelivery.models.Menu;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private FavoriteAdapter favoriteAdapter;
    private List<Menu> menuList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        menuList.add(new Menu(R.drawable.orderimg2, "Spacy fresh crab", "Waroenk kita", 35));
        menuList.add(new Menu(R.drawable.orderimg1, "Spacy fresh crab", "Waroenk kita", 35));
        menuList.add(new Menu(R.drawable.orderimg3, "Spacy fresh crab", "Waroenk kita", 35));

        prepareRecyclerView(menuList);
        return binding.getRoot();
    }

    private void prepareRecyclerView(List<Menu> menus){
        binding.menusRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));

        binding.menusRv.setHasFixedSize(true);
        binding.menusRv.setItemAnimator(new DefaultItemAnimator());
        favoriteAdapter = new FavoriteAdapter(menus);
        binding.menusRv.setAdapter(favoriteAdapter);
    }
}