package com.myneighbourhood.Yordan_Yordanov;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.BaseActivity;
import com.myneighbourhood.utils.Chat;
import com.myneighbourhood.utils.Message;
import com.myneighbourhood.utils.User;
import com.myneighbourhood.utils.Utils;

import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<Message> messages;
    private ListView messagesLV;
    private EditText newMessageET;
    private TextView checkBox1Label;
    private TextView checkBox2Label;
    private CheckBox checkBoxUser1;
    private CheckBox checkBoxUser2;
    private User user1;
    private User user2;
    private User otherUser;
    private Chat chat;
    private ImageView sendMessageIV;
    private MsgAdapter adapter;
    private Button shakeHandsBTN;

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
        this.chat = DB.getChat(chatId);
        this.messages = DB.getMessagesForChat(chatId);
        this.otherUser = chat.getOtherUser(user);

        System.out.println("currentUser: " + user.getUsername() + ", id: " + user.getId());
        System.out.println("otherUser: " + otherUser.getUsername() + ", id: " + otherUser.getId());


        messagesLV = (ListView) findViewById(R.id.chat_LV_messages);
        newMessageET = (EditText) findViewById(R.id.chat_ET_new_message);
        checkBox1Label = (TextView) findViewById(R.id.chat_TV_check_box_1_label);
        checkBox2Label = (TextView) findViewById(R.id.chat_TV_check_box_2_label);
        sendMessageIV = (ImageView) findViewById(R.id.chat_B_send_message);
        checkBox1Label.setText(otherUser.getUsername() + " confirms");
        checkBox2Label.setText(user.getUsername() + " confirms");
        checkBoxUser1 = (CheckBox) findViewById(R.id.chat_CB_user_1);
        checkBoxUser2 = (CheckBox) findViewById(R.id.chat_CB_user_2);
        shakeHandsBTN = (Button) findViewById(R.id.chat_B_shake_hands);
        shakeHandsBTN.setOnClickListener(this);

        checkBoxUser1.setClickable(false);
        checkBoxUser2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = checkBoxUser2.isChecked();
                chat.setChecked(user, checked);
                DB.updateAccepted(user, chat);
            }
        });


        adapter = new MsgAdapter(this, -1, this.messages, otherUser);
        messagesLV.setAdapter(adapter);

        sendMessageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleNewMessage();
            }
        });
    }

    private void handleNewMessage() {
        String messageString = newMessageET.getText().toString();
        Message message = new Message(new Date(), chat.getId(), messageString, user, otherUser);
        DB.addMessage(message);
        newMessageET.setText("");
        messages.add(message);
        adapter.setMessages(messages);
        messagesLV.smoothScrollToPosition(adapter.getCount() - 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkBoxUser1.setChecked(chat.getAcceptedBy(otherUser));
        checkBoxUser2.setChecked(chat.getAcceptedBy(user));

        if (checkBoxUser2.isChecked() && checkBoxUser1.isChecked()) {
            shakeHandsBTN.setClickable(true);
            shakeHandsBTN.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        } else {
            shakeHandsBTN.setClickable(false);
            shakeHandsBTN.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
        }


        messages = DB.getMessagesForChat(chat.getId());
        adapter.notifyDataSetChanged();
        messagesLV.smoothScrollToPosition(adapter.getCount() - 1);
    }

    @Override
    public void onClick(View v) {
        DB.requestAccepted(chat.getRequest());
        showDialogWithOkButton("Great !");
    }

    private static class MsgAdapter extends ArrayAdapter<Message> {

        private ArrayList<Message> messages;
        private final User otherUser;
        private LinearLayout wrapper;
        private TextView msgTV;

        public void setMessages(ArrayList<Message> messages) {
            this.messages = messages;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return messages.size();
        }

        public MsgAdapter(Context context, int resource, ArrayList<Message> messages, User otherUser) {
            super(context, -1);
            this.messages = messages;
            this.otherUser = otherUser;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                row = inflater.inflate(R.layout.chat_msg_layout, null);
            }


            wrapper = (LinearLayout) row.findViewById(R.id.wrapper);

            Message currMsg = messages.get(position);

            msgTV = (TextView) row.findViewById(R.id.msg_TV_msg);

            msgTV.setText(currMsg.getText());

            msgTV.setBackgroundResource(currMsg.getFromUser().getId() == otherUser.getId()
                    ? R.drawable.bubble_orange : R.drawable.bubble_white);
            wrapper.setGravity(currMsg.getFromUser().getId() == otherUser.getId()
                    ? Gravity.LEFT : Gravity.RIGHT);

            return row;
        }
    }

}
