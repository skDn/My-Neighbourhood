package com.myneighbourhood.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kirchoni on 19/02/16.
 */
public class UserSharedPref {

    public static final String SP_NAME = "userDetails";
    private static UserSharedPref INSTANCE = null;

    public SharedPreferences getUserLocalDatabase() {
        return userLocalDatabase;
    }

    SharedPreferences userLocalDatabase;

    public synchronized static UserSharedPref getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UserSharedPref(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private UserSharedPref(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor editor = userLocalDatabase.edit();
        editor.putInt("id", user.getId());
        editor.putString("username", user.getUsername());
        editor.putString("password", user.getPassword());
        editor.apply();
    }

    public User getUserLoggedIn() {
        int id = userLocalDatabase.getInt("id", 0);
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");
        User u = new User(username, password);
        u.setId(id);
        return u;
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor editor = userLocalDatabase.edit();
        editor.putBoolean("loggedIn", loggedIn);
        editor.apply();
    }

    public boolean getIfLoggedIn() {
        if (userLocalDatabase.getBoolean("loggedIn", false)) {
            return true;
        } else return false;
    }

    public void clearUserData() {
        SharedPreferences.Editor editor = userLocalDatabase.edit();
        editor.clear();
        editor.apply();
    }
}
