package com.myneighbourhood.Kiril_Hristov;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.myneighbourhood.R;
import com.myneighbourhood.utils.Address;
import com.myneighbourhood.utils.Chat;
import com.myneighbourhood.utils.Message;
import com.myneighbourhood.utils.News;
import com.myneighbourhood.utils.Rating;
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

    private static final int DB_VERSION = 30;
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
    private static final String COLUMN_REQUESTS_REQUEST_ID = "requestId";
    private static final String COLUMN_REQUEST_CREATED_BY_ID = "userId";
    private static final String COLUMN_REQUEST_TITLE = "title";
    private static final String COLUMN_REQUEST_DESCRIPTION = "description";
    private static final String COLUMN_REQUEST_PEOPLE_NEEDED = "peopleNeeded";
    private static final String COLUMN_REQUEST_TIMESTAMP = "timestamp";
    private static final String COLUMN_REQUEST_EXPIRES = "expires";
    private static final String COLUMN_REQUEST_ACCEPTED = "isAccepted";
    private static final String COLUMN_REQUEST_STATUS = "status";

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
    private static final String COLUMN_MESSAGE_ID = "messageId";
    private static final String COLUMN_MESSAGES_TEXT = "text";
    private static final String COLUMN_MESSAGES_TIMESTAMP = "timestamp";
    private static final String COLUMN_MESSAGES_CHAT_FK = "chat_fk";
    private static final String COLUMN_MESSAGES_FROM_USER_FK = "from_user_fk";
    private static final String COLUMN_MESSAGES_TO_USER_FK = "to_user_fk";

    // Chat
    private static final String TABLE_CHATS = "CHATS";
    private static final String COLUMN_CHATS_ID = "_id";
    private static final String COLUMN_CHATS_CREATED_AT = "created_at";
    private static final String COLUMN_CHATS_REQUEST_ID = "request_id";
    private static final String COLUMN_CHATS_USER_1 = "request_user_1";
    private static final String COLUMN_CHATS_USER_2 = "request_user_2";
    private static final String COLUMN_CHATS_LATEST_MSG_TIME = "latest_msg";
    private static final String COLUMN_CHATS_LATEST_VIEW_BY_USER_1 = "latest_view_by_user_1";
    private static final String COLUMN_CHATS_LATEST_VIEW_BY_USER_2 = "latest_view_by_user_2";
    private static final String COLUMN_CHATS_ACCEPTED_USER_1 = "accepted_by_user_1";
    private static final String COLUMN_CHATS_ACCEPTED_USER_2 = "accepted_by_user_2";


    public void printUSers(){
        String q = "SELECT * FROM " + TABLE_USER;
        Cursor cursor = getReadableDatabase().rawQuery(q, null);
        for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
            System.out.println(createUserFromCursor(cursor));
        }

        cursor.close();
    }
    // Create table queries
    private static final String createUser =
            "CREATE TABLE " + TABLE_USER + "(" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_USERNAME + " TEXT UNIQUE, " +
                    COLUMN_USER_FIRSTNAME + " TEXT, " +
                    COLUMN_USER_LASTNAME + " TEXT, " +
                    COLUMN_USER_PASSWORD + " TEXT, " +
                    COLUMN_USER_EMAIL + " TEXT, " +
                    COLUMN_USER_PHONE + " TEXT, " +
                    COLUMN_USER_PICTURE + " BLOB" +
                    ");";

    private static final String createRating =
            "CREATE TABLE " + TABLE_RATING + "(" +
                    COLUMN_RATING_USER_ID + " INTEGER UNIQUE, " +
                    COLUMN_RATING_AS_REQUESTER + " INTEGER, " +
                    COLUMN_RATING_AS_APPLICANT + " INTEGER, " +
                    COLUMN_RATING_ENDORCEDBY + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_RATING_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + ")" +

                    ");";

    private static final String createAddress =
            "CREATE TABLE " + TABLE_ADDRESS + "(" +
                    COLUMN_ADDRESS_USER_ID + " INTEGER, " +
                    COLUMN_ADDRESS_STREET + " TEXT, " +
                    COLUMN_ADDRESS_RECT_X + " DOUBLE, " +
                    COLUMN_ADDRESS_RECT_Y + " DOUBLE, " +
                    "FOREIGN KEY (" + COLUMN_ADDRESS_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                    ");";

    private static final String createRequest =
            "CREATE TABLE " + TABLE_REQUEST + "(" +
                    COLUMN_REQUESTS_REQUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_REQUEST_CREATED_BY_ID + " INTEGER, " +
                    COLUMN_REQUEST_TITLE + " TEXT, " +
                    COLUMN_REQUEST_DESCRIPTION + " TEXT, " +
                    COLUMN_REQUEST_PEOPLE_NEEDED + " INTEGER, " +
                    COLUMN_REQUEST_TIMESTAMP + " INTEGER, " +
                    COLUMN_REQUEST_EXPIRES + " INTEGER, " +
                    COLUMN_REQUEST_ACCEPTED + " INTEGER, " +
                    COLUMN_REQUEST_STATUS + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_REQUEST_CREATED_BY_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                    "UNIQUE (" + COLUMN_REQUEST_CREATED_BY_ID + ", " + COLUMN_REQUEST_TITLE + ") " +
                    ");";

    private static final String createNews =
            "CREATE TABLE " + TABLE_NEWS + "(" +
                    COLUMN_NEWS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NEWS_CREATED_BY_ID + " INTEGER, " +
                    COLUMN_NEWS_TITLE + " TEXT, " +
                    COLUMN_NEWS_TEXT + " TEXT, " +
                    COLUMN_NEWS_TIMESTAMP + " INTEGER, " +
                    COLUMN_NEWS_PICTURE + " BLOB, " +
                    "FOREIGN KEY (" + COLUMN_NEWS_CREATED_BY_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                    "UNIQUE (" + COLUMN_NEWS_CREATED_BY_ID + ", " + COLUMN_NEWS_TITLE + ") " +
                    ");";

    private static final String createApplicant =
            "CREATE TABLE " + TABLE_APPLICANT + "(" +
                    COLUMN_APPLICANT_RECORD + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_APPLICANT_APPLICANT_ID + " INTEGER, " +
                    COLUMN_APPLICANT_REQUEST_ID + " INTEGER, " +
                    COLUMN_APPLICANT_CREATOR_ID + " INTEGER, " +
                    COLUMN_APPLICANT_TIMESTAMP + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_APPLICANT_APPLICANT_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_APPLICANT_REQUEST_ID + ") REFERENCES " + TABLE_REQUEST + "(" + COLUMN_REQUESTS_REQUEST_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_APPLICANT_CREATOR_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                    "UNIQUE ( " + COLUMN_APPLICANT_APPLICANT_ID + ", " + COLUMN_APPLICANT_REQUEST_ID + " )" +
                    ");";

    private static final String createMessage =
            "CREATE TABLE " + TABLE_MESSAGE + "(" +
                    COLUMN_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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
                    COLUMN_CHATS_CREATED_AT + " INTEGER, " +
                    COLUMN_CHATS_LATEST_MSG_TIME + " INTEGER, " +
                    COLUMN_CHATS_LATEST_VIEW_BY_USER_1 + " INTEGER, " +
                    COLUMN_CHATS_LATEST_VIEW_BY_USER_2 + " INTEGER, " +
                    COLUMN_CHATS_REQUEST_ID + " INTEGER, " +
                    COLUMN_CHATS_USER_1 + " INTEGER, " +
                    COLUMN_CHATS_USER_2 + " INTEGER, " +
                    COLUMN_CHATS_ACCEPTED_USER_1 + " INTEGER, " +
                    COLUMN_CHATS_ACCEPTED_USER_2 + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_CHATS_USER_1 + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_CHATS_USER_2 + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_CHATS_REQUEST_ID + ") REFERENCES " + TABLE_REQUEST + "(" + COLUMN_REQUESTS_REQUEST_ID + "), " +
                    "UNIQUE (" + COLUMN_CHATS_USER_1 + ", " + COLUMN_CHATS_USER_2 + ", " + COLUMN_CHATS_REQUEST_ID + ") " +
                    ");";


    private final Bitmap DEFAULT_PROFILE_PIC;


    public static synchronized DBHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DBHelper(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        DEFAULT_PROFILE_PIC = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_account_circle_black_36dp);
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
            c.close();
            db.close();
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
            values.putNull(COLUMN_USER_PICTURE);
        } else {
            Bitmap yourBitmap = user.getImage();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            yourBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            values.put(COLUMN_USER_PICTURE, bArray);
        }

        long insertedUserId = db.insert(TABLE_USER, null, values);
        User resultUser = new User(insertedUserId, user.getUsername(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getEmail(), user.getPhone(), user.getImage());

        // log address
        ContentValues addressValues = new ContentValues();
        addressValues.put(COLUMN_ADDRESS_USER_ID, insertedUserId);
        addressValues.put(COLUMN_ADDRESS_STREET, address.getAddress());
        addressValues.put(COLUMN_ADDRESS_RECT_X, address.getRectX());
        addressValues.put(COLUMN_ADDRESS_RECT_Y, address.getRextY());
        db.insert(TABLE_ADDRESS, null, addressValues);

        // create default rating
        Rating defaultRating = new Rating(resultUser, 0, 0, 0);
        ContentValues ratingValues = new ContentValues();
        ratingValues.put(COLUMN_RATING_USER_ID, defaultRating.getUser().getId());
        ratingValues.put(COLUMN_RATING_AS_REQUESTER, defaultRating.getRatingAsRequester());
        ratingValues.put(COLUMN_RATING_AS_APPLICANT, defaultRating.getRatingAsApplicant());
        ratingValues.put(COLUMN_RATING_ENDORCEDBY, defaultRating.getEndorsedBy());
        db.insert(TABLE_RATING, null, ratingValues);

        c.close();
        db.close();

        resultUser.setRating(defaultRating);
        resultUser.setAddress(address);

        return resultUser;
    }

    public User getUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        String authenticate =
                "SELECT * FROM " + TABLE_USER +
                        " WHERE " + COLUMN_USER_USERNAME + "=\"" + username + "\" " +
                        " AND " + COLUMN_USER_PASSWORD + "=\"" + password + "\" " + ";";

        Cursor c = db.rawQuery(authenticate, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            User userFromCursor = createUserFromCursor(c);
            c.close();
            db.close();
            return userFromCursor;
        }
        c.close();
        db.close();
        return null;
    }

    public User getUser(long userId) {
        SQLiteDatabase db = getReadableDatabase();
        String authenticate =
                "SELECT * FROM " + TABLE_USER +
                        " WHERE " + COLUMN_USER_ID + " = " + userId + ";";
        Cursor c = db.rawQuery(authenticate, null);

        if (c.getCount() > 0) {
            c.moveToFirst();
            User userFromCursor = createUserFromCursor(c);
            c.close();
            db.close();
            return userFromCursor;
        }
        c.close();
        db.close();
        return null;
    }

    private User createUserFromCursor(Cursor c) {

        SQLiteDatabase db = getWritableDatabase();
        long userId = c.getLong(c.getColumnIndex(COLUMN_USER_ID));
        String username = c.getString(c.getColumnIndex(COLUMN_USER_USERNAME));
        String fName = c.getString(c.getColumnIndex(COLUMN_USER_FIRSTNAME));
        String lName = c.getString(c.getColumnIndex(COLUMN_USER_LASTNAME));
        String password = c.getString(c.getColumnIndex(COLUMN_USER_PASSWORD));
        String email = c.getString(c.getColumnIndex(COLUMN_USER_EMAIL));
        String phone = c.getString(c.getColumnIndex(COLUMN_USER_PHONE));

        byte[] p = c.getBlob(c.getColumnIndex(COLUMN_USER_PICTURE));
        Bitmap image;
        if (p != null) {
            image = BitmapFactory.decodeByteArray(p, 0, p.length);
        } else {
            image = DEFAULT_PROFILE_PIC;
        }
        User user = new User(userId, username, fName, lName, password, email, phone, image);

        String ratingQuery = "SELECT * FROM " + TABLE_RATING + " WHERE " + COLUMN_RATING_USER_ID + " = " + userId;
        String addressQuery = "SELECT * FROM " + TABLE_ADDRESS + " WHERE " + COLUMN_RATING_USER_ID + " = " + userId;

        Cursor ratingC = db.rawQuery(ratingQuery, null);
        ratingC.moveToFirst();
        int ratingAsRequester = ratingC.getInt(ratingC.getColumnIndex(COLUMN_RATING_AS_REQUESTER));
        int ratingAsApplicant = ratingC.getInt(ratingC.getColumnIndex(COLUMN_RATING_AS_APPLICANT));
        int endorsedBy = ratingC.getInt(ratingC.getColumnIndex(COLUMN_RATING_ENDORCEDBY));
        Rating userRating = new Rating(user, ratingAsRequester, ratingAsApplicant, endorsedBy);
        ratingC.close();

        Cursor addressC = db.rawQuery(addressQuery, null);
        addressC.moveToFirst();
        String street = addressC.getString(addressC.getColumnIndex(COLUMN_ADDRESS_STREET));
        double rectX = addressC.getDouble(addressC.getColumnIndex(COLUMN_ADDRESS_RECT_X));
        double rectY = addressC.getDouble(addressC.getColumnIndex(COLUMN_ADDRESS_RECT_Y));
        Address address = new Address(user, street, rectX, rectY);
        addressC.close();


        user.setAddress(address);
        user.setRating(userRating);

        db.close();
        return user;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> toReturn = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String authenticate =
                "SELECT * FROM " + TABLE_USER + ";";
        Cursor c = db.rawQuery(authenticate, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            User u = createUserFromCursor(c);
            toReturn.add(u);
            c.moveToNext();
        }

        c.close();
        db.close();
        return toReturn;
    }


    public Request addRequest(Request request) {
        // check if request exists already


        ContentValues values = new ContentValues();
        values.put(COLUMN_REQUEST_CREATED_BY_ID, request.getCreator().getId());
        values.put(COLUMN_REQUEST_TITLE, request.getTitle());
        values.put(COLUMN_REQUEST_DESCRIPTION, request.getDescription());
        values.put(COLUMN_REQUEST_PEOPLE_NEEDED, request.getPeopleNeeded());
        values.put(COLUMN_REQUEST_TIMESTAMP, request.getTimestamp());
        values.put(COLUMN_REQUEST_EXPIRES, request.getExpires());
        values.put(COLUMN_REQUEST_ACCEPTED, request.getAccepted());
        values.put(COLUMN_REQUEST_STATUS, request.getStatus());
        SQLiteDatabase db = getWritableDatabase();
        long insertedId = db.insert(TABLE_REQUEST, null, values);
        db.close();


        if (insertedId == -1) {
            System.out.println("addRequest -1");
            return null;
        } else {
            request.setId(insertedId);
        }
        return request;
    }

    public Request getRequest(long creatorId, String title) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_REQUEST + " WHERE " + COLUMN_REQUEST_CREATED_BY_ID + " = " + creatorId + " AND " + COLUMN_REQUEST_TITLE + " = \"" + title + "\"";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            Request requestFromCursor = createRequestFromCursor(cursor);
            cursor.close();
            db.close();
            return requestFromCursor;
        }
        cursor.close();
        db.close();
        return null;
    }

    public Request getRequest(long requestId) {
        SQLiteDatabase db = getReadableDatabase();
        String getRequest =
                "SELECT * FROM " + TABLE_REQUEST +
                        " WHERE " + COLUMN_REQUESTS_REQUEST_ID + " = " + requestId + ";";
        Cursor c = db.rawQuery(getRequest, null);

        if (c.getCount() > 0) {
            c.moveToFirst();
            Request requestFromCursor = createRequestFromCursor(c);
            c.close();
            db.close();
            return requestFromCursor;
        }
        c.close();
        db.close();
        return null;
    }

    private Request createRequestFromCursor(Cursor c) {
        long creatorId = c.getLong(c.getColumnIndex(COLUMN_REQUEST_CREATED_BY_ID));
        User creator = getUser(creatorId);
        String title = "";
        String description = "";
        long requestId = c.getLong(c.getColumnIndex(COLUMN_REQUESTS_REQUEST_ID));
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
        int status = c.getInt(c.getColumnIndex(COLUMN_REQUEST_STATUS));
        return new Request(requestId, creator, title, description, peopleNeeded, timestamp, expires, accepted, status);
    }

    // get myRequests or feedRequests
    public ArrayList<Request> getRequests(long userId, String feedOrMy) {
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

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(queryToExecute, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Request r = createRequestFromCursor(c);
            toReturn.add(r);

            c.moveToNext();
        }
        c.close();
        db.close();
        return toReturn;
    }

    public ArrayList<News> getNews() {
        long newsId;
        long userId;
        String title = "";
        String text = "";
        long timestamp = 0;
        Bitmap picture = null;
        ArrayList<News> toReturn = new ArrayList<>();

        String queryToExecute =
                "SELECT * FROM " + TABLE_NEWS;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(queryToExecute, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            newsId = c.getInt(c.getColumnIndex(COLUMN_NEWS_ID));
            userId = c.getLong(c.getColumnIndex(COLUMN_NEWS_CREATED_BY_ID));

            User creator = getUser(userId);
            if (c.getString(c.getColumnIndex(COLUMN_NEWS_TITLE)) != null) {
                title = c.getString(c.getColumnIndex(COLUMN_NEWS_TITLE));
            }
            if (c.getString(c.getColumnIndex(COLUMN_NEWS_TEXT)) != null) {
                text = c.getString(c.getColumnIndex(COLUMN_NEWS_TEXT));
            }
            if (c.getString(c.getColumnIndex(COLUMN_NEWS_TIMESTAMP)) != null) {
                timestamp = c.getLong(c.getColumnIndex(COLUMN_NEWS_TIMESTAMP));
            }
            byte[] p = c.getBlob(c.getColumnIndex(COLUMN_NEWS_PICTURE));
            picture = BitmapFactory.decodeByteArray(p, 0, p.length);
            toReturn.add(new News(newsId, creator, title, text, timestamp, picture));

            c.moveToNext();
        }
        c.close();
        db.close();
        return toReturn;
    }

    public Chat addChat(User user1, User user2, Request request) {
        ContentValues values = new ContentValues();
        long now = System.currentTimeMillis();
        values.put(COLUMN_CHATS_CREATED_AT, now);
        values.put(COLUMN_CHATS_REQUEST_ID, request.getId());
        values.put(COLUMN_CHATS_USER_1, user1.getId());
        values.put(COLUMN_CHATS_USER_2, user2.getId());
        values.putNull(COLUMN_CHATS_LATEST_MSG_TIME);
        values.put(COLUMN_CHATS_LATEST_VIEW_BY_USER_1, now);
        values.put(COLUMN_CHATS_LATEST_VIEW_BY_USER_2, now);
        values.put(COLUMN_CHATS_ACCEPTED_USER_1, 0);
        values.put(COLUMN_CHATS_ACCEPTED_USER_2, 0);

        long insertedChatId = getWritableDatabase().insert(TABLE_CHATS, null, values);
        System.out.println("chat added : " + insertedChatId);
        if (insertedChatId == -1) {
            return getExistingChat(user1, user2, request);
        } else {
            Chat chat = new Chat(insertedChatId, new Date(now), request, user1, user2, null, new Date(), new Date(), false, false);
            return chat;
        }
    }

    public Chat getExistingChat(User user1, User user2, Request request) {
        String queryChats = "SELECT * FROM " + TABLE_CHATS + " WHERE (("
                + COLUMN_CHATS_USER_1 + " = " + user1.getId() + " AND " + COLUMN_CHATS_USER_2 + " = " + user2.getId() + ") OR ("
                + COLUMN_CHATS_USER_1 + " = " + user2.getId() + " AND " + COLUMN_CHATS_USER_2 + " = " + user1.getId() + ")) AND "
                + COLUMN_CHATS_REQUEST_ID + " = " + request.getId();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(queryChats, null);

        c.moveToFirst();
        Chat chatFromCursor = createChatFromCursor(c);
        c.close();
        db.close();
        return chatFromCursor;
    }


    public Message addMessage(Message msg) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MESSAGES_TEXT, msg.getText());
        values.put(COLUMN_MESSAGES_TIMESTAMP, msg.getTimestamp().getTime());
        values.put(COLUMN_MESSAGES_CHAT_FK, msg.getChatId());
        values.put(COLUMN_MESSAGES_FROM_USER_FK, msg.getFromUser().getId());
        values.put(COLUMN_MESSAGES_TO_USER_FK, msg.getToUser().getId());

        SQLiteDatabase db = getWritableDatabase();

        long insertedId = db.insert(TABLE_MESSAGE, null, values);

        if (insertedId == -1) {
            db.close();
            return null;
        } else {
            msg.setId(insertedId);

            String updateChatQuery = "UPDATE " + TABLE_CHATS + " SET " + COLUMN_CHATS_LATEST_MSG_TIME + " = " + msg.getTimestamp().getTime() + " WHERE " + COLUMN_CHATS_ID + " = " + msg.getChatId();
            db.execSQL(updateChatQuery);
            db.close();
            return msg;
        }

    }

    public ArrayList<Chat> getChatsForUser(User user) {
        ArrayList<Chat> chats = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String queryChats = "SELECT * FROM " + TABLE_CHATS + " WHERE " + COLUMN_CHATS_USER_1 + " = " + user.getId() + " OR " + COLUMN_CHATS_USER_2 + " = " + user.getId() + " ORDER BY " + COLUMN_CHATS_LATEST_MSG_TIME + " DESC ";

        Cursor cChats = db.rawQuery(queryChats, null);
        if (cChats.getCount() > 0) {
            cChats.moveToFirst();
            while (!cChats.isAfterLast()) {
                Chat chat = createChatFromCursor(cChats);
                chats.add(chat);
                cChats.moveToNext();
            }
        }
        cChats.close();
        return chats;
    }

    private Chat createChatFromCursor(Cursor cursor) {
        long chatId = cursor.getLong(cursor.getColumnIndex(COLUMN_CHATS_ID));
        long createdAtTime = cursor.getLong(cursor.getColumnIndex(COLUMN_CHATS_CREATED_AT));
        long requestId = cursor.getLong(cursor.getColumnIndex(COLUMN_CHATS_REQUEST_ID));
        long userId1 = cursor.getLong(cursor.getColumnIndex(COLUMN_CHATS_USER_1));
        long userId2 = cursor.getLong(cursor.getColumnIndex(COLUMN_CHATS_USER_2));
        long latestMsgTime = cursor.getLong(cursor.getColumnIndex(COLUMN_CHATS_LATEST_MSG_TIME));
        long latestViewByUser1Time = cursor.getLong(cursor.getColumnIndex(COLUMN_CHATS_LATEST_VIEW_BY_USER_1));
        long latestViewByUser2Time = cursor.getLong(cursor.getColumnIndex(COLUMN_CHATS_LATEST_VIEW_BY_USER_2));
        int acceptedUser1 = cursor.getInt(cursor.getColumnIndex(COLUMN_CHATS_ACCEPTED_USER_1));
        int acceptedUser2 = cursor.getInt(cursor.getColumnIndex(COLUMN_CHATS_ACCEPTED_USER_2));

        Date createdAt = new Date(createdAtTime);
        Request request = getRequest(requestId);
        User user1 = getUser(userId1);
        User user2 = getUser(userId2);
        Date latestMsgDate = new Date(latestMsgTime);
        Date latestViewByUser1Date = new Date(latestViewByUser1Time);
        Date latestViewByUser2Date = new Date(latestViewByUser2Time);

        return new Chat(chatId, createdAt, request, user1, user2, latestMsgDate, latestViewByUser1Date, latestViewByUser2Date, acceptedUser1 == 1 , acceptedUser2 == 1);
    }

    public void addNews(News news) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NEWS_CREATED_BY_ID, news.getCreator().getId());
        values.put(COLUMN_NEWS_TITLE, news.getTitle());
        values.put(COLUMN_NEWS_TEXT, news.getText());
        values.put(COLUMN_NEWS_TIMESTAMP, news.getTimestamp());

        if (news.getPicture() == null) {
            values.put(COLUMN_NEWS_PICTURE, "");
        } else {
            //Bitmap yourBitmap = null;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            news.getPicture().compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            values.put(COLUMN_NEWS_PICTURE, bArray);
        }

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NEWS, null, values);
        db.close();
    }

    public News getNews(long id) {
        long newsId;
        long userId;
        String title = "";
        String text = "";
        long timestamp = 0;
        Bitmap picture = null;

        String queryToExecute =
                "SELECT * FROM " + TABLE_NEWS + " WHERE " + COLUMN_NEWS_ID + "=\"" + id + "\" " + ";";

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(queryToExecute, null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            newsId = c.getLong(c.getColumnIndex(COLUMN_NEWS_ID));
            userId = c.getLong(c.getColumnIndex(COLUMN_NEWS_CREATED_BY_ID));
            User creator = getUser(userId);

            if (c.getString(c.getColumnIndex(COLUMN_NEWS_TITLE)) != null) {
                title = c.getString(c.getColumnIndex(COLUMN_NEWS_TITLE));
            }
            if (c.getString(c.getColumnIndex(COLUMN_NEWS_TEXT)) != null) {
                text = c.getString(c.getColumnIndex(COLUMN_NEWS_TEXT));
            }
            if (c.getString(c.getColumnIndex(COLUMN_NEWS_TIMESTAMP)) != null) {
                timestamp = c.getLong(c.getColumnIndex(COLUMN_NEWS_TIMESTAMP));
            }

            byte[] p = c.getBlob(c.getColumnIndex(COLUMN_NEWS_PICTURE));
            picture = BitmapFactory.decodeByteArray(p, 0, p.length);

            db.close();
            c.close();
            return new News(newsId, creator, title, text, timestamp, picture);
        }
        c.close();
        db.close();
        return null;
    }

    public void deleteAdminViliChat(User admin, User vili) {
        String query = "DELETE FROM " + TABLE_CHATS + " WHERE ( " + COLUMN_CHATS_USER_1 + " = " + admin.getId() + " AND " + COLUMN_CHATS_USER_2 + " = " + vili.getId() + " ) OR ( " +
                COLUMN_CHATS_USER_1 + " = " + vili.getId() + " AND " + COLUMN_CHATS_USER_2 + " = " + admin.getId() + " ) ";

        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.execSQL(query);
        writableDatabase.close();
    }

    public void deleteUser(User user) {
        System.out.println("deleting user : " + user.getUsername());
        SQLiteDatabase db = getWritableDatabase();
        try {
            String query = "DELETE FROM " + TABLE_USER + " WHERE " + COLUMN_USER_USERNAME + " = " + user.getUsername();

            db.execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }

    public ArrayList<Message> getMessagesForChat(long chatId) {
        ArrayList<Message> messages = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_MESSAGE + " WHERE " + COLUMN_MESSAGES_CHAT_FK + " = " + chatId + " ORDER BY " + COLUMN_MESSAGES_TIMESTAMP + " ASC ";

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            long msgId = c.getLong(c.getColumnIndex(COLUMN_MESSAGE_ID));
            String msgText = c.getString(c.getColumnIndex(COLUMN_MESSAGES_TEXT));
            long timestamp = c.getLong(c.getColumnIndex(COLUMN_MESSAGES_TIMESTAMP));
            long fromUserId = c.getLong(c.getColumnIndex(COLUMN_MESSAGES_FROM_USER_FK));
            long toUserId = c.getLong(c.getColumnIndex(COLUMN_MESSAGES_TO_USER_FK));

            Message msg = new Message(msgId, new Date(timestamp), chatId, msgText, getUser(fromUserId), getUser(toUserId));
            messages.add(msg);
        }
        c.close();
        db.close();
        return messages;
    }

