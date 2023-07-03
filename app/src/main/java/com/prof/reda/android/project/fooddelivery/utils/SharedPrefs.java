package com.prof.reda.android.project.fooddelivery.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SharedPrefs {
    public static final String PREFS_Logger = "LoggerFile";
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    private final Context context;

    public SharedPrefs(Context context) {
        this.context = context;

        pref = context.getSharedPreferences(PREFS_Logger, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setUserInfo(String email, String password){
        editor.putString("token", pref.getString("token", null));
        editor.putString(Constants.KEY_EMAIL, pref.getString(Constants.KEY_EMAIL, null));
        editor.putString(Constants.KEY_PASSWORD, pref.getString(Constants.KEY_PASSWORD, null));
        editor.commit();
    }

    public HashMap<String, String> getSessionInfo(){
        HashMap<String, String> userInfo= new HashMap<>();
        userInfo.put(Constants.KEY_PASSWORD, pref.getString(Constants.KEY_PASSWORD, null));
        userInfo.put(Constants.KEY_EMAIL, pref.getString(Constants.KEY_EMAIL, null));
        return userInfo;
    }
}
