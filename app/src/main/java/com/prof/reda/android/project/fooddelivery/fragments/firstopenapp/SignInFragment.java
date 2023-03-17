package com.prof.reda.android.project.fooddelivery.fragments.firstopenapp;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentSignInBinding;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentSignUpBinding;

public class SignInFragment extends Fragment {
    private FragmentSignInBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in,container, false);

        return binding.getRoot();
    }
}