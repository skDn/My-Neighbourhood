package com.myneighbourhood.Kiril_Hristov;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myneighbourhood.utils.User;

/**
 * Created by velin on 19/02/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper INSTANCE;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Database.db";

    public static synchronized DBHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DBHelper(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public User registerUser(String username, String password, String email, String phone) {
        // TODO: save user in DB if username is unique else null or throw error
        return new User(username, password, phone, email);
    }

    public User getUser(String username, String password) {
        // TODO: check username + password
        return new User(username, password, "07784397999", "abv@abv.bg");
    }

    public User getUser(int lastLoginUserId) {
        // TODO: get user by id
        return new User("vili", "222", "07784397999", "abv@abv.bg");
    }
}
