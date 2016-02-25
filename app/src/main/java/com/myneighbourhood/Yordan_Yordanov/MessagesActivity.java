package com.myneighbourhood.Yordan_Yordanov;

import android.os.Bundle;

import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.BaseActivity;

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

        
    }
}
