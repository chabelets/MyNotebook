package com.example.tom.mynotebook.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSettings {
    private final static String KEY_BOOLEAN_IS_FIRST_START = "KEY_BOOLEAN_IS_FIRST_START";

    private SharedPreferences mSharedPreferences;

    public AppSettings(Context context){
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isFirstStart(){
        return mSharedPreferences.getBoolean(KEY_BOOLEAN_IS_FIRST_START, true);
    }

    public void setIsFirstStart(boolean isFirstStart){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(KEY_BOOLEAN_IS_FIRST_START, isFirstStart);
        editor.commit();

    }
}
