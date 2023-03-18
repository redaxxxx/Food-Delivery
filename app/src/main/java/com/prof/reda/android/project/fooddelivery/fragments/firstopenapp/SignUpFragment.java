package com.prof.reda.android.project.fooddelivery.fragments.firstopenapp;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentSignUpBinding;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);

        binding.alreadyHaveAccount.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_signInFragment);
        });

        binding.createBtn.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_fillInBioFragment);
        });
        return binding.getRoot();
    }

    private void createAccountWithFirebase() {

    }
}