package com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentResetPasswordSuccessfulBinding;
import com.prof.reda.android.project.fooddelivery.ui.activities.LoginActivity;

public class ResetPasswordSuccessfulFragment extends Fragment {

    private FragmentResetPasswordSuccessfulBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reset_password_successful, container, false);

        binding.backBtn.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });

        return binding.getRoot();
    }
}