package com.myneighbourhood.Kiril_Hristov;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.BaseActivity;
import com.myneighbourhood.utils.Request;
import com.myneighbourhood.utils.User;

import java.util.ArrayList;

public class JobsActivity extends BaseActivity {

    ListView jobsList;
    ArrayList<Request> allRequests;
    ArrayList<Request> jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);


        jobsList = (ListView) findViewById(R.id.jobsList);

    }

    @Override
    protected void onResume() {
        super.onResume();

        allRequests = DB.getAllRequests();
        jobs = new ArrayList<>();

        for(Request request: allRequests){
            if(request.getCreator().getId() == user.getId() && request.getAccepted() == 1){
                jobs.add(request);
            }
            else{
                ArrayList<User> applicants = DB.getApplicants(request.getId());
                for(User applicant: applicants){
                    if (applicant.getId() == user.getId()){
                        jobs.add(request);
                        break;
                    }
                }
            }
        }



        ArrayAdapter<Request> jobsAdapter =
                new CustomJobRowAdapter(this, jobs, user);
        jobsList.setAdapter(jobsAdapter);
    }
}
