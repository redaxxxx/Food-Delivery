package com.prof.reda.android.project.fooddelivery.views.fragments.firstopenapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentSignUpSuccessBinding;
import com.prof.reda.android.project.fooddelivery.views.activities.HomeActivity;


public class SignUpSuccessFragment extends Fragment {

    private FragmentSignUpSuccessBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_success, container, false);

        binding.tryOrderBtn.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), HomeActivity.class));
        });

        return binding.getRoot();
    }
}