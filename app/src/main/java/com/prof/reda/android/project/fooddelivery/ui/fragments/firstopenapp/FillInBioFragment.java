package com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentFillInBioBinding;


public class FillInBioFragment extends Fragment {
    private FragmentFillInBioBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fill_in_bio, container, false);


        binding.nextButton.setOnClickListener(view -> {
            if (isValide()){
                completeRegister();
            }
        });
        return binding.getRoot();
    }

    private void completeRegister(){

    }

    private boolean isValide(){
        if (binding.phoneNumberEditText.getText().toString() == null){
            binding.phoneNumberEditText.setError("phone number required");
        } else if (!binding.phoneNumberEditText.getText().toString().matches("639[0-9]{9}")) {
            binding.phoneNumberEditText.setError("phone number not valid");
        }

        if (TextUtils.isEmpty(binding.firstNameEditText.getText().toString())){
            binding.firstNameEditText.setError("first name is required");
        }

        if (TextUtils.isEmpty(binding.lastNameEditText.getText().toString())){
            binding.lastNameEditText.setError("last name is required");
        }

        return true;
    }
}