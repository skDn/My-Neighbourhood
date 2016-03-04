package com.myneighbourhood.utils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by velin on 25/02/2016.
 */
public class Chat {
    long id;
    Request request;
    User user1;
    User user2;

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    Date createdAt;
    Date latestMsgDate;
    Date latestViewByUser1Date;
    Date latestViewByUser2Date;
    boolean acceptedUser1;
    boolean acceptedUser2;
    ArrayList<Message> messages;

    public Chat(long id, Date createdAt, Request request, User user1, User user2, Date latestMsgDate, Date latestViewByUser1Date, Date latestViewByUser2Date, boolean acceptedUser1, boolean acceptedUser2) {
        this.id = id;
        this.createdAt = createdAt;
        this.request = request;
        this.user1 = user1;
        this.user2 = user2;
        this.latestMsgDate = latestMsgDate;
        this.latestViewByUser1Date = latestViewByUser1Date;
        this.latestViewByUser2Date = latestViewByUser2Date;
        this.acceptedUser1 = acceptedUser1;
        this.acceptedUser2 = acceptedUser2;

        System.out.println("new chat between : " + user1.getUsername() + " and " + user2.getUsername());

    }

    public long getId() {
        return id;
    }

    public User getOtherUser(User user) {
        return user.getId() == user1.getId() ? user2 : user1;
    }

    public Date getLatestMsgDate() {
        return latestMsgDate;
    }

    public void addMsg(Message msg) {
        if (msg != null) {
            if (messages == null) {
                messages = new ArrayList<>();
            }
            messages.add(msg);
        }
    }

    public Request getRequest() {
        return request;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setChecked(User user, boolean checked) {
        if (user1.getId() == user.getId()) {
            System.out.println("CHAT setting accepted 1");
            setAcceptedUser1(checked);
        } else if (user2.getId() == user.getId()) {
            setAcceptedUser2(checked);
        }
    }

    public boolean isAcceptedUser2() {
        return acceptedUser2;
    }

    public void setAcceptedUser2(boolean acceptedUser2) {
        this.acceptedUser2 = acceptedUser2;
    }

    public boolean isAcceptedUser1() {
        return acceptedUser1;
    }

    public void setAcceptedUser1(boolean acceptedUser1) {
        this.acceptedUser1 = acceptedUser1;
    }

    public boolean getAcceptedBy(User user) {
        if (user.getId() == user1.getId()) {
            return isAcceptedUser1();
        } else {
            return isAcceptedUser2();
        }
    }
}
