package com.myneighbourhood.Kiril_Hristov;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.myneighbourhood.utils.Address;
import com.myneighbourhood.utils.Chat;
import com.myneighbourhood.utils.Message;
import com.myneighbourhood.utils.News;
import com.myneighbourhood.utils.Request;
import com.myneighbourhood.utils.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kiril on 19/02/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper INSTANCE;

    private static final int DB_VERSION = 10;
    private static final String DB_NAME = "Database.db";

    //User table
    private static final String TABLE_USER = "User";
    private static final String COLUMN_USER_ID = "userId";
    private static final String COLUMN_USER_USERNAME = "username";
    private static final String COLUMN_USER_FIRSTNAME = "firstName";
    private static final String COLUMN_USER_LASTNAME = "lastName";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PHONE = "phone";
    private static final String COLUMN_USER_PICTURE = "picture";

    //Rating table
    private static final String TABLE_RATING = "Rating";
    private static final String COLUMN_RATING_USER_ID = "userId";
    private static final String COLUMN_RATING_AS_REQUESTER = "ratingRequester";
    private static final String COLUMN_RATING_AS_APPLICANT = "ratingApplicant";
    private static final String COLUMN_RATING_ENDORCEDBY = "endorcedBy";

    //Address table
    private static final String TABLE_ADDRESS = "Address";
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
    private static final String COLUMN_MESSAGES_TEXT = "text";
    private static final String COLUMN_MESSAGES_TIMESTAMP = "timestamp";
    private static final String COLUMN_MESSAGES_CHAT_FK = "chat_fk";
    private static final String COLUMN_MESSAGES_FROM_USER_FK = "from_user_fk";
    private static final String COLUMN_MESSAGES_TO_USER_FK = "to_user_fk";

    // Chat
    private static final String TABLE_CHATS = "CHATS";
    private static final String COLUMN_CHATS_ID = "_id";
    private static final String COLUMN_CHATS_REQUEST_ID = "request_id";
    private static final String COLUMN_CHATS_USER_1 = "request_user_1";
    private static final String COLUMN_CHATS_USER_2 = "request_user_2";
    private static final String COLUMN_CHATS_LATEST_MSG_TIME = "latest_msg";
    private static final String COLUMN_CHATS_LATEST_VIEW_BY_USER_1 = "latest_view_by_user_1";
    private static final String COLUMN_CHATS_LATEST_VIEW_BY_USER_2 = "latest_view_by_user_2";


    // Create table queries
    private static final String createUser =
            "CREATE TABLE " + TABLE_USER + "(" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_USERNAME + " TEXT, " +
                    COLUMN_USER_FIRSTNAME + " TEXT, " +
                    COLUMN_USER_LASTNAME + " TEXT, " +
                    COLUMN_USER_PASSWORD + " TEXT, " +
                    COLUMN_USER_EMAIL + " TEXT, " +
                    COLUMN_USER_PHONE + " TEXT, " +
                    COLUMN_USER_PICTURE + " BLOB" + ");";

    private static final String createRating =
            "CREATE TABLE " + TABLE_RATING + "(" +
                    COLUMN_RATING_USER_ID + " INTEGER, " +
                    COLUMN_RATING_AS_REQUESTER + " INTEGER, " +
                    COLUMN_RATING_AS_APPLICANT + " INTEGER, " +
                    COLUMN_RATING_ENDORCEDBY + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_RATING_USER_ID + ") REFERENCES " +
                    TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                    ");";

    private static final String createAddress =
            "CREATE TABLE " + TABLE_ADDRESS + "(" +
                    COLUMN_ADDRESS_USER_ID + " INTEGER, " +
                    COLUMN_ADDRESS_STREET + " TEXT, " +
                    COLUMN_ADDRESS_RECT_X + " DOUBLE, " +
                    COLUMN_ADDRESS_RECT_Y + " DOUBLE, " +
                    "FOREIGN KEY (" + COLUMN_ADDRESS_USER_ID + ") REFERENCES " +
                    TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                    ");";

    private static final String createRequest =
            "CREATE TABLE " + TABLE_REQUEST + "(" +
                    COLUMN_REQUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_REQUEST_CREATED_BY_ID + " INTEGER, " +
                    COLUMN_REQUEST_TITLE + " TEXT, " +
                    COLUMN_REQUEST_DESCRIPTION + " TEXT, " +
                    COLUMN_REQUEST_PEOPLE_NEEDED + " INTEGER, " +
                    COLUMN_REQUEST_TIMESTAMP + " INTEGER, " +
                    COLUMN_REQUEST_EXPIRES + " INTEGER, " +
                    COLUMN_REQUEST_ACCEPTED + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_REQUEST_CREATED_BY_ID + ") REFERENCES " +
                    TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                    ");";

    private static final String createNews =
            "CREATE TABLE " + TABLE_NEWS + "(" +
                    COLUMN_NEWS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NEWS_CREATED_BY_ID + " INTEGER, " +
                    COLUMN_NEWS_TITLE + " TEXT, " +
                    COLUMN_NEWS_TEXT + " TEXT, " +
                    COLUMN_NEWS_TIMESTAMP + " INTEGER, " +
                    COLUMN_NEWS_PICTURE + " BLOB, " +
                    "FOREIGN KEY (" + COLUMN_NEWS_CREATED_BY_ID + ") REFERENCES " +
                    TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                    ");";

    private static final String createApplicant =
            "CREATE TABLE " + TABLE_APPLICANT + "(" +
                    COLUMN_APPLICANT_RECORD + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_APPLICANT_APPLICANT_ID + " INTEGER, " +
                    COLUMN_APPLICANT_REQUEST_ID + " INTEGER, " +
                    COLUMN_APPLICANT_CREATOR_ID + " INTEGER, " +
                    COLUMN_APPLICANT_TIMESTAMP + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_APPLICANT_APPLICANT_ID + ") REFERENCES " +
                    TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_APPLICANT_REQUEST_ID + ") REFERENCES " +
                    TABLE_REQUEST + "(" + COLUMN_REQUEST_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_APPLICANT_CREATOR_ID + ") REFERENCES " +
                    TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                    ");";

    private static final String createMessage =
            "CREATE TABLE " + TABLE_MESSAGE + "(" +
                    COLUMN_MESSAGE_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_MESSAGES_TEXT + " TEXT, " +
                    COLUMN_MESSAGES_TIMESTAMP + " INTEGER, " +
                    COLUMN_MESSAGES_CHAT_FK + " INTEGER, " +
                    COLUMN_MESSAGES_FROM_USER_FK + " INTEGER, " +
                    COLUMN_MESSAGES_TO_USER_FK + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_MESSAGES_CHAT_FK + ") REFERENCES " + TABLE_CHATS + "(" + COLUMN_CHATS_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_MESSAGES_FROM_USER_FK + ") REFERENCES " + TABLE_USER + "(" + COLUMN_CHATS_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_MESSAGES_TO_USER_FK + ") REFERENCES " + TABLE_USER + "(" + COLUMN_CHATS_ID + ")" +
                    ");";

    private static final String createChats =
            "CREATE TABLE " + TABLE_CHATS + "(" +
                    COLUMN_CHATS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CHATS_LATEST_MSG_TIME + " INTEGER, " +
                    COLUMN_CHATS_LATEST_VIEW_BY_USER_1 + " INTEGER, " +
                    COLUMN_CHATS_LATEST_VIEW_BY_USER_2 + " INTEGER, " +
                    COLUMN_CHATS_REQUEST_ID + " INTEGER, " +
                    COLUMN_CHATS_USER_1 + " INTEGER, " +
                    COLUMN_CHATS_USER_2 + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_CHATS_USER_1 + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_CHATS_USER_2 + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_CHATS_REQUEST_ID + ") REFERENCES " + TABLE_REQUEST + "(" + COLUMN_REQUEST_ID + ") " +
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
        db.execSQL(createRating);
        db.execSQL(createAddress);
        db.execSQL(createRequest);
        db.execSQL(createNews);
        db.execSQL(createApplicant);
        db.execSQL(createMessage);
        db.execSQL(createChats);
