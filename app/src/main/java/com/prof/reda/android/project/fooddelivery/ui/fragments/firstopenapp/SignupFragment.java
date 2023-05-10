package com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentSignUpBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupFragment extends Fragment {
    private FragmentSignUpBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);

        binding.createBtn.setOnClickListener(view -> {
            // validate fields first
            if (isValidate()){
                // do something

            }
        });

        binding.alreadyHaveAccount.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer,
                    new LoginFragment()).commit();
        });

        return binding.getRoot();
    }

    private boolean isValidate(){

        if (TextUtils.isEmpty(binding.emailEditText.getText().toString())){
            binding.emailEditText.setError("Email is required");
            return false;
        }
        if (TextUtils.isEmpty(binding.passwordEditText.getText().toString())){
            binding.passwordEditText.setError("Required at least 8 characters");
            return false;
        }
        if (TextUtils.isEmpty(binding.usernameEditText.getText().toString())){
            binding.emailEditText.setError("username is required");
        }
        return true;
    }

}
