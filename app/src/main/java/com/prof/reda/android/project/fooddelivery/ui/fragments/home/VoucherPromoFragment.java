package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentVoucherPromoBinding;

public class VoucherPromoFragment extends Fragment {

    private FragmentVoucherPromoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_voucher_promo, container, false);

        binding.checkoutBtn.setOnClickListener(view -> {

        });
        return binding.getRoot();
    }
}