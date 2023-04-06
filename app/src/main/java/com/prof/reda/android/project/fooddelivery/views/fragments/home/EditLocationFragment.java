package com.prof.reda.android.project.fooddelivery.views.fragments.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentEditLocationBinding;
import com.prof.reda.android.project.fooddelivery.views.activities.MapActivity;

public class EditLocationFragment extends Fragment {

    private static final String LOCATION = "com.prof.reda.android.project.fooddelivery.views.fragments.home";
    private FragmentEditLocationBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_location, container, false);

        binding.setLocationBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), MapActivity.class);
            startActivity(intent);
        });
        return binding.getRoot();
    }
}