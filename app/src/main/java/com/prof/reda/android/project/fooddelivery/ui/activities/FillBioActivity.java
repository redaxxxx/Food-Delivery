package com.prof.reda.android.project.fooddelivery.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp.FillInBioFragment;

public class FillBioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_bio);

        getSupportFragmentManager().beginTransaction().replace(R.id.fillBioFrame, new FillInBioFragment())
                .commit();
    }
}