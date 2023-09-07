package com.prof.reda.android.project.fooddelivery.databaseLocal;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.prof.reda.android.project.fooddelivery.models.OrderEntity;

@androidx.room.Dao
public interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrder(OrderEntity orderEntity);

    @Query("SELECT * FROM orderInfo WHERE orderId=:orderId")
    LiveData<OrderEntity> queryOrderById(String orderId);
}
