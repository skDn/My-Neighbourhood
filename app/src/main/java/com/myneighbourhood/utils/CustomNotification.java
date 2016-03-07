package com.myneighbourhood.utils;

import java.util.Date;

/**
 * Created by velin on 07/03/2016.
 */
public class CustomNotification {
    public static Type getType(int notType) {
        switch (notType){
            case 1:
                return Type.NEW_MESSAGE;
            case 2:
                return Type.NEW_APPLICANT;
            case 3:
                return Type.NEW_REQUEST;
            default:
                return null;
        }
    }

    public enum Type {
        NEW_MESSAGE(1), NEW_APPLICANT(2), NEW_REQUEST(3);
        public int type;

        Type(int type) {
            this.type = type;
        }
    }

    private long id;
    private Type type;
    private User forUser;
    private User fromUser;
    private String text;
    private boolean isShown;
    private Date creationDate;

    public CustomNotification(Type type, User forUser, User fromUser) {
        this.type = type;
        this.forUser = forUser;
        this.fromUser = fromUser;
        text = stringFromType(type, forUser, fromUser);

        isShown = false;
        creationDate = new Date();
    }

    private String stringFromType(Type type, User toUser, User fromUser) {
        switch (type) {
            case NEW_MESSAGE:
                return fromUser.getUsername() + " send you a new message";
            case NEW_APPLICANT:
                return fromUser.getUsername() + " applied for one of your requests";
            case NEW_REQUEST:
                return fromUser.getUsername() + " created new request";
            default:
                return "Notification from " + fromUser.toString();
        }
    }

    public CustomNotification(Type type, User forUser, User fromUser, String text) {
        this.type = type;
        this.forUser = forUser;
        this.fromUser = fromUser;
        this.text = text;
        isShown = false;
        creationDate = new Date();
    }

    public CustomNotification(long id, Type type, User forUser, User fromUser, String text, boolean isShown, Date creationDate) {
        this.id = id;
        this.type = type;
        this.forUser = forUser;
        this.fromUser = fromUser;
        this.text = text;
    }

    public Type getType() {
        return type;
    }

    public User getForUser() {
        return forUser;
    }

    public User getFromUser() {
        return fromUser;
    }

    public String getText() {
        return text;
    }

    public boolean isShown() {
        return isShown;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
