package com.prof.reda.android.project.fooddelivery.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp.ForgotPasswordFragment;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportFragmentManager().beginTransaction().replace(R.id.forgotPasswordFrame, new ForgotPasswordFragment())
                .commit();
    }
}