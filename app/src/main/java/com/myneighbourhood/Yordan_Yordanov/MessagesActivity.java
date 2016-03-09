package com.myneighbourhood.Yordan_Yordanov;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.BaseActivity;
import com.myneighbourhood.Velin_Kerkov.ChatActivity;
import com.myneighbourhood.utils.Chat;
import com.myneighbourhood.utils.Request;
import com.myneighbourhood.utils.User;
import com.myneighbourhood.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessagesActivity extends BaseActivity {

    private ListView chatsLV;
    private ArrayList<Chat> chatsForUser;
    private ChatsListArrayAdapter adapter;

    public boolean showHomeInSteadOfChat(){
        return true;
    }

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

        final SearchView searchSV = (SearchView) findViewById(R.id.messages_SV_search);
        searchSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchSV.onActionViewExpanded();
            }
        });
        chatsLV = (ListView) findViewById(R.id.messages_LV_chats_list);
        chatsForUser = new ArrayList<>();
        adapter = new ChatsListArrayAdapter(this, R.layout.chats_list_row_layout, chatsForUser);
        chatsLV.setAdapter(adapter);
        chatsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chat chat = chatsForUser.get(position);
                long chatId = chat.getId();
                long otherUserId = chat.getOtherUser(user).getId();
                Intent i = new Intent(MessagesActivity.this, ChatActivity.class);
                i.putExtra(Utils.EXTRA_CHAT_ID, chatId);
                i.putExtra(Utils.EXTRA_CHAT_OTHER_USER, otherUserId);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        chatsForUser = DB.getChatsForUser(user);
        adapter.setChats(this.chatsForUser);
    }


    private static class ChatsListArrayAdapter extends ArrayAdapter<Chat> {

        private ArrayList<Chat> chats;

        private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        static class ViewHolderItem {
            ImageView profileImageIV;
            TextView usernameTV;
            TextView titleTV;
            TextView dateDateTV;
            TextView dateTimeTV;
            LinearLayout vLayout;
        }

        @Override
        public int getCount() {
            return chats.size();
        }

        public void setChats(ArrayList<Chat> chats) {
            this.chats = chats;
            notifyDataSetChanged();
        }

        public ChatsListArrayAdapter(Context context, int resource, ArrayList<Chat> chats) {
            super(context, resource);
            this.chats = chats;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolderItem viewHolder;

            if (convertView == null) {
                System.out.println("convertView == null");
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.chats_list_row_layout, parent, false);
                viewHolder = new ViewHolderItem();

                viewHolder.vLayout = (LinearLayout) convertView;
                viewHolder.profileImageIV = (ImageView) convertView.findViewById(R.id.chats_list_row_CIV_profile_pic);
                viewHolder.usernameTV = (TextView) convertView.findViewById(R.id.chats_list_row_TV_user_username);
                viewHolder.titleTV = (TextView) convertView.findViewById(R.id.chats_list_row_TV_request_title);
                viewHolder.dateDateTV = (TextView) convertView.findViewById(R.id.chats_list_row_TV_date_date);
                viewHolder.dateTimeTV = (TextView) convertView.findViewById(R.id.chats_list_row_TV_date_time);

                convertView.setTag(viewHolder);
            } else {
                System.out.println("convertView != null");
                viewHolder = (ViewHolderItem) convertView.getTag();
            }

            Chat chat = chats.get(position);
            User otherUser = chat.getOtherUser(user);
            Request request = chat.getRequest();
            Date latestMsgDate = chat.getLatestMsgDate();
            if (latestMsgDate.getTime() == 0) {
                latestMsgDate = chat.getCreatedAt();
            }
            System.out.println("latestMsgDate: " + latestMsgDate.getTime());
            String dateDate = dateFormat.format(latestMsgDate);
            String dateTime = timeFormat.format(latestMsgDate);

            viewHolder.usernameTV.setText(otherUser.getUsername());
            viewHolder.profileImageIV.setImageBitmap(otherUser.getImage());
            viewHolder.titleTV.setText(request.getTitle());
            viewHolder.dateDateTV.setText(dateDate);
            viewHolder.dateTimeTV.setText(dateTime);

            return convertView;
        }
    }
}
