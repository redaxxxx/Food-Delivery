package com.prof.reda.android.project.fooddelivery.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.ActivityFilterBinding;

public class FilterActivity extends AppCompatActivity {
    private ActivityFilterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter);

        binding.restroChip.setOnClickListener(view -> {
            binding.restroChip.setChecked(true);
        });
    }
}