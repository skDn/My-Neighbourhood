package com.myneighbourhood.utils;

import java.util.Date;

/**
 * Created by kirchoni on 25/02/16.
 */
public class Message {
    long id;
    final Date msgTime;
    final long chatId;
    final String text;
    final User fromUser;
    final User toUser;

    public Message(long id, Date msgTime, long chatId, String text, User fromUser, User toUser) {
        this.id = id;
        this.msgTime = msgTime;
        this.chatId = chatId;
        this.text = text;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    public String getText() {
        return text;
    }
}
