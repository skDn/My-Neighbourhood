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

    public Message(int id, Date msgTime, Chat chat, String text) {
        this.id = id;
        this.msgTime = msgTime;
        this.chat = chat;
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
