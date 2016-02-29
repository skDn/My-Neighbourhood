package com.myneighbourhood.Yordan_Yordanov;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.BaseActivity;
import com.myneighbourhood.utils.Message;
import com.myneighbourhood.utils.User;
import com.myneighbourhood.utils.Utils;

import java.util.ArrayList;

public class ChatActivity extends BaseActivity {

    private ArrayList<Message> messages;
    private ScrollView messagesSV;
    private EditText newMessageET;
    private TextView checkBox1Label;
    private TextView checkBox2Label;
    private CheckBox checkBoxUser1;
    private CheckBox checkBoxUser2;
    private User user1;
    private User user2;
    private User otherUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Chat");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        Bundle extras = getIntent().getExtras();
        long chatId = extras.getLong(Utils.EXTRA_CHAT_ID);
        long user1Id = extras.getLong(Utils.EXTRA_USER_1_ID);
        long user2Id = extras.getLong(Utils.EXTRA_USER_2_ID);

        System.out.println("user1 : " + user1Id + ", user2: " + user2Id);
        System.out.println("currentUser: " + user.getUsername() + ", id: " + user.getId());
        this.otherUser = DB.getUser(user.getId() == user1Id ? user2Id : user1Id);
        this.messages = DB.getMessagesForChat(chatId);


        messagesSV = (ScrollView) findViewById(R.id.chat_SV_messages);
        newMessageET = (EditText) findViewById(R.id.chat_ET_new_message);
        checkBox1Label = (TextView) findViewById(R.id.chat_TV_check_box_1_label);
        checkBox2Label = (TextView) findViewById(R.id.chat_TV_check_box_2_label);
        checkBox1Label.setText(user.getUsername() + " confirms");
        checkBox2Label.setText(otherUser.getUsername() + " confirms");

        checkBoxUser1 = (CheckBox) findViewById(R.id.chat_CB_user_1);
        checkBoxUser2 = (CheckBox) findViewById(R.id.chat_CB_user_2);


    }
}
