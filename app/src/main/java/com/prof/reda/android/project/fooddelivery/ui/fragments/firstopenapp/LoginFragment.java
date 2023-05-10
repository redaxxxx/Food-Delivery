package com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentLoginBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);


        binding.loginBtn.setOnClickListener(view -> {
            // validate fields first
            if (isValidate()){
                // do something
                login();

            }
        });


        return binding.getRoot();
    }

    private void login() {

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
        return true;
    }

}
