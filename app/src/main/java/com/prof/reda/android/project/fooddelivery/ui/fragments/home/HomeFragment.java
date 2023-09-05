package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.FoodAdapter;
import com.prof.reda.android.project.fooddelivery.adapters.RestroAdapter;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentHomeBinding;
import com.prof.reda.android.project.fooddelivery.models.Food;
import com.prof.reda.android.project.fooddelivery.models.Restro;
import com.prof.reda.android.project.fooddelivery.ui.activities.DetailMenuActivity;
import com.prof.reda.android.project.fooddelivery.ui.activities.DetailsProductActivity;
import com.prof.reda.android.project.fooddelivery.utils.Constants;
import com.prof.reda.android.project.fooddelivery.utils.OnClickFoodItemListener;
import com.prof.reda.android.project.fooddelivery.utils.OnClickRestaurantItemListener;
import com.prof.reda.android.project.fooddelivery.utils.VerifyConnection;
import com.prof.reda.android.project.fooddelivery.viewModel.FoodViewModel;
import com.prof.reda.android.project.fooddelivery.viewModel.FoodViewModelFactory;

import java.util.List;

public class HomeFragment extends Fragment implements OnClickRestaurantItemListener,
        OnClickFoodItemListener {
    private FragmentHomeBinding binding;
    private VerifyConnection verifyConnection;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container, false);

        FoodViewModelFactory factory = new FoodViewModelFactory(getActivity());
        FoodViewModel viewModel = new ViewModelProvider(this, factory).get(FoodViewModel.class);

        verifyConnection = new VerifyConnection(getActivity());
        if (!verifyConnection.isConnected()){
            binding.noInternetLayout.setVisibility(View.VISIBLE);
            binding.scrollView.setVisibility(View.GONE);

        }else {
            binding.noInternetLayout.setVisibility(View.GONE);
            binding.scrollView.setVisibility(View.VISIBLE);

            viewModel.getFood().observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
                @Override
                public void onChanged(List<Food> foodList) {
                    prepareFoodRV(foodList);
                }
            });

            viewModel.getRestaurant().observe(getViewLifecycleOwner(), new Observer<List<Restro>>() {
                @Override
                public void onChanged(List<Restro> restroList) {
                    prepareRestaurantRV(restroList);
                }
            });
        }


        //button view more Restaurant
        binding.viewMoreRestroTV.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_popularRestroFragment);
        });

        //button view more menu
        binding.viewMoreMenuTV.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_popularMenuFragment);
        });

        // button filter
        binding.filterBtn.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_filterFragment);
        });

        // notification button
        binding.iconBtn.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_notificationFragment);
        });


        return binding.getRoot();
    }

    private void prepareRestaurantRV(List<Restro> restroList){

        binding.rvNearestRestaurant.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL
        , false));

        binding.rvNearestRestaurant.setHasFixedSize(true);
        binding.rvNearestRestaurant.setItemAnimator(new DefaultItemAnimator());
        RestroAdapter restroAdapter = new RestroAdapter(restroList, this);
        binding.rvNearestRestaurant.setAdapter(restroAdapter);
    }

    private void prepareFoodRV(List<Food> foodList){
        binding.rvPopularMenu.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));

        binding.rvPopularMenu.setHasFixedSize(true);
        binding.rvPopularMenu.setItemAnimator(new DefaultItemAnimator());
        FoodAdapter foodAdapter = new FoodAdapter(getActivity(), foodList, this);
        binding.rvPopularMenu.setAdapter(foodAdapter);

    }
    @Override
    public void onClickFoodItem(Food food) {
        Intent intent = new Intent(getActivity(),DetailMenuActivity.class);

        intent.putExtra(Constants.KEY_IMAGE, food.getImage());
        intent.putExtra(Constants.KEY_FOOD_NAME, food.getFoodName());
        intent.putExtra(Constants.KEY_PRICE, food.getPrice());
        intent.putExtra(Constants.KEY_RESTRO_NAME, food.getRestroName());

        startActivity(intent);
    }

    @Override
    public void onClickRestaurantItem(Restro restro) {
        Intent intent = new Intent(getActivity(), DetailsProductActivity.class);

        intent.putExtra(Constants.KEY_RESTRO_NAME, restro.getName());

        startActivity(intent);
    }
}