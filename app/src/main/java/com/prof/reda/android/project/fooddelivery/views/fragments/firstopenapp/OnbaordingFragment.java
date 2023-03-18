package com.prof.reda.android.project.fooddelivery.views.fragments.firstopenapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentOnbaordingBinding;

import java.util.Timer;
import java.util.TimerTask;


public class OnbaordingFragment extends Fragment {

    private FragmentOnbaordingBinding binding;
    private Timer timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_onbaording, container, false);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        timer = new Timer();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Navigation.findNavController(view).navigate(R.id.action_onbaordingFragment_to_secondOnbaordingFragment);
            }
        }, 5000);


    }


    //    private void isFirstTime() {
//        //for checking if the app is running for the very first time
//        //we need to saved a value to shared preferences
//        SharedPreferences preferences = getActivity().getSharedPreferences("onBoard", Context.MODE_PRIVATE);
//        boolean isFirstTime = preferences.getBoolean("isFirstTime", true);
//
//        //default value
//        if (isFirstTime){
//            //if its true then its first time and we will change it false
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putBoolean("isFirstTime", false);
//            editor.apply();
//
//            Navigation.createNavigateOnClickListener(R.id.action_onbaordingFragment_to_secondOnbaordingFragment);
//        }else {
//            //Start Home
//        }
//    }
}