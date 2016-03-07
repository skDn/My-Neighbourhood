package com.myneighbourhood.Kiril_Hristov;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.ProfileActivity;
import com.myneighbourhood.utils.User;
import com.myneighbourhood.utils.Utils;

import java.util.ArrayList;

/**
 * Created by kiril on 04/03/16.
 */
public class CustomApplicantRowAdapter extends ArrayAdapter<String> {

    ArrayList<User> applicants;


    public CustomApplicantRowAdapter(Context context, String[] titles, ArrayList<User> applicants) {
        super(context, R.layout.custom_applicant_row, titles);
        this.applicants = applicants;
    }

    @Override
    public int getCount() {
        return applicants.size();
    }

    static class ViewHolderItem{
        TextView username, rating;
        ImageView profileImage, chatIV;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_applicant_row, parent, false);
            viewHolder = new ViewHolderItem();

            viewHolder.profileImage = (ImageView) convertView.findViewById(R.id.ApplicantRowUserImage);
            viewHolder.username = (TextView) convertView.findViewById(R.id.ApplicantRowUsername);
            viewHolder.rating = (TextView) convertView.findViewById(R.id.ApplicantRowRating);
            viewHolder.chatIV = (ImageView) convertView.findViewById(R.id.custom_applicant_row_IV_chat);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        if(applicants.get(position).getImage() != null){
            viewHolder.profileImage.setImageBitmap(applicants.get(position).getImage());
        } else viewHolder.profileImage.setImageResource(R.drawable.ic_account_circle_black_36dp);

        viewHolder.username.setText(applicants.get(position).getUsername());
        viewHolder.rating.setText(String.valueOf(applicants.get(position).getRating().getRatingAsApplicant()));


        viewHolder.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long applicantId = applicants.get(position).getId();
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra(Utils.EXTRA_USER_ID_FOR_PROFILE_ACTIVITY, applicantId);
                getContext().startActivity(i);
            }
        });

        return convertView;
    }


}