//    private static final String createApplicant =
//            "CREATE TABLE " + TABLE_APPLICANT + "(" +
//                    COLUMN_APPLICANT_RECORD + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    COLUMN_APPLICANT_APPLICANT_ID + " INTEGER, " +
//                    COLUMN_APPLICANT_REQUEST_ID + " INTEGER, " +
//                    COLUMN_APPLICANT_CREATOR_ID + " INTEGER, " +
//                    COLUMN_APPLICANT_TIMESTAMP + " INTEGER, " +
//                    "FOREIGN KEY (" + COLUMN_APPLICANT_APPLICANT_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
//                    "FOREIGN KEY (" + COLUMN_APPLICANT_REQUEST_ID + ") REFERENCES " + TABLE_REQUEST + "(" + COLUMN_REQUESTS_REQUEST_ID + "), " +
//                    "FOREIGN KEY (" + COLUMN_APPLICANT_CREATOR_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + ")" +
//                    ");";

    public boolean addApplicant(long applicantId, long requestId, long creatorId){

        ContentValues values = new ContentValues();
        long now = System.currentTimeMillis();
        values.put(COLUMN_APPLICANT_APPLICANT_ID, applicantId);
        values.put(COLUMN_APPLICANT_REQUEST_ID, requestId);
        values.put(COLUMN_APPLICANT_CREATOR_ID, creatorId);
        values.put(COLUMN_APPLICANT_TIMESTAMP, now);

        long insertedApplicantRecord = getWritableDatabase().insert(TABLE_APPLICANT, null, values);
        System.out.println("ADD APPLICANT " + insertedApplicantRecord);
        if (insertedApplicantRecord == -1) {
            return false;
        }
        return true;
    }

    public ArrayList<User> getApplicants(long requestId) {
        ArrayList<User> applicants = new ArrayList<>();

        String query = "SELECT " + COLUMN_APPLICANT_APPLICANT_ID +" FROM " + TABLE_APPLICANT
                     + " WHERE " + COLUMN_APPLICANT_REQUEST_ID + " = " + requestId;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            long applicantId = c.getLong(c.getColumnIndex(COLUMN_APPLICANT_APPLICANT_ID));
            User u = getUser(applicantId);
            applicants.add(u);
        }
        c.close();
        db.close();
        return applicants;
    }

    public Chat getChat(long chatId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CHATS + " WHERE " + COLUMN_CHATS_ID + " = " + chatId;

        Chat chat = null;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            chat = createChatFromCursor(cursor);
        }
        cursor.close();
        db.close();
        return chat;
    }

    public void updateAccepted(User user, Chat chat){
        System.out.println("DB Helper updateAccepted");
        String query = "";
        if(chat.getUser1().getId() == user.getId()){
            query = "UPDATE " + TABLE_CHATS + " SET " + COLUMN_CHATS_ACCEPTED_USER_1 + " = " + (chat.isAcceptedUser1() ? 1: 0)
                + " WHERE " + COLUMN_CHATS_ID + " = " + chat.getId();
        }
        else query = "UPDATE " + TABLE_CHATS + " SET " + COLUMN_CHATS_ACCEPTED_USER_2 + " = " + (chat.isAcceptedUser2() ? 1 : 0)
                    + " WHERE " + COLUMN_CHATS_ID + " = " + chat.getId();

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public boolean acceptRequest(Request request) {
        boolean result = true;
        String query = "UPDATE " + TABLE_REQUEST + " SET " + COLUMN_REQUEST_ACCEPTED + " = " + 1 + " WHERE " + COLUMN_REQUESTS_REQUEST_ID + " = " +  request.getId();
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(query);
            request.setAccepted(1);
        } catch (Exception e){
            e.printStackTrace();
            result = false;
        }
        db.close();
        return  result;
    }

    public void requestStatusUpdate(Request request) {
        String query = "UPDATE " + TABLE_REQUEST + " SET " + COLUMN_REQUEST_STATUS + " = " + request.getStatus() + " WHERE " + COLUMN_REQUESTS_REQUEST_ID + " = " +   request.getId();
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public ArrayList<Request> getAllRequests() {
        ArrayList<Request> toReturn = new ArrayList<>();

        String getRequests =
                "SELECT * FROM " + TABLE_REQUEST;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(getRequests, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Request r = createRequestFromCursor(c);
            toReturn.add(r);

            c.moveToNext();
        }
        c.close();
        db.close();
        return toReturn;
    }
}
