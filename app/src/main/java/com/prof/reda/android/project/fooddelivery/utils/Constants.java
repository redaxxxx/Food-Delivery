package com.prof.reda.android.project.fooddelivery.utils;

import com.google.firebase.auth.FirebaseAuth;

public class Constants {
    public static final String KEY_FIRST_NAME = "com.prof.reda.android.project.fooddelivery.firstName";
    public static final String KEY_SECOND_NAME = "com.prof.reda.android.project.fooddelivery.secondName";
    public static final String KEY_PHONE_NUMBER = "com.prof.reda.android.project.fooddelivery.phoneNumber";
    public static final String KEY_E_MMAIL = "com.prof.reda.android.project.fooddelivery.email";
    public static final String KEY_USERNAME = "com.prof.reda.android.project.fooddelivery.username";
    public static final String KEY_PRICE = "com.prof.reda.android.project.fooddelivery.price";
    public static final String KEY_FOOD_NAME = "com.prof.reda.android.project.fooddelivery.foodName";
    public static final String KEY_RESTRO_NAME = "com.prof.reda.android.project.fooddelivery.restroName";
    public static final String KEY_IMAGE = "com.prof.reda.android.project.fooddelivery.pic";
    public static final String TAG = "FoodDelivery";

    public static final String USERS_COLLECTION = "users";
    public static final String RESTAURANTS_COLLECTION = "restaurants";
    public static final String FOODS_COLLECTION = "foods";
    public static final String CARTS_COLLECTION = "carts";
    public static final String ORDERS_COLLECTION = "orders";

    public static final String RESTAURANTS_STORAGE_REF = "restaurant";
    public static final String FOODS_STORAGE_REF = "food";

    public static final String USERS_PREF = "user";
    public static final String STATUS_PREF = "status";
    public static final String PREFS_IS_LOGGING = "isLogging";
    public static final String PREFS_IS_FIRST_TIME = "isFirstTime";



//    public static String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

}
