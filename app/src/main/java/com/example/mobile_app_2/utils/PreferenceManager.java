package com.example.mobile_app_2.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREF_NAME = "MovieAppPrefs";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoggedIn(int userId) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
