package com.prof.reda.android.project.fooddelivery.views.fragments.home;

import android.os.Bundle;

import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentFinishOrderBinding;

public class FinishOrderFragment extends Fragment {
    private FragmentFinishOrderBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_finish_order, container, false);

        binding.ratingBar.setRating(3);

        binding.sumbitBtn.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_finishOrderFragment_to_rateFoodFragment);
        });
        return binding.getRoot();
    }
}