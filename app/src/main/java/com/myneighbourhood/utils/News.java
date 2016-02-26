package com.myneighbourhood.utils;

import android.graphics.Bitmap;

/**
 * Created by skDn on 23/02/2016.
 */
public class News {
    private int newsId;
    private int userId;
    private String title;
    private String text;
    private long timestamp;
    private Bitmap picture;

    public News(int newsId, int userId, String title, String text, long timestamp, Bitmap picture) {
        this.newsId = newsId;
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.timestamp = timestamp;
        this.picture = picture;
    }

    public News(int userId, String title, String text, long timestamp, Bitmap picture) {
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.timestamp = timestamp;
        this.picture = picture;
    }


    public Bitmap getPicture() {
        return picture;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

//    public String getPicture() {
//        return picture;
//    }
//
//    public void setPicture(String picture) {
//        this.picture = picture;
//    }
}
