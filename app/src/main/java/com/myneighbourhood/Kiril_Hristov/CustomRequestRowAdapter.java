package com.myneighbourhood.Kiril_Hristov;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.myneighbourhood.R;
import com.myneighbourhood.utils.Request;
import com.myneighbourhood.utils.User;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Kiril on 19/02/16.
 */

public class CustomRequestRowAdapter extends ArrayAdapter<String>{

    ArrayList<Request> feedRequests;
    ArrayList<User> users;

    public CustomRequestRowAdapter(Context context, String[] titles, ArrayList<Request> feedRequest, ArrayList<User> users) {
        super(context, R.layout.custom_request_row, titles);
        this.feedRequests = feedRequest;
        this.users = users;
    }

    static class ViewHolderItem{
        ImageView userImage;
        TextView username;
        TextView title;
        TextView description;
        TextView rating;
        Button contact;
        Button hide;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_request_row, parent, false);
            viewHolder = new ViewHolderItem();

            viewHolder.userImage = (ImageView) convertView.findViewById(R.id.RowRequestImage);
            viewHolder.username  = (TextView) convertView.findViewById(R.id.RowRequestUsername);
            viewHolder.title = (TextView) convertView.findViewById(R.id.RowRequestTitle);
            viewHolder.description = (TextView) convertView.findViewById(R.id.RowRequestDescription);
            viewHolder.rating = (TextView) convertView.findViewById(R.id.RowRequestRating);
            viewHolder.contact = (Button) convertView.findViewById(R.id.RowRequestContact);
            viewHolder.hide = (Button) convertView.findViewById(R.id.RowRequestHide);



            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolderItem) convertView.getTag();
        }


        String title = getItem(position);
        viewHolder.title.setText(title);
        viewHolder.description.setText(feedRequests.get(position).getDescription());

        Bitmap profilePicture = null;
        String username = "";
        Integer rating;
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getId() == feedRequests.get(position).getCreatorId()){

                profilePicture = users.get(i).getImage();
                username = users.get(i).getUsername();
                rating = users.get(i).getRating().getRatingAsRequester();
                System.out.println("position " + position + " username " + username);
                break;
            }
        }

        viewHolder.username.setText(username);
        if(profilePicture != null) {
            viewHolder.userImage.setImageBitmap(profilePicture);
        }
        else viewHolder.userImage.setImageResource(R.drawable.ic_account_circle_black_36dp);
        //viewHolder.rating.setText(rating.toString());
        viewHolder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("KLIKASH NA BUTON CONTACT");
            }
        });

        viewHolder.hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("KLIKASH NA BUTON HIDE");
            }
        });

        return convertView;
    }
}
