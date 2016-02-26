package com.myneighbourhood.utils;

import android.graphics.Bitmap;

/**
 * Created by skDn on 23/02/2016.
 */
public class News {
    private long newsId;
    private User creator;
    private String title;
    private String text;
    private long timestamp;
    private Bitmap picture;

    public News(long newsId, User creator, String title, String text, long timestamp, Bitmap picture) {
        this.newsId = newsId;
        this.creator = creator;
        this.title = title;
        this.text = text;
        this.timestamp = timestamp;
        this.picture = picture;
    }

    public News(User creator, String title, String text, long timestamp, Bitmap picture) {
        this.creator = creator;
        this.title = title;
        this.text = text;
        this.timestamp = timestamp;
        this.picture = picture;
    }


    public Bitmap getPicture() {
        return picture;
    }

    public long getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public User getCreator() {
        return creator;
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
