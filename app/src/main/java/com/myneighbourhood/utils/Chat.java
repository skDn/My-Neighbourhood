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
    Date latestMsgDate;
    Date latestViewByUser1Date;
    Date latestViewByUser2Date;
    ArrayList<Message> messages;

    public Chat(long id, Request request, User user1, User user2, Date latestMsgDate, Date latestViewByUser1Date, Date latestViewByUser2Date, ArrayList<Message> messages) {
        this.id = id;
        this.request = request;
        this.user1 = user1;
        this.user2 = user2;
        this.latestMsgDate = latestMsgDate;
        this.latestViewByUser1Date = latestViewByUser1Date;
        this.latestViewByUser2Date = latestViewByUser2Date;
        this.messages = messages;

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
}
