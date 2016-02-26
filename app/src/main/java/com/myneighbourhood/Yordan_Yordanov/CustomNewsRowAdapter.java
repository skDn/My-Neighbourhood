package com.myneighbourhood.Yordan_Yordanov;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.myneighbourhood.R;
import com.myneighbourhood.utils.News;

import java.util.ArrayList;

/**
 * Created by skDn on 23/02/2016.
 */
public class CustomNewsRowAdapter extends ArrayAdapter<String> implements View.OnClickListener{

    private ArrayList<News> newsFeed;

    public CustomNewsRowAdapter(Context context, String[] titles, ArrayList<News> newsFeed) {
        super(context, R.layout.custom_request_row, titles);
        this.newsFeed = newsFeed;
    }

    static class ViewHolderItem{
        ImageView newsImage;
        TextView username;
        TextView newsTitle;
        TextView newsText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_news_row, parent, false);
            viewHolder = new ViewHolderItem();

            viewHolder.newsImage = (ImageView) convertView.findViewById(R.id.RowNewsImage);
            viewHolder.username  = (TextView) convertView.findViewById(R.id.RowNewsUsername);
            viewHolder.newsTitle = (TextView) convertView.findViewById(R.id.RowNewsTitle);
            viewHolder.newsText = (TextView) convertView.findViewById(R.id.RowNewsDescription);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolderItem) convertView.getTag();
        }


//        String title = getItem(position);
        viewHolder.newsTitle.setText(newsFeed.get(position).getTitle());
        viewHolder.newsText.setText(newsFeed.get(position).getText());
        viewHolder.username.setText("username");

        return convertView;
    }

    @Override
    public void onClick(View v) {

    }
}
