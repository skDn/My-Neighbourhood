package com.myneighbourhood.Kiril_Hristov;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myneighbourhood.R;
import com.myneighbourhood.utils.User;

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
        ImageView profileImage;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_applicant_row, parent, false);
            viewHolder = new ViewHolderItem();

            viewHolder.profileImage = (ImageView) convertView.findViewById(R.id.ApplicantRowUserImage);
            viewHolder.username = (TextView) convertView.findViewById(R.id.ApplicantRowUsername);
            viewHolder.rating = (TextView) convertView.findViewById(R.id.ApplicantRowRating);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        if(applicants.get(position).getImage() != null){
            viewHolder.profileImage.setImageBitmap(applicants.get(position).getImage());
        } else viewHolder.profileImage.setImageResource(R.drawable.ic_account_circle_black_36dp);

        viewHolder.username.setText(applicants.get(position).getUsername());
        viewHolder.rating.setText(String.valueOf(applicants.get(position).getRating().getRatingAsApplicant()));

        return convertView;
    }


}
