package com.prof.reda.android.project.fooddelivery.views.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentPaymentsBinding;

public class PaymentsFragment extends Fragment {

    private FragmentPaymentsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payments, container, false);

        binding.editAddressButton.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_fragmentPayments_to_editLocationFragment);
        });

        binding.editPaymentsButton.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_fragmentPayments_to_editPaymentFragment);
        });

        Bundle bundle = getArguments();
        if (bundle != null){
            binding.paymentImgView.setImageResource(bundle.getInt(EditPaymentFragment.LOGO_IMG_VIEW));
        }

        binding.arrowBackBtn.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_fragmentPayments_to_orderDetailsFragment);
        });




        return binding.getRoot();
    }
}
