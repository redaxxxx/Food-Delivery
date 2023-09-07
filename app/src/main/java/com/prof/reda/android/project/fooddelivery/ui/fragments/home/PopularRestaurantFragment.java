package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.chip.Chip;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.RestroAdapter;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentPopularRestaurantBinding;
import com.prof.reda.android.project.fooddelivery.models.Restro;
import com.prof.reda.android.project.fooddelivery.ui.activities.DetailsProductActivity;
import com.prof.reda.android.project.fooddelivery.utils.Constants;
import com.prof.reda.android.project.fooddelivery.utils.OnClickRestaurantItemListener;
import com.prof.reda.android.project.fooddelivery.viewModel.FoodViewModel;
import com.prof.reda.android.project.fooddelivery.viewModel.FoodViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class PopularRestaurantFragment extends Fragment implements OnClickRestaurantItemListener {

    private FragmentPopularRestaurantBinding binding;
    private RestroAdapter restroAdapter;
    private SharedPreferences sharedPreferences;
    private ArrayList<String> resultsList;
    private Bundle bundle;

    private FirebaseFirestore firestore;
    private FoodViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_popular_restaurant, container, false);

        firestore = FirebaseFirestore.getInstance();

        FoodViewModelFactory factory = new FoodViewModelFactory(getActivity());
        viewModel = new ViewModelProvider(this, factory).get(FoodViewModel.class);

        viewModel.getRestaurant().observe(getViewLifecycleOwner(), new Observer<List<Restro>>() {
            @Override
            public void onChanged(List<Restro> restroList) {
                prepareRestaurantRV(restroList);
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
    }

    //prepare recycler view of all restaurants
    private void prepareRestaurantRV(List<Restro> restroList) {

        binding.rvPopularRestro.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));

        binding.rvPopularRestro.setHasFixedSize(true);
        binding.rvPopularRestro.setItemAnimator(new DefaultItemAnimator());

        restroAdapter = new RestroAdapter(restroList, this);
        binding.rvPopularRestro.setAdapter(restroAdapter);
    }
    @Override
    public void onClickRestaurantItem(Restro restro) {
        Intent intent = new Intent(getActivity(), DetailsProductActivity.class);

        intent.putExtra(Constants.KEY_RESTRO_NAME, restro.getName());

        startActivity(intent);
    }
}