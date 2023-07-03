package com.prof.reda.android.project.fooddelivery.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.prof.reda.android.project.fooddelivery.models.Order;

@Dao
public interface FoodDao {

    @Query("SELECT * FROM orderInfo")
    LiveData<Order> getAllOrder();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrder(Order order);

    @Delete
    void deleteOrder(Order order);

}
