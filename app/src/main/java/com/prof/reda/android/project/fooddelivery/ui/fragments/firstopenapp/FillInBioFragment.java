package com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentFillInBioBinding;
import com.prof.reda.android.project.fooddelivery.utils.Constants;


public class FillInBioFragment extends Fragment {
    private FragmentFillInBioBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private String email;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fill_in_bio, container, false);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Bundle bundle = getArguments();

        binding.nextButton.setOnClickListener(view -> {
            if (isValidate()){
                if (bundle != null){
                    bundle.putString(Constants.KEY_FIRST_NAME, binding.firstNameEt.getText().toString());
                    bundle.putString(Constants.KEY_SECOND_NAME, binding.lastNameEt.getText().toString());
                    bundle.putString(Constants.KEY_PHONE_NUMBER, binding.phoneNumberEt.getText().toString());
                }

                Fragment fragment = new PaymentMethodsFragment();
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameAuthContainer, fragment).commit();

            }
        });
        return binding.getRoot();
    }

    private boolean isValidate(){
        if (binding.phoneNumberEt.getText() == null){
            binding.phoneNumberOutline.setError("phone number required");
            binding.phoneNumberOutline.setErrorEnabled(true);
        }

        if (TextUtils.isEmpty(binding.firstNameEt.getText())){
            binding.firstNameOutline.setError("first name is required");
            binding.firstNameOutline.setErrorEnabled(true);
        }

        if (TextUtils.isEmpty(binding.lastNameEt.getText())){
            binding.lastNameOutline.setError("last name is required");
            binding.lastNameOutline.setErrorEnabled(true);
        }

        binding.phoneNumberOutline.setErrorEnabled(false);
        binding.firstNameOutline.setErrorEnabled(false);
        binding.lastNameOutline.setErrorEnabled(false);
        return true;
    }
}