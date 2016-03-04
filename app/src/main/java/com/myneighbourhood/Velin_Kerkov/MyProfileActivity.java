package com.myneighbourhood.Velin_Kerkov;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myneighbourhood.R;
import com.myneighbourhood.utils.Utils;

public class MyProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Profile");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        Button logOffBTN = (Button) findViewById(R.id.my_profile_B_log_off);
        TextView ratingRequesterTV = (TextView) findViewById(R.id.my_profile_TV_rating_requester);
        TextView ratingApplicantTV = (TextView) findViewById(R.id.my_profile_TV_rating_applicant);
        TextView ratingEndorsedByTV = (TextView) findViewById(R.id.my_profile_TV_rating_endorsed_by);
        TextView usernameTV = (TextView) findViewById(R.id.my_profile_TV_username);
        TextView firstNameTV = (TextView) findViewById(R.id.my_profile_TV_first_name);
        TextView lastNameTV = (TextView) findViewById(R.id.my_profile_TV_last_name);
        TextView emailTV = (TextView) findViewById(R.id.my_profile_TV_email);
        TextView phoneTV = (TextView) findViewById(R.id.my_profile_TV_phone);
        TextView addressTV = (TextView) findViewById(R.id.my_profile_TV_address);

        usernameTV.setText(user.getUsername());
        emailTV.setText(user.getEmail());
        phoneTV.setText(user.getPhone());
        ratingRequesterTV.setText(String.valueOf(user.getRating().getRatingAsRequester()));
        ratingApplicantTV.setText(String.valueOf(user.getRating().getRatingAsApplicant()));
        ratingEndorsedByTV.setText(String.valueOf(user.getRating().getEndorsedBy()));

        firstNameTV.setText(user.getFirstName());
        lastNameTV.setText(user.getLastName());
        addressTV.setText(user.getAddress().getAddress());


        System.out.println("Username: " + user.getUsername()
                + "\nFirstname: " + user.getFirstName()
                + "\nLastname: " + user.getLastName()
                + "\nphone: " + user.getPhone()
                + "\nemail: " + user.getEmail()
                + "\naddress: " + user.getAddress().getAddress()
                + "\nratingReq: " + user.getRating().getRatingAsRequester()
                + "\nratingAppl: " + user.getRating().getRatingAsApplicant()
                + "\nendorcedBy: " + user.getRating().getEndorsedBy());


        System.out.println("USERS:\n");
        DB.printUSers();

        logOffBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyProfileActivity.user = null;
                SP_VILI_EDITOR.remove(Utils.SP_LAST_USER_ID);
                SP_VILI_EDITOR.apply();

                Intent i = new Intent(MyProfileActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }
}
