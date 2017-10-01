package com.example.goateeddev.revisionalarmclock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

class Preferences {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    Preferences(Context context){
        preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    void setValue(String key, boolean value){
        editor.putBoolean(key, value);
        editor.apply();
    }

    void setValue(String key, int value){
        editor.putInt(key, value);
        editor.apply();
    }

    void setValue(String key, String value){
        editor.putString(key, value);
        editor.apply();
    }

    boolean getValue(String key, boolean def){
        return preferences.getBoolean(key, def);
    }

    int getValue(String key, int def){
        return preferences.getInt(key, def);
    }

    String getValue(String key, String def){
        return preferences.getString(key, def);
    }

    void removePreference(String key){
        preferences.edit().remove(key).apply();
    }

    void removeAllPreferences(){
        preferences.edit().clear().apply();
    }
}
