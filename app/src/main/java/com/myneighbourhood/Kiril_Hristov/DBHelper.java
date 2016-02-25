package com.myneighbourhood.Kiril_Hristov;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;

import com.myneighbourhood.utils.Address;
import com.myneighbourhood.utils.News;
import com.myneighbourhood.utils.Request;
import com.myneighbourhood.utils.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Kiril on 19/02/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper INSTANCE;

    private static final int DB_VERSION = 4;
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
            COLUMN_USER_FIRSTNAME + " TEXT, " +
            COLUMN_USER_LASTNAME + " TEXT, " +
            COLUMN_USER_PASSWORD + " TEXT, " +
            COLUMN_USER_EMAIL + " TEXT, " +
            COLUMN_USER_PHONE + " TEXT, " +
            COLUMN_USER_PICTURE + " BLOB" + ");";

    private static final String createRating =
            "CREATE TABLE " + TABLE_RATING + "(" +
            COLUMN_RATING_USER_ID + " INTEGER, " +
            COLUMN_RATING_AS_REQUESTER + " INTEGER, "+
            COLUMN_RATING_AS_APPLICANT + " INTEGER, " +
            COLUMN_RATING_ENDORCEDBY + " INTEGER, " +
            "FOREIGN KEY (" + COLUMN_RATING_USER_ID + ") REFERENCES " +
            TABLE_USER + "(" + COLUMN_USER_ID + ")"+
            ");";

    private static final String createAddress =
            "CREATE TABLE " + TABLE_ADDRESS + "(" +
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
            COLUMN_REQUEST_TIMESTAMP + " INTEGER, " +
            COLUMN_REQUEST_EXPIRES + " INTEGER, " +
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
            COLUMN_NEWS_TIMESTAMP + " INTEGER, " +
            COLUMN_NEWS_PICTURE + " BLOB, " +
            "FOREIGN KEY (" + COLUMN_NEWS_CREATED_BY_ID + ") REFERENCES " +
            TABLE_USER + "(" + COLUMN_USER_ID + ")"+
            ");";

    private static final String createApplicant =
            "CREATE TABLE " + TABLE_APPLICANT + "(" +
            COLUMN_APPLICANT_RECORD + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_APPLICANT_APPLICANT_ID + " INTEGER, " +
            COLUMN_APPLICANT_REQUEST_ID + " INTEGER, " +
            COLUMN_APPLICANT_CREATOR_ID + " INTEGER, " +
            COLUMN_APPLICANT_TIMESTAMP + " INTEGER, " +
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
            COLUMN_MESSAGES_TIMESTAMP + " INTEGER, " +
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
        db.execSQL(createRating);
        db.execSQL(createAddress);
        db.execSQL(createRequest);
        db.execSQL(createNews);
        db.execSQL(createApplicant);
        db.execSQL(createMessage);
        registerUser(new User("admin", "fName", "lName", "pass", "mail@mail.com", "080808", null), new Address("100 Gibson Street", 55.8734611, -4.2890117));
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
        onCreate(db);
    }

    public User registerUser(User user, Address address) {
        SQLiteDatabase db = getWritableDatabase();
        String checkUnique =
                "SELECT * FROM " + TABLE_USER +
                " WHERE " + COLUMN_USER_USERNAME + "=\"" + user.getUsername() + "\";";
        Cursor c = db.rawQuery(checkUnique, null);
        c.moveToFirst();
        if (c.getCount() > 0){
            // user with that username already exists
            return null;
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_FIRSTNAME, user.getFirstName());
        values.put(COLUMN_USER_LASTNAME, user.getLastName());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PHONE, user.getPhone());

        if(user.getImage() == null){
            values.put(COLUMN_USER_PICTURE, "");
        }
        else {
            Bitmap yourBitmap = null;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            yourBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            values.put(COLUMN_USER_PICTURE, bArray);
        }

        db.insert(TABLE_USER, null, values);

        int id=0;
        c = db.rawQuery(checkUnique, null);
        c.moveToFirst();
        if (c.getCount() > 0){
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
        return new User(id, user.getUsername(), user.getFirstName(), user.getLastName(), user.getPassword(),user.getEmail(), user.getPhone(), user.getImage());
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
        if (c.getCount() > 0){
            id = c.getInt(c.getColumnIndex(COLUMN_USER_ID));
            if(c.getString(c.getColumnIndex(COLUMN_USER_FIRSTNAME)) != null){
                fName = c.getString(c.getColumnIndex(COLUMN_USER_FIRSTNAME));
            }
            if(c.getString(c.getColumnIndex(COLUMN_USER_LASTNAME)) != null){
                lName = c.getString(c.getColumnIndex(COLUMN_USER_LASTNAME));
            }
            if(c.getString(c.getColumnIndex(COLUMN_USER_EMAIL)) != null){
                email = c.getString(c.getColumnIndex(COLUMN_USER_EMAIL));
            }
            if(c.getString(c.getColumnIndex(COLUMN_USER_PHONE))!=null){
                phone = c.getString(c.getColumnIndex(COLUMN_USER_PHONE));
            }

            if(c.getBlob(c.getColumnIndex(COLUMN_USER_PICTURE))!=null){
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
        String username= "";
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
        if (c.getCount() > 0){
            if(c.getString(c.getColumnIndex(COLUMN_USER_USERNAME)) != null){
                username = c.getString(c.getColumnIndex(COLUMN_USER_USERNAME));
            }
            if(c.getString(c.getColumnIndex(COLUMN_USER_FIRSTNAME)) != null){
                fName = c.getString(c.getColumnIndex(COLUMN_USER_FIRSTNAME));
            }
            if(c.getString(c.getColumnIndex(COLUMN_USER_LASTNAME)) != null){
                lName = c.getString(c.getColumnIndex(COLUMN_USER_LASTNAME));
            }
            if(c.getString(c.getColumnIndex(COLUMN_USER_PASSWORD)) != null){
                password = c.getString(c.getColumnIndex(COLUMN_USER_PASSWORD));
            }
            if(c.getString(c.getColumnIndex(COLUMN_USER_EMAIL)) != null){
                email = c.getString(c.getColumnIndex(COLUMN_USER_EMAIL));
            }
            if(c.getString(c.getColumnIndex(COLUMN_USER_PHONE))!=null){
                phone = c.getString(c.getColumnIndex(COLUMN_USER_PHONE));
            }
            if(c.getBlob(c.getColumnIndex(COLUMN_USER_PICTURE))!=null){
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

    public ArrayList<User> getUsers(){
        int id;
        String username= "";
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
        if (c.getCount() > 0){
            id = c.getInt(c.getColumnIndex(COLUMN_USER_ID));
            if(c.getString(c.getColumnIndex(COLUMN_USER_USERNAME)) != null){
                username = c.getString(c.getColumnIndex(COLUMN_USER_USERNAME));
            }
            if(c.getString(c.getColumnIndex(COLUMN_USER_FIRSTNAME)) != null){
                fName = c.getString(c.getColumnIndex(COLUMN_USER_FIRSTNAME));
            }
            if(c.getString(c.getColumnIndex(COLUMN_USER_LASTNAME)) != null){
                lName = c.getString(c.getColumnIndex(COLUMN_USER_LASTNAME));
            }
            if(c.getString(c.getColumnIndex(COLUMN_USER_PASSWORD)) != null){
                password = c.getString(c.getColumnIndex(COLUMN_USER_PASSWORD));
            }
            if(c.getString(c.getColumnIndex(COLUMN_USER_EMAIL)) != null){
                email = c.getString(c.getColumnIndex(COLUMN_USER_EMAIL));
            }
            if(c.getString(c.getColumnIndex(COLUMN_USER_PHONE))!=null){
                phone = c.getString(c.getColumnIndex(COLUMN_USER_PHONE));
            }
            if(c.getBlob(c.getColumnIndex(COLUMN_USER_PICTURE))!=null){
                byte[] p = c.getBlob(c.getColumnIndex(COLUMN_USER_PICTURE));
                image = BitmapFactory.decodeByteArray(p, 0, p.length);
            }
            c.close();
            db.close();
            toReturn.add(new User(id, username, fName, lName, password, email, phone, image));
        }
        c.close();
        db.close();
        return toReturn;
    }


    public void addRequest(Request request){
        ContentValues values = new ContentValues();
        values.put(COLUMN_REQUEST_CREATED_BY_ID, request.getCreatorId());
        values.put(COLUMN_REQUEST_TITLE, request.getTitle());
        values.put(COLUMN_REQUEST_DESCRIPTION, request.getDescription());
        values.put(COLUMN_REQUEST_PEOPLE_NEEDED, request.getPeopleNeeded());
        values.put(COLUMN_REQUEST_TIMESTAMP, request.getTimestamp());
        values.put(COLUMN_REQUEST_EXPIRES, request.getExpires());
        values.put(COLUMN_REQUEST_ACCEPTED, request.getAccepted());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public Request getRequest(int requestId) {
        int creatorId;
        String title= "";
        String description = "";
        int peopleNeeded;
        long timestamp = 0;
        String expires = "";
        int accepted;
        SQLiteDatabase db = getWritableDatabase();
        String getRequest =
                "SELECT * FROM " + TABLE_REQUEST +
                        " WHERE " + COLUMN_REQUEST_ID + " = " + requestId + ";";
        Cursor c = db.rawQuery(getRequest, null);
        c.moveToFirst();
        if (c.getCount() > 0){
            creatorId = c.getInt(c.getColumnIndex(COLUMN_REQUEST_CREATED_BY_ID));
            if(c.getString(c.getColumnIndex(COLUMN_REQUEST_TITLE)) != null){
                title = c.getString(c.getColumnIndex(COLUMN_REQUEST_TITLE));
            }
            if(c.getString(c.getColumnIndex(COLUMN_REQUEST_DESCRIPTION)) != null){
                description = c.getString(c.getColumnIndex(COLUMN_REQUEST_DESCRIPTION));
            }
            peopleNeeded = c.getInt(c.getColumnIndex(COLUMN_REQUEST_PEOPLE_NEEDED));
            if(c.getString(c.getColumnIndex(COLUMN_REQUEST_TIMESTAMP)) != null){
                timestamp = c.getLong(c.getColumnIndex(COLUMN_REQUEST_TIMESTAMP));
            }
            if(c.getString(c.getColumnIndex(COLUMN_REQUEST_EXPIRES)) != null){
                expires = c.getString(c.getColumnIndex(COLUMN_REQUEST_EXPIRES));
            }
            accepted = c.getInt(c.getColumnIndex(COLUMN_REQUEST_ACCEPTED));
            c.close();
            db.close();
            return new Request(requestId, creatorId, title, description, peopleNeeded, timestamp, expires, accepted);
        }
        c.close();
        db.close();
        return null;
    }

    // get myRequests or feedRequests
    public ArrayList<Request> getRequests(int userId, String feedOrMy) {
        int requestId;
        int creatorId;
        String title="";
        String description="";
        int peopleNeeded;
        long timestamp = 0;
        String expires = "";
        int accepted;
        ArrayList<Request> toReturn = new ArrayList<>();

        String getMyRequests =
                "SELECT * FROM " + TABLE_REQUEST +
                        " WHERE " + COLUMN_REQUEST_CREATED_BY_ID + " = " + userId + ";";
        String getFeedRequests =
                "SELECT * FROM " + TABLE_REQUEST +
                        " WHERE " + COLUMN_REQUEST_CREATED_BY_ID + " = " + userId + ";";

        String queryToExecute;
        if(feedOrMy.equals("feed")){
            queryToExecute = getMyRequests;
        }
        else queryToExecute = getFeedRequests;

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(queryToExecute, null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            requestId = c.getInt(c.getColumnIndex(COLUMN_REQUEST_ID));
            creatorId = c.getInt(c.getColumnIndex(COLUMN_REQUEST_CREATED_BY_ID));
            if(c.getString(c.getColumnIndex(COLUMN_REQUEST_TITLE)) != null){
                title = c.getString(c.getColumnIndex(COLUMN_REQUEST_TITLE));
            }
            if(c.getString(c.getColumnIndex(COLUMN_REQUEST_DESCRIPTION)) != null){
                description = c.getString(c.getColumnIndex(COLUMN_REQUEST_DESCRIPTION));
            }
            peopleNeeded = c.getInt(c.getColumnIndex(COLUMN_REQUEST_PEOPLE_NEEDED));
            if(c.getString(c.getColumnIndex(COLUMN_REQUEST_TIMESTAMP)) != null){
                timestamp = c.getLong(c.getColumnIndex(COLUMN_REQUEST_TIMESTAMP));
            }
            if(c.getString(c.getColumnIndex(COLUMN_REQUEST_EXPIRES)) != null){
                expires = c.getString(c.getColumnIndex(COLUMN_REQUEST_EXPIRES));
            }
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
//        String picture;
        ArrayList<News> toReturn = new ArrayList<>();

        String queryToExecute =
                "SELECT * FROM " + TABLE_NEWS;

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(queryToExecute, null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            newsId = c.getInt(c.getColumnIndex(COLUMN_NEWS_ID));
            userId = c.getInt(c.getColumnIndex(COLUMN_NEWS_CREATED_BY_ID));
            if(c.getString(c.getColumnIndex(COLUMN_NEWS_TITLE)) != null){
                title = c.getString(c.getColumnIndex(COLUMN_NEWS_TITLE));
            }
            if(c.getString(c.getColumnIndex(COLUMN_NEWS_TEXT)) != null){
                text = c.getString(c.getColumnIndex(COLUMN_NEWS_TEXT));
            }
            if(c.getString(c.getColumnIndex(COLUMN_NEWS_TIMESTAMP)) != null){
                timestamp = c.getLong(c.getColumnIndex(COLUMN_NEWS_TIMESTAMP));
            }

            toReturn.add(new News(newsId, userId, title, text, timestamp));

            c.moveToNext();
        }
        c.close();
        db.close();
        return toReturn;
    }

    //public ArrayList<Message>
}
