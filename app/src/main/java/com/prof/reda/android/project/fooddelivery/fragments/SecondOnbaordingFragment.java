package com.prof.reda.android.project.fooddelivery.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentSecondOnbaordingBinding;

public class SecondOnbaordingFragment extends Fragment {

    FragmentSecondOnbaordingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second_onbaording, container, false);

        binding.nextBtn.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_secondOnbaordingFragment_to_thirdOnboardingFragment2);
        });

        return binding.getRoot();
        // Inflate the layout for this fragment
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}