package com.prof.reda.android.project.fooddelivery.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.ActivityMainBinding;
import com.prof.reda.android.project.fooddelivery.utils.Constants;
import com.prof.reda.android.project.fooddelivery.utils.DataSource;

public class MainActivity extends AppCompatActivity {
    
    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    private SharedPreferences sharedPreferences;
    private DataSource dataSource;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences(Constants.STATUS_PREF, Context.MODE_PRIVATE);
        dataSource = new DataSource(sharedPreferences);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        if (firebaseAuth.getCurrentUser() != null){
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        }else{
                            isFirstTime();
                        }
                    }
                });

//                if(sharedPreferences.getBoolean(Constants.PREFS_IS_LOGGING, true)){
//                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
//                    finish();
//                }else {
//                    isFirstTime();
//                }
            }
        }, 1500);
    }

    private void isFirstTime() {
        //for checking if the app is running for the very first time
        //we need to saved a value to shared preferences
        SharedPreferences preferences = getSharedPreferences(Constants.STATUS_PREF, Context.MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean(Constants.PREFS_IS_FIRST_TIME, true);

        //default value
        if (isFirstTime){
            sharedPreferences.edit().putBoolean(Constants.PREFS_IS_FIRST_TIME, false).apply();
            startActivity(new Intent(this, OnboardActivity.class));

        }else {
            //Start Home
            startActivity(new Intent(this, AuthActivity.class));

        }
    }

}