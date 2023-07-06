package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentEditPaymentBinding;

public class EditPaymentFragment extends Fragment {

    private FragmentEditPaymentBinding binding;
    public static final String LOGO_IMG_VIEW = "com.prof.reda.android.project.fooddelivery.views.fragments.home";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_payment, container, false);

        binding.paypalCardView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(LOGO_IMG_VIEW, R.drawable.paypallogo);
            Navigation.findNavController(view).navigate(R.id.action_editPaymentFragment_to_fragmentPayments, bundle);
        });

        binding.visaCardView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(LOGO_IMG_VIEW, R.drawable.visalogo);
            Navigation.findNavController(view).navigate(R.id.action_editPaymentFragment_to_fragmentPayments, bundle);
        });

        binding.payoneerCardView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(LOGO_IMG_VIEW, R.drawable.payoneerlogo);
            Navigation.findNavController(view).navigate(R.id.action_editPaymentFragment_to_fragmentPayments, bundle);
        });

        binding.arrowBackBtn.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_editPaymentFragment_to_fragmentPayments);
        });
        return binding.getRoot();
    }
}