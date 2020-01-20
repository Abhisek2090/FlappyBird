package com.mygames.utils;

import android.content.Context;
import android.content.SharedPreferences;



public class SharedPreferencesHelper {

    private static final String PREF_NAME = "sharedPreferences";
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private static SharedPreferencesHelper sInstance;

    public SharedPreferencesHelper(Context context) {
        editor = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE).edit();
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }


    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SharedPreferencesHelper(context);
        }
    }

    public static synchronized SharedPreferencesHelper getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(SharedPreferencesHelper.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

        public void putString(String key, String value) {
            editor.putString(key,value);
            editor.apply();
        }
        public void putBoolean(String key, Boolean value) {
            editor.putBoolean(key, value);
            editor.apply();

        }
        public void putLong(String key, long value) {
            editor.putLong(key, value);
            editor.apply();
        }

        public void putFloat(String key, float value) {
            editor.putFloat(key, value);
            editor.apply();
        }

        public void removeId(String id) {
            editor.remove(id);
            editor.apply();
        }

        public String getString(String key) {return sharedPreferences.getString(key, null);}

        public boolean getBoolean(String key) {return sharedPreferences.getBoolean(key, false);}

        public long getLong(String key) {return  sharedPreferences.getLong(key, 0);}

        public Float getFloat(String key) {return  sharedPreferences.getFloat(key, 0);}



}
