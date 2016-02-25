package com.myneighbourhood.utils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by velin on 25/02/2016.
 */
public class Chat {
    int id;
    Request request;
    User user1;
    User user2;
    Date latestMsg;
    Date latestViewByUser1;
    Date latestViewByUser2;
    ArrayList<Message> messages;

    public Chat(int id, Request request, User user1, User user2, Date latestMsg, Date latestViewByUser1, Date latestViewByUser2, ArrayList<Message> messages) {
        this.id = id;
        this.request = request;
        this.user1 = user1;
        this.user2 = user2;
        this.latestMsg = latestMsg;
        this.latestViewByUser1 = latestViewByUser1;
        this.latestViewByUser2 = latestViewByUser2;
        this.messages = messages;
    }



}
