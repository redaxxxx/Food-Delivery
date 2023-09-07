package com.prof.reda.android.project.fooddelivery.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp.ResetFragment;
import com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp.VerificationCodeFragment;

public class ResetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.resetPasswordFrame, new VerificationCodeFragment());
    }
}