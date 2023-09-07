package com.prof.reda.android.project.fooddelivery.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.prof.reda.android.project.fooddelivery.databaseLocal.AppDatabase;

public class OrderViewModelFactory implements ViewModelProvider.Factory {

    private AppDatabase database;
    public OrderViewModelFactory(AppDatabase database){
        this.database = database;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new OrderViewModel(database);
    }
}
