package com.myneighbourhood.Kiril_Hristov;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.ProfileActivity;
import com.myneighbourhood.Yordan_Yordanov.ChatActivity;
import com.myneighbourhood.utils.Chat;
import com.myneighbourhood.utils.Request;
import com.myneighbourhood.utils.User;
import com.myneighbourhood.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kiril on 19/02/16.
 */

public class CustomRequestRowAdapter extends ArrayAdapter<String> implements View.OnClickListener{

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");

    ArrayList<Request> feedRequests;
    User user;
    DBHelper db;

    public CustomRequestRowAdapter(Context context, String[] titles, ArrayList<Request> feedRequest, User user) {
        super(context, R.layout.custom_request_row, titles);
        this.feedRequests = feedRequest;
        this.user = user;
        db = DBHelper.getInstance(context);
    }

    @Override
    public int getCount() {
        return feedRequests.size();
    }

    @Override
    public void onClick(View v) {
        int requestIndex = (int) v.getTag();
        long clickedRequestCreatorId = feedRequests.get(requestIndex).getCreator().getId();
        Intent i = new Intent(getContext(), ProfileActivity.class);
        i.putExtra(Utils.EXTRA_USER_ID_FOR_PROFILE_ACTIVITY, clickedRequestCreatorId);
        getContext().startActivity(i);
    }

    static class ViewHolderItem {
        ImageView userImage;
        TextView username;
        TextView dateCreated;
        TextView title;
        TextView description;
        TextView rating;
        Button contact;
        Button hide;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_request_row, parent, false);

            RelativeLayout userInfoRL = (RelativeLayout) convertView.findViewById(R.id.custom_request_row_RL_user_info);
            userInfoRL.setOnClickListener(this);
            userInfoRL.setTag(position);

            viewHolder = new ViewHolderItem();

            viewHolder.userImage = (ImageView) convertView.findViewById(R.id.RowRequestImage);
            viewHolder.username = (TextView) convertView.findViewById(R.id.RowRequestUsername);
            viewHolder.dateCreated = (TextView) convertView.findViewById(R.id.request_row_TV_date_created);
            viewHolder.title = (TextView) convertView.findViewById(R.id.RowRequestTitle);
            viewHolder.description = (TextView) convertView.findViewById(R.id.RowRequestDescription);
            viewHolder.rating = (TextView) convertView.findViewById(R.id.RowRequestRating);
            viewHolder.contact = (Button) convertView.findViewById(R.id.RowRequestContact);
            viewHolder.hide = (Button) convertView.findViewById(R.id.RowRequestHide);
            viewHolder.hide.setTag(position);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }


        User u = feedRequests.get(position).getCreator();
        String title = feedRequests.get(position).getTitle();
        Integer rating = feedRequests.get(position).getCreator().getRating().getRatingAsRequester();
        Bitmap profilePicture = u.getImage();


        if (profilePicture != null) {
            viewHolder.userImage.setImageBitmap(profilePicture);
        } else {
            viewHolder.userImage.setImageResource(R.drawable.ic_account_circle_black_36dp);
        }
        viewHolder.username.setText(feedRequests.get(position).getCreator().getUsername());
        viewHolder.title.setText(title);
        viewHolder.description.setText(feedRequests.get(position).getDescription());
        viewHolder.rating.setText(String.valueOf(rating));
        viewHolder.dateCreated.setText(dateFormat.format(new Date(feedRequests.get(position).getTimestamp())));

        ArrayList<User> applicants = db.getApplicants(feedRequests.get(position).getId());

        boolean isApplicant = false;
        for(User applicant : applicants){
            if (applicant.getId() == user.getId()){
                isApplicant = true;
                break;
            }
        }

        if(isApplicant){
            viewHolder.contact.setText("Contact");
            viewHolder.contact.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.holo_green_dark));
        }
        else{
            viewHolder.contact.setText("Apply");
            viewHolder.contact.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        }


        viewHolder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addApplicant(user.getId(), feedRequests.get(position).getId(), feedRequests.get(position).getCreator().getId());
                Intent i = new Intent(getContext(), ChatActivity.class);
                Chat c = db.addChat(feedRequests.get(position).getCreator(), user, feedRequests.get(position));
                System.out.println("applying for request: " + feedRequests.get(position));
                System.out.println("chatId: " + c.getId());
                i.putExtra(Utils.EXTRA_CHAT_ID, c.getId());
                i.putExtra("tab", 0);
                getContext().startActivity(i);
            }
        });

        viewHolder.hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicking remove for : " + v.getTag());
                //feedRequests.remove((int) v.getTag());
                //CustomRequestRowAdapter.this.notifyDataSetChanged();
                removeListItem((View) (v.getParent()).getParent());
            }
        });

        return convertView;
        }


    protected void removeListItem(final View rowView) {
        final Animation animation = AnimationUtils.loadAnimation(
                getContext(), android.R.anim.slide_out_right);
        rowView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                rowView.setVisibility(View.GONE);
                int tag = (int) rowView.findViewById(R.id.RowRequestHide).getTag();
                feedRequests.remove(tag);
                CustomRequestRowAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}