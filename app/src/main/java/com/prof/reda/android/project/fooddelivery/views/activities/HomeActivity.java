package com.prof.reda.android.project.fooddelivery.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.navigation.NavigationBarView;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.ActivityHomeBinding;
import com.prof.reda.android.project.fooddelivery.views.fragments.home.CartFragment;
import com.prof.reda.android.project.fooddelivery.views.fragments.home.ChatFragment;
import com.prof.reda.android.project.fooddelivery.views.fragments.home.HomeFragment;
import com.prof.reda.android.project.fooddelivery.views.fragments.home.ProfileFragment;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    private final String[] menus ={"Home", "Profile","Cart","Chat"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        setupBottomView();

    }

    private void setupBottomView(){
        binding.bottomView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()){
                    case R.id.homeFragment:
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        return true;

                    case R.id.profile:
                        fragment = new ProfileFragment();
                        loadFragment(fragment);
                        return true;

                    case R.id.cartFragment:
                        fragment = new CartFragment();
                        loadFragment(fragment);
                        return true;

                    case R.id.chatFragment:
                        fragment = new ChatFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });
        BadgeDrawable badgeDrawable = binding.bottomView.getOrCreateBadge(R.id.cartFragment);
        badgeDrawable.isVisible();
        badgeDrawable.setNumber(7);

        BadgeDrawable badgeDrawable2 = binding.bottomView.getOrCreateBadge(R.id.chatFragment);
        badgeDrawable2.isVisible();
        badgeDrawable2.setNumber(5);
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.container.getId(), fragment).commitAllowingStateLoss();
    }

    public void onResume() {
        super.onResume();
        ActionBar supportActionBar = ((AppCompatActivity) this).getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ActionBar supportActionBar = ((AppCompatActivity) this).getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.hide();
    }

}