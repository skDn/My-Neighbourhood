package com.myneighbourhood.Kiril_Hristov;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class ApplicantsActivity extends BaseActivity {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    TextView title, description, expires;
    ListView applicantsList;
    Request request;
    ArrayList<User> applicants;
    TextView noAppliants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicants);

        Intent i = getIntent();
        long requestId = i.getLongExtra("requestId", 0);

        request = DB.getRequest(requestId);

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(request.getTitle());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        title = (TextView) findViewById(R.id.ApplicantsActTitle);
        description = (TextView) findViewById(R.id.ApplicantsActDescription);
        expires = (TextView) findViewById(R.id.ApplicantsActExpires);
        applicantsList = (ListView) findViewById(R.id.applicantsList);
        noAppliants = (TextView) findViewById(R.id.noApplicants);
        applicants = DB.getApplicants(request.getId());


        String[] userNames = new String[applicants.size()];
        for(int j = 0; j < applicants.size(); j++){
            userNames[j] = applicants.get(j).getUsername();
        }

        if(applicants.size()!=0){
            noAppliants.setVisibility(View.GONE);
        }

        title.setText(request.getTitle());
        description.setText(request.getDescription());
        String dateDate = dateFormat.format(request.getExpires());
        String dateTime = timeFormat.format(request.getExpires());
        expires.setText(dateDate + " " + dateTime);

        ArrayAdapter<String> applicantsAdapter =
                new CustomApplicantRowAdapter(this, userNames, applicants);
        applicantsList.setAdapter(applicantsAdapter);

        // setting onclicklistener to create a chat with the applicant
        applicantsList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(ApplicantsActivity.this, ChatActivity.class);
                        Chat c = DB.addChat(user, applicants.get(position), request);
                        i.putExtra(Utils.EXTRA_CHAT_ID, c.getId());
                        startActivity(i);
//                        finish();
                    }
                }
        );

    }
}
