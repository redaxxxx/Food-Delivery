package com.prof.reda.android.project.fooddelivery.views.fragments.home;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentRateFoodBinding;

public class RateFoodFragment extends Fragment {
    private FragmentRateFoodBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rate_food, container, false);

        binding.ratingBar.setRating(3);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}