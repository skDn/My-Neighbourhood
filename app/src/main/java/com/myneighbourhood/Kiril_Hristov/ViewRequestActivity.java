package com.myneighbourhood.Kiril_Hristov;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.BaseActivity;
import com.myneighbourhood.Yordan_Yordanov.ChatActivity;
import com.myneighbourhood.utils.Chat;
import com.myneighbourhood.utils.Request;
import com.myneighbourhood.utils.User;
import com.myneighbourhood.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewRequestActivity extends BaseActivity {

    ImageView profilePicture;
    TextView username, title, description, rating, expires;
    Button apply;
    int tab;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        Intent i= getIntent();
        long requestId = i.getLongExtra("requestId", 0);
        tab = i.getIntExtra("tab", 0);

        profilePicture = (ImageView) findViewById(R.id.ReqActUserImage);
        username = (TextView) findViewById(R.id.ReqActUsername);
        title = (TextView) findViewById(R.id.ReqActTitle);
        description = (TextView) findViewById(R.id.ReqActDescription);
        rating = (TextView) findViewById(R.id.ReqActRating);
        expires = (TextView) findViewById(R.id.ReqActExpires);
        apply = (Button) findViewById(R.id.ReqActButton);

        final Request request = DB.getRequest(requestId);

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(request.getTitle());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        Bitmap image = request.getCreator().getImage();
        if(profilePicture != null) {
            profilePicture.setImageBitmap(image);
        }
        else {profilePicture.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_account_circle_black_36dp));}
        username.setText(request.getCreator().getUsername());
        rating.setText(String.valueOf(request.getCreator().getRating().getRatingAsRequester()));
        title.setText(request.getTitle());
        description.setText(request.getDescription());


        ArrayList<User> applicants = DB.getApplicants(requestId);

        boolean isApplicant = false;
        for(User applicant : applicants){
            if (applicant.getId() == user.getId()){
                isApplicant = true;
                break;
            }
        }

        if(isApplicant){
            apply.setText("Already applied, Contact");
            apply.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
        }
        else{
            apply.setText("I am glad to help!");
            apply.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        String dateDate = dateFormat.format(request.getExpires());
        String dateTime = timeFormat.format(request.getExpires());
        expires.setText(dateDate + " " + dateTime);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.addApplicant(user.getId(), request.getId(), request.getCreator().getId());
                Intent i = new Intent(ViewRequestActivity.this, ChatActivity.class);
                Chat c = DB.addChat(request.getCreator(), user, request);
                i.putExtra(Utils.EXTRA_CHAT_ID, c.getId());
                startActivity(i);
                finish();

            }
        });
    }
}
