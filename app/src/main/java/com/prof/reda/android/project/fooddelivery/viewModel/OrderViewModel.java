package com.prof.reda.android.project.fooddelivery.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.prof.reda.android.project.fooddelivery.databaseLocal.AppDatabase;
import com.prof.reda.android.project.fooddelivery.models.OrderEntity;

public class OrderViewModel extends ViewModel {
    private AppDatabase database;
    private MutableLiveData<OrderEntity> orderEntityMutableLiveData = new MutableLiveData<>();
    public OrderViewModel(AppDatabase database){
        this.database = database;
    }
    public LiveData<OrderEntity> queryOrderById(String orderId){
        return database.orderDao().queryOrderById(orderId);
    }
}
