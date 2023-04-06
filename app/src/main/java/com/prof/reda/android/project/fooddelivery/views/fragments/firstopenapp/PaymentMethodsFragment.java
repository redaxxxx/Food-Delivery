package com.prof.reda.android.project.fooddelivery.views.fragments.firstopenapp;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentPaymentMethodsBinding;

public class PaymentMethodsFragment extends Fragment {

    private FragmentPaymentMethodsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment_methods, container, false);

        binding.nextBtn.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_paymentMethodsFragment_to_uploadPhotoFragment);
        });
        return binding.getRoot();
    }
}