package com.prof.reda.android.project.fooddelivery.utils;

import com.google.firebase.auth.FirebaseAuth;

public class Config {
    public static final String firebaseUSerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
}
