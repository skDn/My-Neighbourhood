package com.myneighbourhood.Kiril_Hristov;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.myneighbourhood.R;
import com.myneighbourhood.utils.DBHelper;
import com.myneighbourhood.utils.Request;
import com.myneighbourhood.utils.User;

import java.util.ArrayList;

/**
 * Created by Kiril on 04/03/16.
 */
public class CustomJobRowAdapter extends ArrayAdapter<Request> {

    ArrayList<Request> jobs;
    User user;
    DBHelper db;


    public CustomJobRowAdapter(Context context, ArrayList<Request> jobs, User user) {
        super(context, R.layout.custom_job_row, jobs);
        this.jobs = jobs;
        this.user = user;
        this.db = DBHelper.getInstance(context);
    }

    static class ViewHolderItem{
        TextView status, userType, userName, title;
        ImageView like, dislike;
        Button markFinished;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_job_row, parent, false);
            viewHolder = new ViewHolderItem();

            viewHolder.status = (TextView) convertView.findViewById(R.id.jobStatus);
            viewHolder.userType = (TextView) convertView.findViewById(R.id.jobCreatorCaption);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.jobUsername);
            viewHolder.title = (TextView) convertView.findViewById(R.id.jobTitle);
            viewHolder.like = (ImageView) convertView.findViewById(R.id.jobLike);
            viewHolder.dislike = (ImageView) convertView.findViewById(R.id.jobDislike);
            viewHolder.markFinished = (Button) convertView.findViewById(R.id.jobMarkFinished);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        if(jobs.get(position).getStatus() == 0){
            viewHolder.status.setText(" In Progress ");
            viewHolder.like.setVisibility(View.INVISIBLE);
            viewHolder.dislike.setVisibility(View.INVISIBLE);
            viewHolder.markFinished.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.status.setText(" DONE ");
            viewHolder.like.setVisibility(View.VISIBLE);
            viewHolder.dislike.setVisibility(View.VISIBLE);
            viewHolder.markFinished.setVisibility(View.GONE);
        }

        if(jobs.get(position).getCreator().getId() == user.getId()){
            viewHolder.userType.setText("Applicant: ");
            ArrayList<User> applicants = db.getApplicants(jobs.get(position).getId());
            User applicant = db.getApplicantForAcceptedRequest(applicants);
            viewHolder.userName.setText(applicant.getUsername());
        }
        else {
            viewHolder.userType.setText("Creator: ");
            viewHolder.userName.setText(jobs.get(position).getCreator().getUsername());
        }

        viewHolder.title.setText(jobs.get(position).getTitle());

        viewHolder.markFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobs.get(position).setStatus(1);
                db.requestStatusUpdate(jobs.get(position));
                CustomJobRowAdapter.this.notifyDataSetChanged();
            }
        });

        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Giving +1 rating to the other user is not implemented");
            }
        });

        viewHolder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Taking -1 rating from the user`s rating is not implemented");
            }
        });

        return convertView;
    }


    public void showDialog(String message){
        AlertDialog.Builder allertBuilder = new AlertDialog.Builder(getContext());
        allertBuilder.setMessage(message);
        allertBuilder.setPositiveButton("OK", null);
        allertBuilder.show();
    }

}
