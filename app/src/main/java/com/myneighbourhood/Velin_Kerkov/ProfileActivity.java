package com.myneighbourhood.Velin_Kerkov;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myneighbourhood.R;
import com.myneighbourhood.utils.User;
import com.myneighbourhood.utils.Utils;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity {

    private User otherUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle extras = getIntent().getExtras();
        long otherUserId = extras.getLong(Utils.EXTRA_USER_ID_FOR_PROFILE_ACTIVITY);
        otherUser = DB.getUser(otherUserId);

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(otherUser.getUsername());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        Button blockContentFromBTN = (Button) findViewById(R.id.profile_B_block_content_from);
        Button blockFeedToBTN = (Button) findViewById(R.id.profile_B_block_feed_to);
        Button endorseBTN = (Button) findViewById(R.id.profile_B_endorse);


        CircleImageView profilePicCIV = (CircleImageView) findViewById(R.id.profile_CIV_profile_pic);

        TextView usernameTV = (TextView) findViewById(R.id.profile_TV_username);
        TextView firstNameTV = (TextView) findViewById(R.id.profile_TV_first_name);
        TextView lastNameTV = (TextView) findViewById(R.id.profile_TV_last_name);
        TextView ratingRequesterTV = (TextView) findViewById(R.id.profile_TV_rating_requester);
        TextView ratingEndorsedByTV = (TextView) findViewById(R.id.profile_TV_rating_endorsed_by);
        TextView ratingApplicantTV = (TextView) findViewById(R.id.profile_TV_rating_applicant);


        usernameTV.setText(otherUser.getUsername());
        firstNameTV.setText(otherUser.getFirstName());
        lastNameTV.setText(otherUser.getLastName());
        ratingApplicantTV.setText(String.valueOf(otherUser.getRating().getRatingAsApplicant()));
        ratingEndorsedByTV.setText(String.valueOf(otherUser.getRating().getEndorsedBy()));
        ratingRequesterTV.setText(String.valueOf(otherUser.getRating().getRatingAsRequester()));
        profilePicCIV.setImageBitmap(otherUser.getImage());

        endorseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
