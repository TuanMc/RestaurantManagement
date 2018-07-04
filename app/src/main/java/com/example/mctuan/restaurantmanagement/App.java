package com.example.mctuan.restaurantmanagement;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class App extends Application {

    public static final String FCM_TOKEN = "fcm_token";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void savePref(String key, String value) {
        SharedPreferences caches = getSharedPreferences("caches", 0);
        SharedPreferences.Editor edCaches = caches.edit();
        edCaches.putString(key, value);
        edCaches.commit();
    }

    public String loadPref(String key) {
        SharedPreferences caches = getSharedPreferences("caches", 0);
        return caches.getString(key, null);
    }

    public void clearPref(String key) {
        SharedPreferences caches = getSharedPreferences("caches", 0);
        SharedPreferences.Editor edCaches = caches.edit();
        edCaches.remove(key);
        edCaches.commit();
    }

    public static void hideKeyboard(AppCompatActivity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
