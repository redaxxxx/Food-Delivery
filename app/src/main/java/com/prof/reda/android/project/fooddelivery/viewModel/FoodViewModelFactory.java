package com.prof.reda.android.project.fooddelivery.viewModel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FoodViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public FoodViewModelFactory(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FoodViewModel(context);
    }
}
