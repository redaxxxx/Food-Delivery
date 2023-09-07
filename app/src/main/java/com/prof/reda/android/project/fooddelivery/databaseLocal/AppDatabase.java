package com.prof.reda.android.project.fooddelivery.databaseLocal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.prof.reda.android.project.fooddelivery.models.OrderEntity;

@Database(entities = {OrderEntity.class}, version = 1)
public abstract class AppDatabase extends  RoomDatabase{
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "order.db";
    private static AppDatabase mInstance;

    public static AppDatabase getInstance(Context context){
        if (mInstance != null){
            synchronized (LOCK){
                mInstance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return mInstance;
    }

    public abstract OrderDao orderDao();
}
