package com.myneighbourhood.Kiril_Hristov;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myneighbourhood.utils.User;

/**
 * Created by Kiril on 19/02/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper INSTANCE;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Database.db";

    //User table
    private static final String TABLE_USER = "User";
    private static final String COLUMN_USER_ID = "userId";;
    private static final String COLUMN_USER_USERNAME = "username";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PHONE = "phone";
    private static final String COLUMN_USER_PICTURE = "picture";

    //Address table
    private static final String TABLE_ADDRESS = "Address";
    private static final String COLUMN_ADDRESS_ID = "addressId";
    private static final String COLUMN_ADDRESS_USER_ID = "userId";
    private static final String COLUMN_ADDRESS_STREET = "street";
    private static final String COLUMN_ADDRESS_RECT_X = "rectX";
    private static final String COLUMN_ADDRESS_RECT_Y = "rectY";

    //Request table
    private static final String TABLE_REQUEST = "Request";
    private static final String COLUMN_REQUEST_ID = "requestId";
    private static final String COLUMN_REQUEST_CREATED_BY_ID = "userId";
    private static final String COLUMN_REQUEST_TITLE = "title";
    private static final String COLUMN_REQUEST_DESCRIPTION = "description";
    private static final String COLUMN_REQUEST_PEOPLE_NEEDED = "peopleNeeded";
    private static final String COLUMN_REQUEST_TIMESTAMP = "timestamp";
    private static final String COLUMN_REQUEST_EXPIRES = "expires";
    private static final String COLUMN_REQUEST_ACCEPTED = "isAccepted";

    //News table
    private static final String TABLE_NEWS = "News";
    private static final String COLUMN_NEWS_ID = "newsId";
    private static final String COLUMN_NEWS_CREATED_BY_ID = "userId";
    private static final String COLUMN_NEWS_TITLE = "title";
    private static final String COLUMN_NEWS_TEXT = "text";
    private static final String COLUMN_NEWS_TIMESTAMP = "timestamp";
    private static final String COLUMN_NEWS_PICTURE = "picture";

    //News table
    private static final String TABLE_APPLICANT = "Applicant";
    private static final String COLUMN_APPLICANT_RECORD = "applicationId";
    private static final String COLUMN_APPLICANT_APPLICANT_ID = "applicantId";
    private static final String COLUMN_APPLICANT_REQUEST_ID = "requestId";
    private static final String COLUMN_APPLICANT_CREATOR_ID = "creatorId";
    private static final String COLUMN_APPLICANT_TIMESTAMP = "timestamp";

    //Messages table
    private static final String TABLE_MESSAGE = "Message";
    private static final String COLUMN_MESSAGE_MESSAGE_ID = "messageId";
    private static final String COLUMN_MESSAGES_SENDER_ID = "senderId";
    private static final String COLUMN_MESSAGES_RECEIVER_ID = "receiverId";
    private static final String COLUMN_MESSAGES_REQUEST_ID = "requestId";
    private static final String COLUMN_MESSAGES_TEXT = "text";
    private static final String COLUMN_MESSAGES_TIMESTAMP = "timestamp";

    // Create table queries
    private static final String createUser =
            "CREATE TABLE " + TABLE_USER + "(" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USER_USERNAME + " TEXT, " +
            COLUMN_USER_PASSWORD + " TEXT, " +
            COLUMN_USER_EMAIL + " TEXT, " +
            COLUMN_USER_PHONE + " TEXT, " +
            COLUMN_USER_PICTURE + " TEXT" + ");";

    private static final String createAddress =
            "CREATE TABLE " + TABLE_ADDRESS + "(" +
            COLUMN_ADDRESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ADDRESS_USER_ID + " INTEGER, " +
            COLUMN_ADDRESS_STREET + " TEXT, " +
            COLUMN_ADDRESS_RECT_X + " DOUBLE, " +
            COLUMN_ADDRESS_RECT_Y + " DOUBLE, " +
            "FOREIGN KEY (" + COLUMN_ADDRESS_USER_ID + ") REFERENCES " +
            TABLE_USER + "(" + COLUMN_USER_ID + ")"+
                    ");";

    private static final String createRequest =
            "CREATE TABLE " + TABLE_REQUEST + "(" +
            COLUMN_REQUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_REQUEST_CREATED_BY_ID + " INTEGER, " +
            COLUMN_REQUEST_TITLE + " TEXT, " +
            COLUMN_REQUEST_DESCRIPTION + " TEXT, " +
            COLUMN_REQUEST_PEOPLE_NEEDED + " INTEGER, " +
            COLUMN_REQUEST_TIMESTAMP + " TEXT, " +
            COLUMN_REQUEST_EXPIRES + " TEXT, " +
            COLUMN_REQUEST_ACCEPTED + " INTEGER, " +
            "FOREIGN KEY (" + COLUMN_REQUEST_CREATED_BY_ID + ") REFERENCES " +
            TABLE_USER + "(" + COLUMN_USER_ID + ")"+
            ");";

    private static final String createNews =
            "CREATE TABLE " + TABLE_NEWS + "(" +
            COLUMN_NEWS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NEWS_CREATED_BY_ID + " INTEGER, " +
            COLUMN_NEWS_TITLE + " TEXT, " +
            COLUMN_NEWS_TEXT + " TEXT, " +
            COLUMN_NEWS_TIMESTAMP + " TEXT, " +
            COLUMN_NEWS_PICTURE + " TEXT, " +
            "FOREIGN KEY (" + COLUMN_NEWS_CREATED_BY_ID + ") REFERENCES " +
            TABLE_USER + "(" + COLUMN_USER_ID + ")"+
            ");";

    private static final String createApplicant =
            "CREATE TABLE " + TABLE_APPLICANT + "(" +
            COLUMN_APPLICANT_RECORD + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_APPLICANT_APPLICANT_ID + " INTEGER, " +
            COLUMN_APPLICANT_REQUEST_ID + " INTEGER, " +
            COLUMN_APPLICANT_CREATOR_ID + " INTEGER, " +
            COLUMN_APPLICANT_TIMESTAMP + " TEXT, " +
            "FOREIGN KEY (" + COLUMN_APPLICANT_APPLICANT_ID + ") REFERENCES " +
            TABLE_USER + "(" + COLUMN_USER_ID + "), "+
            "FOREIGN KEY (" + COLUMN_APPLICANT_REQUEST_ID + ") REFERENCES " +
            TABLE_REQUEST + "(" + COLUMN_REQUEST_ID + "), "+
            "FOREIGN KEY (" + COLUMN_APPLICANT_CREATOR_ID + ") REFERENCES " +
            TABLE_USER + "(" + COLUMN_USER_ID + ")"+
            ");";

    private static final String createMessage =
            "CREATE TABLE " + TABLE_MESSAGE + "(" +
            COLUMN_MESSAGE_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_MESSAGES_SENDER_ID + " INTEGER, " +
            COLUMN_MESSAGES_RECEIVER_ID + " INTEGER, " +
            COLUMN_MESSAGES_REQUEST_ID + " INTEGER, " +
            COLUMN_MESSAGES_TEXT + " TEXT, " +
            COLUMN_MESSAGES_TIMESTAMP + " TEXT, " +
            "FOREIGN KEY (" + COLUMN_MESSAGES_SENDER_ID + ") REFERENCES " +
            TABLE_USER + "(" + COLUMN_USER_ID + "), "+
            "FOREIGN KEY (" + COLUMN_MESSAGES_REQUEST_ID + ") REFERENCES " +
            TABLE_REQUEST + "(" + COLUMN_REQUEST_ID + "), "+
            "FOREIGN KEY (" + COLUMN_MESSAGES_RECEIVER_ID + ") REFERENCES " +
            TABLE_USER + "(" + COLUMN_USER_ID + ")"+
            ");";

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
        db.execSQL(createUser);
        db.execSQL(createAddress);
        db.execSQL(createRequest);
        db.execSQL(createNews);
        db.execSQL(createApplicant);
        db.execSQL(createMessage);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUEST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPLICANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        onCreate(db);
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
