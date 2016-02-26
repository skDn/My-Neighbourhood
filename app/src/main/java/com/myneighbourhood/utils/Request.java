package com.myneighbourhood.utils;

/**
 * Created by Kiril on 19/02/16.
 */
public class Request {

    private long id;
    private User creator;
    private String title;
    private String description;
    private int peopleNeeded;
    private long timestamp;
    private long expires;
    private int accepted;

    // to use when creating Request
    public Request(User creator, String title, String description, int peopleNeeded, long expires){
        this.creator=creator;
        this.title = title;
        this.description = description;
        this.peopleNeeded = peopleNeeded;
        this.expires = expires;

        // defaults
        this.timestamp = System.currentTimeMillis();
        this.accepted = 0;
    }

    // to use when fetching from DB
    public Request(long id, User creator, String title, String description, int peopleNeeded, long timestamp, long expires, int accepted){
        System.out.println("creator: " + creator);
        this.id = id;
        this.creator = creator;
        this.title = title;
        this.description = description;
        this.peopleNeeded = peopleNeeded;
        this.timestamp = timestamp;
        this.expires = expires;
        this.accepted = accepted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPeopleNeeded() {
        return peopleNeeded;
    }

    public void setPeopleNeeded(int peopleNeeded) {
        this.peopleNeeded = peopleNeeded;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

}
