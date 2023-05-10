package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentFilterBinding;

import java.util.ArrayList;
import java.util.List;


public class FilterFragment extends Fragment {

    private FragmentFilterBinding binding;
    private List<String> selectedChipData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter, container, false);




//

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedChipData = new ArrayList<>();

        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    selectedChipData.add(compoundButton.getText().toString());
                } else {
                    selectedChipData.add(compoundButton.getText().toString());
                }
            }
        };

        binding.RestroChip.setOnCheckedChangeListener(checkedChangeListener);
        binding.menuChip.setOnCheckedChangeListener(checkedChangeListener);
        binding.locationChip1.setOnCheckedChangeListener(checkedChangeListener);
        binding.locationChipLessThan1.setOnCheckedChangeListener(checkedChangeListener);
        binding.locationChipMoreThan1.setOnCheckedChangeListener(checkedChangeListener);
        binding.cakeChip.setOnCheckedChangeListener(checkedChangeListener);
        binding.soupChip.setOnCheckedChangeListener(checkedChangeListener);
        binding.mainCourseChip.setOnCheckedChangeListener(checkedChangeListener);
        binding.appetizerChip.setOnCheckedChangeListener(checkedChangeListener);
        binding.dessertChip.setOnCheckedChangeListener(checkedChangeListener);
    }

}