package com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentPaymentMethodsBinding;
import com.prof.reda.android.project.fooddelivery.ui.activities.CompleteSignUpActivity;
import com.prof.reda.android.project.fooddelivery.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class PaymentMethodsFragment extends Fragment {

    private FragmentPaymentMethodsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment_methods, container, false);

        binding.nextBtn.setOnClickListener(view-> {
            startActivity(new Intent(getActivity(), CompleteSignUpActivity.class));
            getActivity().finish();
        });

        return binding.getRoot();
    }
}