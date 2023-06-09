package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentFilterBinding;
import java.util.ArrayList;


public class FilterFragment extends Fragment {

    private FragmentFilterBinding binding;
    private ArrayList<String> selectedChipData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter, container, false);

        selectedChipData = new ArrayList<>();

        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    selectedChipData.add(compoundButton.getText().toString());
                }else {
                    selectedChipData.remove(compoundButton.getText().toString());
                }
            }
        };

        binding.restroChip.setOnCheckedChangeListener(checkedChangeListener);
        binding.menuChip.setOnCheckedChangeListener(checkedChangeListener);
        binding.locationChip1.setOnCheckedChangeListener(checkedChangeListener);
        binding.locationChipMoreThan1.setOnCheckedChangeListener(checkedChangeListener);
        binding.locationChipLessThan1.setOnCheckedChangeListener(checkedChangeListener);
        binding.cakeChip.setOnCheckedChangeListener(checkedChangeListener);
        binding.soupChip.setOnCheckedChangeListener(checkedChangeListener);
        binding.mainCourseChip.setOnCheckedChangeListener(checkedChangeListener);
        binding.appetizerChip.setOnCheckedChangeListener(checkedChangeListener);
        binding.dessertChip.setOnCheckedChangeListener(checkedChangeListener);

        binding.searchBtn.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("selectedChipData", selectedChipData);
            bundle.putBoolean("isFiltering", true);

            if (selectedChipData.contains("Restaurants")){
                Navigation.findNavController(view).navigate(R.id.action_filterFragment_to_popularRestroFragment, bundle);
            }else {
                Navigation.findNavController(view).navigate(R.id.action_filterFragment_to_popularMenuFragment, bundle);
            }

        });

        return binding.getRoot();
    }


}