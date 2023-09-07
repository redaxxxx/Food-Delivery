package com.prof.reda.android.project.fooddelivery.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp.UploadPhotoFragment;

public class CompleteSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_sign_up);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.completeSignUpFrame, new UploadPhotoFragment())
                .commit();
    }
}