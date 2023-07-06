package com.prof.reda.android.project.fooddelivery.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.prof.reda.android.project.fooddelivery.models.EntityOrder;

import java.util.List;

@Dao
public interface FoodDao {

    @Query("SELECT * FROM orderInfo")
    LiveData<List<EntityOrder>> getAllOrder();

    @Insert
    void insertOrder(EntityOrder order);

    @Delete
    void deleteOrder(EntityOrder order);

}
