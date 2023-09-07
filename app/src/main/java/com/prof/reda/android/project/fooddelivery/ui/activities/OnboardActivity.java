package com.prof.reda.android.project.fooddelivery.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.ViewPagerAdapter;
import com.prof.reda.android.project.fooddelivery.databinding.ActivityOnboardBinding;

public class OnboardActivity extends AppCompatActivity {

    private ActivityOnboardBinding binding;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboard);

        init();
    }

    private void init(){
        viewPagerAdapter = new ViewPagerAdapter(this);
        binding.viewPager.addOnPageChangeListener(listener); // create this listener
        binding.viewPager.setAdapter(viewPagerAdapter);

        binding.nextButton.setOnClickListener(view -> {

            if (binding.viewPager.getCurrentItem() == 0){
                binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1);
            }else {
                startActivity(new Intent(this, AuthActivity.class));
                finish();
            }
        });
    }

    private final ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position == 0){
                binding.nextButton.setVisibility(View.VISIBLE);
                binding.nextButton.setEnabled(true);
                binding.nextButton.setText("Next");
            }else {
                binding.nextButton.setVisibility(View.VISIBLE);
                binding.nextButton.setEnabled(true);
                binding.nextButton.setText("Next");
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}