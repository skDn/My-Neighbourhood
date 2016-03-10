package com.myneighbourhood.Yordan_Yordanov;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myneighbourhood.R;
import com.myneighbourhood.utils.News;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by skDn on 23/02/2016.
 */
public class CustomNewsRowAdapter extends ArrayAdapter<String> implements View.OnClickListener {

    private ArrayList<News> newsFeed;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public CustomNewsRowAdapter(Context context, String[] titles, ArrayList<News> newsFeed) {
        super(context, R.layout.custom_news_row, titles);
        this.newsFeed = newsFeed;
    }

    static class ViewHolderItem {
        ImageView newsImage;
        ImageView userImage;
        TextView username;
        TextView newsTitle;
        TextView date;
        TextView dateTime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_news_row, parent, false);
            viewHolder = new ViewHolderItem();

            viewHolder.newsImage = (ImageView) convertView.findViewById(R.id.RowNewsImage);
            viewHolder.username = (TextView) convertView.findViewById(R.id.RowNewsUsername);
            viewHolder.newsTitle = (TextView) convertView.findViewById(R.id.RowNewsTitle);
            viewHolder.userImage = (ImageView) convertView.findViewById(R.id.RowNewsUserImage);
            viewHolder.date = (TextView) convertView.findViewById(R.id.RowNewsDate);
            viewHolder.dateTime = (TextView) convertView.findViewById(R.id.RowNewsDateTime);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }


        if (newsFeed.get(position).getPicture() == null) {
            viewHolder.newsImage.setVisibility(View.GONE);
        } else {
            viewHolder.newsImage.setVisibility(View.VISIBLE);
            viewHolder.newsImage.setImageBitmap(newsFeed.get(position).getPicture());
        }

        viewHolder.newsTitle.setText(newsFeed.get(position).getTitle());
        viewHolder.username.setText(newsFeed.get(position).getCreator().getUsername());
        viewHolder.userImage.setImageBitmap(newsFeed.get(position).getCreator().getImage());
        Date latestMsgDate = new Date(newsFeed.get(position).getTimestamp());

        String dateDate = dateFormat.format(latestMsgDate);
        String dateTime = timeFormat.format(latestMsgDate);

        viewHolder.date.setText(dateDate);
        viewHolder.dateTime.setText(dateTime);

        return convertView;
    }

    @Override
    public void onClick(View v) {

    }
}
