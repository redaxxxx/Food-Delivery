package com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentResetBinding;

import org.w3c.dom.Text;

public class ResetFragment extends Fragment {

    private FragmentResetBinding binding;
    private FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reset, container, false);

        auth = FirebaseAuth.getInstance();

        binding.nextButton.setOnClickListener(view -> {
            if (isValidate()){
                resetPassword();
            }
        });

        return binding.getRoot();
    }

    private void resetPassword(){

    }

    private boolean isValidate(){
        if (TextUtils.isEmpty(binding.newPasswordEt.getText())){
            binding.newPasswordOutline.setError("Please Enter Password");
            binding.newPasswordOutline.setErrorEnabled(true);
            return false;
        }
        if (TextUtils.isEmpty(binding.confirmPasswordEt.getText())){
            binding.confirmPasswordOutline.setError("Please Confirm Password");
            binding.confirmPasswordOutline.setErrorEnabled(true);
            return false;

        }
        if (binding.confirmPasswordEt.getText() != binding.newPasswordEt.getText()){
            binding.confirmPasswordOutline.setError("Password dosn't matching");
            binding.confirmPasswordOutline.setErrorEnabled(true);
            return false;
        }

        return true;
    }
}