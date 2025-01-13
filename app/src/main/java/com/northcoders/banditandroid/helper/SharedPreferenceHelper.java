package com.northcoders.banditandroid.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {
    private static final String PREF_NAME = "common_shared_prefs";
    private static SharedPreferenceHelper instance;
    private final SharedPreferences sharedPreferences;
    private SharedPreferenceHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    public static synchronized SharedPreferenceHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceHelper(context);
        }
        return instance;
    }
    public void putString(String key, String value)
    {
        sharedPreferences.edit().putString(key, value).apply();
    }
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }
}