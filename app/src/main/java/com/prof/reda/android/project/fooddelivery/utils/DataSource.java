package com.prof.reda.android.project.fooddelivery.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class DataSource {

    private final SharedPreferences sharedPreferences;
    public DataSource(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;

    }

    public void loggingState(boolean isLogging){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.PREFS_IS_LOGGING, isLogging);
        editor.apply();
    }

    public void firstTimeOpen(boolean isFirstTime){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.PREFS_IS_FIRST_TIME, isFirstTime);
        editor.apply();
    }
}