//        registerUser(new User("admin", "fName", "lName", "pass", "mail@mail.com", "080808", null), new Address("100 Gibson Street", 55.8734611, -4.2890117));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUEST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPLICANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHATS);
        onCreate(db);
    }

    public User registerUser(User user, Address address) {
        SQLiteDatabase db = getWritableDatabase();
        String checkUnique =
                "SELECT * FROM " + TABLE_USER +
                        " WHERE " + COLUMN_USER_USERNAME + "=\"" + user.getUsername() + "\";";
        Cursor c = db.rawQuery(checkUnique, null);
        if (c.getCount() > 0) {
            // user with that username already exists
            c.moveToFirst();
            int userId = c.getInt(c.getColumnIndex(COLUMN_USER_ID));
            user.setId(userId);
            return null;
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_FIRSTNAME, user.getFirstName());
        values.put(COLUMN_USER_LASTNAME, user.getLastName());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PHONE, user.getPhone());

        if (user.getImage() == null) {
            values.put(COLUMN_USER_PICTURE, "");
        } else {
            Bitmap yourBitmap = null;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            yourBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            values.put(COLUMN_USER_PICTURE, bArray);
        }

        db.insert(TABLE_USER, null, values);

        int id = 0;
        c = db.rawQuery(checkUnique, null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            id = c.getInt(c.getColumnIndex(COLUMN_USER_ID));
        }

        ContentValues addressValues = new ContentValues();
        addressValues.put(COLUMN_ADDRESS_USER_ID, id);
        addressValues.put(COLUMN_ADDRESS_STREET, address.getAddress());
        addressValues.put(COLUMN_ADDRESS_RECT_X, address.getRectX());
        addressValues.put(COLUMN_ADDRESS_RECT_Y, address.getRextY());
        db.insert(TABLE_ADDRESS, null, addressValues);

        ContentValues ratingValues = new ContentValues();
        ratingValues.put(COLUMN_RATING_USER_ID, id);
        ratingValues.put(COLUMN_RATING_AS_REQUESTER, 0);
        ratingValues.put(COLUMN_RATING_AS_APPLICANT, 0);
        ratingValues.put(COLUMN_RATING_ENDORCEDBY, 0);
        db.insert(TABLE_RATING, null, ratingValues);

        c.close();
        db.close();
        return new User(id, user.getUsername(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getEmail(), user.getPhone(), user.getImage());
    }

    public User getUser(String username, String password) {
        int id;
        String email = "";
        String phone = "";
        String fName = "";
        String lName = "";
        Bitmap image = null;

        SQLiteDatabase db = getWritableDatabase();
        String authenticate =
                "SELECT * FROM " + TABLE_USER +
                        " WHERE " + COLUMN_USER_USERNAME + "=\"" + username + "\" " +
                        " AND " + COLUMN_USER_PASSWORD + "=\"" + password + "\" " + ";";

        Cursor c = db.rawQuery(authenticate, null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            id = c.getInt(c.getColumnIndex(COLUMN_USER_ID));
            if (c.getString(c.getColumnIndex(COLUMN_USER_FIRSTNAME)) != null) {
                fName = c.getString(c.getColumnIndex(COLUMN_USER_FIRSTNAME));
            }
            if (c.getString(c.getColumnIndex(COLUMN_USER_LASTNAME)) != null) {
                lName = c.getString(c.getColumnIndex(COLUMN_USER_LASTNAME));
            }
            if (c.getString(c.getColumnIndex(COLUMN_USER_EMAIL)) != null) {
                email = c.getString(c.getColumnIndex(COLUMN_USER_EMAIL));
            }
            if (c.getString(c.getColumnIndex(COLUMN_USER_PHONE)) != null) {
                phone = c.getString(c.getColumnIndex(COLUMN_USER_PHONE));
            }

            if (c.getBlob(c.getColumnIndex(COLUMN_USER_PICTURE)) != null) {
                byte[] p = c.getBlob(c.getColumnIndex(COLUMN_USER_PICTURE));
                image = BitmapFactory.decodeByteArray(p, 0, p.length);
            }

            c.close();
            db.close();

            return new User(id, username, fName, lName, password, email, phone, image);
        }
        c.close();
        db.close();
        return null;
    }

    public User getUser(int lastLoginUserId) {
        String username = "";
        String password = "";
        String fName = "";
        String lName = "";
        String email = "";
        String phone = "";
        Bitmap image = null;
        SQLiteDatabase db = getWritableDatabase();
        String authenticate =
                "SELECT * FROM " + TABLE_USER +
                        " WHERE " + COLUMN_USER_ID + " = " + lastLoginUserId + ";";
        Cursor c = db.rawQuery(authenticate, null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            if (c.getString(c.getColumnIndex(COLUMN_USER_USERNAME)) != null) {
                username = c.getString(c.getColumnIndex(COLUMN_USER_USERNAME));
            }
            if (c.getString(c.getColumnIndex(COLUMN_USER_FIRSTNAME)) != null) {
                fName = c.getString(c.getColumnIndex(COLUMN_USER_FIRSTNAME));
            }
            if (c.getString(c.getColumnIndex(COLUMN_USER_LASTNAME)) != null) {
                lName = c.getString(c.getColumnIndex(COLUMN_USER_LASTNAME));
            }
            if (c.getString(c.getColumnIndex(COLUMN_USER_PASSWORD)) != null) {
                password = c.getString(c.getColumnIndex(COLUMN_USER_PASSWORD));
            }
            if (c.getString(c.getColumnIndex(COLUMN_USER_EMAIL)) != null) {
                email = c.getString(c.getColumnIndex(COLUMN_USER_EMAIL));
            }
            if (c.getString(c.getColumnIndex(COLUMN_USER_PHONE)) != null) {
                phone = c.getString(c.getColumnIndex(COLUMN_USER_PHONE));
            }
            if (c.getBlob(c.getColumnIndex(COLUMN_USER_PICTURE)) != null) {
                byte[] p = c.getBlob(c.getColumnIndex(COLUMN_USER_PICTURE));
                image = BitmapFactory.decodeByteArray(p, 0, p.length);
            }
            c.close();
            db.close();
            return new User(lastLoginUserId, username, fName, lName, password, email, phone, image);
        }
        c.close();
        db.close();
        return null;
    }

    public ArrayList<User> getUsers() {
        int id;
        String username = "";
        String password = "";
        String fName = "";
        String lName = "";
        String email = "";
        String phone = "";
        Bitmap image = null;
        ArrayList<User> toReturn = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String authenticate =
                "SELECT * FROM " + TABLE_USER + ";";
        Cursor c = db.rawQuery(authenticate, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            id = c.getInt(c.getColumnIndex(COLUMN_USER_ID));
            if (c.getString(c.getColumnIndex(COLUMN_USER_USERNAME)) != null) {
                username = c.getString(c.getColumnIndex(COLUMN_USER_USERNAME));
            }
            if (c.getString(c.getColumnIndex(COLUMN_USER_FIRSTNAME)) != null) {
                fName = c.getString(c.getColumnIndex(COLUMN_USER_FIRSTNAME));
            }
            if (c.getString(c.getColumnIndex(COLUMN_USER_LASTNAME)) != null) {
                lName = c.getString(c.getColumnIndex(COLUMN_USER_LASTNAME));
            }
            if (c.getString(c.getColumnIndex(COLUMN_USER_PASSWORD)) != null) {
                password = c.getString(c.getColumnIndex(COLUMN_USER_PASSWORD));
            }
            if (c.getString(c.getColumnIndex(COLUMN_USER_EMAIL)) != null) {
                email = c.getString(c.getColumnIndex(COLUMN_USER_EMAIL));
            }
            if (c.getString(c.getColumnIndex(COLUMN_USER_PHONE)) != null) {
                phone = c.getString(c.getColumnIndex(COLUMN_USER_PHONE));
            }
            if (c.getBlob(c.getColumnIndex(COLUMN_USER_PICTURE)) != null) {
                byte[] p = c.getBlob(c.getColumnIndex(COLUMN_USER_PICTURE));
                image = BitmapFactory.decodeByteArray(p, 0, p.length);
            }
            toReturn.add(new User(id, username, fName, lName, password, email, phone, image));
            c.moveToNext();
        }

        c.close();
        db.close();
        return toReturn;
    }


    public Request addRequestFromUI(Request request) {
        // check if request exists already
        ContentValues values = new ContentValues();
        values.put(COLUMN_REQUEST_CREATED_BY_ID, request.getCreatorId());
        values.put(COLUMN_REQUEST_TITLE, request.getTitle());
        values.put(COLUMN_REQUEST_DESCRIPTION, request.getDescription());
        values.put(COLUMN_REQUEST_PEOPLE_NEEDED, request.getPeopleNeeded());
        values.put(COLUMN_REQUEST_TIMESTAMP, request.getTimestamp());
        values.put(COLUMN_REQUEST_EXPIRES, request.getExpires());
        values.put(COLUMN_REQUEST_ACCEPTED, request.getAccepted());
        SQLiteDatabase db = getWritableDatabase();
        long insertedId = db.insert(TABLE_REQUEST, null, values);
        db.close();


        if (insertedId == -1) {
            return null;
        } else {
            request.setId(insertedId);
        }
        return request;
    }

    

    public Request getRequest(User creator, String title) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_REQUEST + " WHERE " + COLUMN_REQUEST_CREATED_BY_ID + " = " + creator.getId() + " AND " + COLUMN_REQUEST_TITLE + " = \"" + title + "\"";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return createRequestFromCursor(cursor);
        }
        return null;
    }

    public Request getRequest(int requestId) {
        int creatorId;
        String title = "";
        String description = "";
        int peopleNeeded;
        long timestamp = 0;
        long expires = 0;
        int accepted;
        SQLiteDatabase db = getWritableDatabase();
        String getRequest =
                "SELECT * FROM " + TABLE_REQUEST +
                        " WHERE " + COLUMN_REQUEST_ID + " = " + requestId + ";";
        Cursor c = db.rawQuery(getRequest, null);

        if (c.getCount() > 0) {
            c.moveToFirst();
            return createRequestFromCursor(c);
        }
        c.close();
        db.close();
        return null;
    }

    private Request createRequestFromCursor(Cursor c) {
        SQLiteDatabase db = getWritableDatabase();
        long creatorId = c.getLong(c.getColumnIndex(COLUMN_REQUEST_CREATED_BY_ID));
        String title = "";
        String description = "";
        long requestId = c.getLong(c.getColumnIndex(COLUMN_REQUEST_ID));
        if (c.getString(c.getColumnIndex(COLUMN_REQUEST_TITLE)) != null) {
            title = c.getString(c.getColumnIndex(COLUMN_REQUEST_TITLE));
        }
        if (c.getString(c.getColumnIndex(COLUMN_REQUEST_DESCRIPTION)) != null) {
            description = c.getString(c.getColumnIndex(COLUMN_REQUEST_DESCRIPTION));
        }
        int peopleNeeded = c.getInt(c.getColumnIndex(COLUMN_REQUEST_PEOPLE_NEEDED));
        long timestamp = c.getLong(c.getColumnIndex(COLUMN_REQUEST_TIMESTAMP));
        long expires = c.getLong(c.getColumnIndex(COLUMN_REQUEST_EXPIRES));
        int accepted = c.getInt(c.getColumnIndex(COLUMN_REQUEST_ACCEPTED));
        c.close();
        db.close();
        return new Request(requestId, creatorId, title, description, peopleNeeded, timestamp, expires, accepted);
    }

    // get myRequests or feedRequests
    public ArrayList<Request> getRequests(int userId, String feedOrMy) {
        int requestId;
        int creatorId;
        String title = "";
        String description = "";
        int peopleNeeded;
        long timestamp = 0;
        long expires = 0;
        int accepted;
        ArrayList<Request> toReturn = new ArrayList<>();

        String getMyRequests =
                "SELECT * FROM " + TABLE_REQUEST +
                        " WHERE " + COLUMN_REQUEST_CREATED_BY_ID + " = " + userId + ";";
        String getFeedRequests =
                "SELECT * FROM " + TABLE_REQUEST +
                        " WHERE " + COLUMN_REQUEST_CREATED_BY_ID + " != " + userId + ";";

        String queryToExecute;
        if (feedOrMy.equals("feed")) {
            queryToExecute = getFeedRequests;
        } else queryToExecute = getMyRequests;

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(queryToExecute, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            requestId = c.getInt(c.getColumnIndex(COLUMN_REQUEST_ID));
            creatorId = c.getInt(c.getColumnIndex(COLUMN_REQUEST_CREATED_BY_ID));
            if (c.getString(c.getColumnIndex(COLUMN_REQUEST_TITLE)) != null) {
                title = c.getString(c.getColumnIndex(COLUMN_REQUEST_TITLE));
            }
            if (c.getString(c.getColumnIndex(COLUMN_REQUEST_DESCRIPTION)) != null) {
                description = c.getString(c.getColumnIndex(COLUMN_REQUEST_DESCRIPTION));
            }
            peopleNeeded = c.getInt(c.getColumnIndex(COLUMN_REQUEST_PEOPLE_NEEDED));
            if (c.getString(c.getColumnIndex(COLUMN_REQUEST_TIMESTAMP)) != null) {
                timestamp = c.getLong(c.getColumnIndex(COLUMN_REQUEST_TIMESTAMP));
            }
            expires = c.getLong(c.getColumnIndex(COLUMN_REQUEST_EXPIRES));

            accepted = c.getInt(c.getColumnIndex(COLUMN_REQUEST_ACCEPTED));

            toReturn.add(new Request(requestId, creatorId, title, description, peopleNeeded, timestamp, expires, accepted));

            c.moveToNext();
        }
        c.close();
        db.close();
        return toReturn;
    }

    public ArrayList<News> getNews() {
        int newsId;
        int userId;
        String title = "";
        String text = "";
        long timestamp = 0;
        Bitmap picture = null;
        ArrayList<News> toReturn = new ArrayList<>();

        String queryToExecute =
                "SELECT * FROM " + TABLE_NEWS;

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(queryToExecute, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            newsId = c.getInt(c.getColumnIndex(COLUMN_NEWS_ID));
            userId = c.getInt(c.getColumnIndex(COLUMN_NEWS_CREATED_BY_ID));
            if (c.getString(c.getColumnIndex(COLUMN_NEWS_TITLE)) != null) {
                title = c.getString(c.getColumnIndex(COLUMN_NEWS_TITLE));
            }
            if (c.getString(c.getColumnIndex(COLUMN_NEWS_TEXT)) != null) {
                text = c.getString(c.getColumnIndex(COLUMN_NEWS_TEXT));
            }
            if (c.getString(c.getColumnIndex(COLUMN_NEWS_TIMESTAMP)) != null) {
                timestamp = c.getLong(c.getColumnIndex(COLUMN_NEWS_TIMESTAMP));
            }
            if (c.getString(c.getColumnIndex(COLUMN_NEWS_PICTURE)) != null) {
                byte[] p = c.getBlob(c.getColumnIndex(COLUMN_NEWS_PICTURE));
                picture = BitmapFactory.decodeByteArray(p, 0, p.length);
            }

            toReturn.add(new News(newsId, userId, title, text, timestamp, picture));

            c.moveToNext();
        }
        c.close();
        db.close();
        return toReturn;
    }

    public Chat addChat(User user1, User user2, Request request) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CHATS_REQUEST_ID, request.getId());
        values.put(COLUMN_CHATS_USER_1, user1.getId());
        values.put(COLUMN_CHATS_USER_2, user2.getId());
        values.putNull(COLUMN_CHATS_LATEST_MSG_TIME);
        values.put(COLUMN_CHATS_LATEST_VIEW_BY_USER_1, System.currentTimeMillis());
        values.put(COLUMN_CHATS_LATEST_VIEW_BY_USER_2, System.currentTimeMillis());

        long insertedChatId = getWritableDatabase().insert(TABLE_CHATS, null, values);
        if (insertedChatId == -1) {
            return null;
        } else {
            Chat chat = new Chat(insertedChatId, request, user1, user2, null, new Date(), new Date(), new ArrayList<Message>());
            return chat;
        }
    }

    public void addMessage(Chat chat, Message msg) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MESSAGES_TEXT, msg.getText());
        values.put(COLUMN_MESSAGES_TIMESTAMP, System.currentTimeMillis());
        values.put(COLUMN_MESSAGES_CHAT_FK, chat.getId());
    }

    public ArrayList<Chat> getChatsForUser(User user) {
        ArrayList<Chat> chats = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        String queryChats = "SELECT * FROM " + TABLE_CHATS + " WHERE " + COLUMN_CHATS_USER_1 + " = " + user.getId() + " OR " + COLUMN_CHATS_USER_2 + " = " + user.getId() + " ORDER_BY " + COLUMN_CHATS_LATEST_MSG_TIME + " DESC ";

        Cursor cChats = db.rawQuery(queryChats, null);
        if (cChats.getCount() > 0) {
            cChats.moveToFirst();
            while (!cChats.isAfterLast()) {
                int chatId = cChats.getInt(cChats.getColumnIndex(COLUMN_CHATS_ID));
                int requestId = cChats.getInt(cChats.getColumnIndex(COLUMN_CHATS_REQUEST_ID));
                int userId1 = cChats.getInt(cChats.getColumnIndex(COLUMN_CHATS_USER_1));
                int userId2 = cChats.getInt(cChats.getColumnIndex(COLUMN_CHATS_USER_2));
                int latestMsgTime = cChats.getInt(cChats.getColumnIndex(COLUMN_CHATS_LATEST_MSG_TIME));
                int latestViewByUser1Time = cChats.getInt(cChats.getColumnIndex(COLUMN_CHATS_LATEST_VIEW_BY_USER_1));
                int latestViewByUser2Time = cChats.getInt(cChats.getColumnIndex(COLUMN_CHATS_LATEST_VIEW_BY_USER_2));

                Request request = getRequest(requestId);
                User user1 = getUser(userId1);
                User user2 = getUser(userId2);
                Date latestMsgDate = new Date(latestMsgTime);
                Date latestViewByUser1Date = new Date(latestViewByUser1Time);
                Date latestViewByUser2Date = new Date(latestViewByUser2Time);


                Chat chat = new Chat(chatId, request, user1, user2, latestMsgDate, latestViewByUser1Date, latestViewByUser2Date, null);
                String queryMsg = "SELECT * FROM " + TABLE_MESSAGE + " WHERE " + COLUMN_MESSAGES_CHAT_FK + " = " + chat.getId() + " ORDER_BY " + COLUMN_MESSAGES_TIMESTAMP + " DESC ";

                Cursor cMsg = db.rawQuery(queryMsg, null);
                if (cMsg.getCount() > 0) {
                    cMsg.moveToFirst();
                    while (!cMsg.isAfterLast()) {
                        int msgId = cMsg.getInt(cMsg.getColumnIndex(COLUMN_MESSAGE_MESSAGE_ID));
                        int msgTime = cMsg.getInt(cMsg.getColumnIndex(COLUMN_MESSAGES_TIMESTAMP));
                        String msgText = cMsg.getString(cMsg.getColumnIndex(COLUMN_MESSAGES_TEXT));
                        int fromUserId = cMsg.getInt(cMsg.getColumnIndex(COLUMN_MESSAGES_FROM_USER_FK));
                        int toUserId = cMsg.getInt(cMsg.getColumnIndex(COLUMN_MESSAGES_TO_USER_FK));


                        User fromUser = getUser(fromUserId);
                        User toUser = getUser(toUserId);
                        Date msgDate = new Date(msgTime);
                        Message msg = new Message(msgId, msgDate, chat, msgText, fromUser, toUser);
                        chat.addMsg(msg);
                    }
                }
                chats.add(chat);
            }
        }
        return chats;
    }


    public void addNews(News news) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NEWS_CREATED_BY_ID, news.getUserId());
        values.put(COLUMN_NEWS_TITLE, news.getTitle());
        values.put(COLUMN_NEWS_TEXT, news.getText());
        values.put(COLUMN_NEWS_TIMESTAMP, news.getTimestamp());

        if (news.getPicture() == null) {
            values.put(COLUMN_NEWS_PICTURE, "");
        } else {
            Bitmap yourBitmap = null;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            yourBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            values.put(COLUMN_NEWS_PICTURE, bArray);
        }

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NEWS, null, values);
        db.close();
    }

    public News getNews(int id) {
        int newsId;
        int userId;
        String title = "";
        String text = "";
        long timestamp = 0;
        Bitmap picture = null;

        String queryToExecute =
                "SELECT * FROM " + TABLE_NEWS + " WHERE " + COLUMN_NEWS_ID + "=\"" + id + "\" " + ";";

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(queryToExecute, null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            newsId = c.getInt(c.getColumnIndex(COLUMN_NEWS_ID));
            userId = c.getInt(c.getColumnIndex(COLUMN_NEWS_CREATED_BY_ID));
            if (c.getString(c.getColumnIndex(COLUMN_NEWS_TITLE)) != null) {
                title = c.getString(c.getColumnIndex(COLUMN_NEWS_TITLE));
            }
            if (c.getString(c.getColumnIndex(COLUMN_NEWS_TEXT)) != null) {
                text = c.getString(c.getColumnIndex(COLUMN_NEWS_TEXT));
            }
            if (c.getString(c.getColumnIndex(COLUMN_NEWS_TIMESTAMP)) != null) {
                timestamp = c.getLong(c.getColumnIndex(COLUMN_NEWS_TIMESTAMP));
            }
            if (c.getString(c.getColumnIndex(COLUMN_NEWS_PICTURE)) != null) {
                byte[] p = c.getBlob(c.getColumnIndex(COLUMN_NEWS_PICTURE));
                picture = BitmapFactory.decodeByteArray(p, 0, p.length);
            }

            return new News(newsId, userId, title, text, timestamp, picture);
        }
        c.close();
        db.close();
        return null;
    }
}
