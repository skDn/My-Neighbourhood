package com.myneighbourhood.Yordan_Yordanov;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.BaseActivity;
import com.myneighbourhood.utils.Chat;
import com.myneighbourhood.utils.Request;
import com.myneighbourhood.utils.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessagesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Messages");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        SearchView searchSV = (SearchView) findViewById(R.id.messages_SV_search);
        ListView chatsLV = (ListView) findViewById(R.id.messages_LV_chats_list);

    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Chat> chatsForUser = DB.getChatsForUser(user);
    }


    private static class ChatsListArrayAdapter extends ArrayAdapter<Chat> {

        private final Chat[] chats;

        private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        static class ViewHolderItem {
            ImageView profileImageIV;
            TextView usernameTV;
            TextView titleTV;
            TextView dateDateTV;
            TextView dateTimeTV;
        }

        @Override
        public int getCount() {
            return chats.length;
        }

        public ChatsListArrayAdapter(Context context, int resource, Chat[] chats) {
            super(context, -1);
            this.chats = chats;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolderItem viewHolder;

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.custom_news_row, parent, false);
                viewHolder = new ViewHolderItem();

                viewHolder.profileImageIV = (ImageView) convertView.findViewById(R.id.chats_list_row_CIV_profile_pic);
                viewHolder.usernameTV = (TextView) convertView.findViewById(R.id.chats_list_row_TV_user_username);
                viewHolder.titleTV = (TextView) convertView.findViewById(R.id.chats_list_row_TV_request_title);
                viewHolder.dateDateTV = (TextView) convertView.findViewById(R.id.chats_list_row_TV_date_date);
                viewHolder.dateTimeTV = (TextView) convertView.findViewById(R.id.chats_list_row_TV_date_time);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolderItem) convertView.getTag();
            }
            Chat chat = chats[position];
            User otherUser = chat.getOtherUser(user);
            Request request = chat.getRequest();
            Date latestMsgDate = chat.getLatestMsgDate();
            String dateDate = dateFormat.format(latestMsgDate);
            String dateTime = timeFormat.format(latestMsgDate);


            viewHolder.profileImageIV.setImageBitmap(otherUser.getImage());
            viewHolder.usernameTV.setText(otherUser.getUsername());
            viewHolder.titleTV.setText(request.getTitle());
            viewHolder.dateDateTV.setText(dateDate);
            viewHolder.dateTimeTV.setText(dateTime);

            return convertView;
        }
    }
}
