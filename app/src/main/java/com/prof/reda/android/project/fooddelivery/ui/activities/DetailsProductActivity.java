package com.prof.reda.android.project.fooddelivery.ui.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.DetailsProductAdapter;
import com.prof.reda.android.project.fooddelivery.databinding.ActivityDetailsProductBinding;
import com.prof.reda.android.project.fooddelivery.models.Menu;
import com.prof.reda.android.project.fooddelivery.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class DetailsProductActivity extends AppCompatActivity {

    private ActivityDetailsProductBinding binding;
    private final List<Menu> menuList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(DetailsProductActivity.this, R.layout.activity_details_product);

        menuList.add(new Menu(R.drawable.menu4, "Spacy fresh crab", 12));
        menuList.add(new Menu(R.drawable.menu5, "Spacy fresh crab", 16));

        binding.restaurantNameInDetail.setText(getIntent().getStringExtra(Constants.KEY_RESTRO_NAME));

        prepareDetailsProductRecyclerview(menuList);
    }

    private void prepareDetailsProductRecyclerview(List<Menu> menus){
        binding.detailsProductRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        binding.detailsProductRv.setHasFixedSize(true);
        binding.detailsProductRv.setItemAnimator(new DefaultItemAnimator());
        DetailsProductAdapter productAdapter = new DetailsProductAdapter(menus);
        binding.detailsProductRv.setAdapter(productAdapter);
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