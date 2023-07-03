package com.prof.reda.android.project.fooddelivery.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.prof.reda.android.project.fooddelivery.models.Order;

@Database(entities = {Order.class}, version = 1, exportSchema = false)
public abstract class FoodDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "food.db";
    private static FoodDatabase mInstance;

    public static FoodDatabase getInstance(Context context){
        if (mInstance == null){
            synchronized (LOCK){
                mInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        FoodDatabase.class,
                        DATABASE_NAME
                ).fallbackToDestructiveMigration().build();
            }
        }
        return mInstance;
    }

    public abstract FoodDao foodDao();
}
