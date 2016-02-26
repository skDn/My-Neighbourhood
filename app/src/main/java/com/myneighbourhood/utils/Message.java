package com.myneighbourhood.utils;

import java.util.Date;

/**
 * Created by kirchoni on 25/02/16.
 */
public class Message {
    int id;
    final Date msgTime;
    final Chat chat;
    final String text;
    final User fromUser;
    final User toUser;

    public Message(int id, Date msgTime, Chat chat, String text, User fromUser, User toUser) {
        this.id = id;
        this.msgTime = msgTime;
        this.chat = chat;
        this.text = text;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    public String getText() {
        return text;
    }
}
