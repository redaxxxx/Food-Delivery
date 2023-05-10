package com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentUploadPhotoBinding;

public class UploadPhotoFragment extends Fragment {

    private FragmentUploadPhotoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upload_photo, container, false);

        binding.nextBtn.setOnClickListener(view -> {
        });
        return binding.getRoot();
    }
}