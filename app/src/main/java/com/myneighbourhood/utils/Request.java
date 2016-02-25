package com.myneighbourhood.utils;

/**
 * Created by Kiril on 19/02/16.
 */
public class Request {

    private int id;
    private int creatorId;
    private String title;
    private String description;
    private int peopleNeeded;
    private long timestamp;
    private String expires;
    private int accepted;

    // to use when creating Request
    public Request(int creatorId, String title, String description, int peopleNeeded, String expires){
        this.creatorId = creatorId;
        this.title = title;
        this.description = description;
        this.peopleNeeded = peopleNeeded;
        this.timestamp = System.currentTimeMillis();
        this.expires = expires;
        this.accepted = 0;
    }

    // to use when fetching from DB
    public Request(int id, int creatorId, String title, String description, int peopleNeeded, long timestamp, String expires, int accepted){
        this.id = id;
        this.creatorId = creatorId;
        this.title = title;
        this.description = description;
        this.peopleNeeded = peopleNeeded;
        this.timestamp = timestamp;
        this.expires = expires;
        this.accepted = accepted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
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

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

}
