package com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentForgotPasswordBinding;
import com.prof.reda.android.project.fooddelivery.ui.activities.ResetActivity;
import com.prof.reda.android.project.fooddelivery.utils.Constants;

public class ForgotPasswordFragment extends Fragment {

    private FirebaseAuth auth;
    private FragmentForgotPasswordBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false);

        auth = FirebaseAuth.getInstance();

        binding.viaEmailCardView.setOnClickListener(view-> {
            resetPasswordViaEmail(auth.getCurrentUser().getEmail());
        });

        binding.viaSMSCardView.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), ResetActivity.class));
        });
        return binding.getRoot();
    }

    private void resetPasswordViaEmail(String email){
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.forgotPasswordFrame, new ResetPasswordSuccessfulFragment()).commit();
                }
            }
        });
    }

    private void resetPasswordViaSms(){
    }

}