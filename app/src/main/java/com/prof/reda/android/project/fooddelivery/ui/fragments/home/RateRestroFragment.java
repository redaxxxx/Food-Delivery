package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentRateRestroBinding;

public class RateRestroFragment extends Fragment {
    private FragmentRateRestroBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rate_restro, container, false);


        binding.sumbitBtn.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_rateRestroFragment_to_voucherPromoFragment);
        });

        binding.ratingBar.setRating(3);
        //change color of stars to gold color
        LayerDrawable stars = (LayerDrawable) binding.ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(ContextCompat.getColor(getActivity(), R.color.gold),
                PorterDuff.Mode.SRC_ATOP);

        return binding.getRoot();
    }
}