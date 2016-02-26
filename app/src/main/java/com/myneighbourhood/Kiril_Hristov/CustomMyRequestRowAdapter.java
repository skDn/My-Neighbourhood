package com.myneighbourhood.Kiril_Hristov;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myneighbourhood.R;
import com.myneighbourhood.utils.Request;

import java.util.ArrayList;

/**
 * Created by Kiril on 19/02/16.
 */

public class CustomMyRequestRowAdapter extends ArrayAdapter<String>{

    ArrayList<Request> feedRequests;

    public CustomMyRequestRowAdapter(Context context, String[] titles, ArrayList<Request> feedRequest) {
        super(context, R.layout.custom_request_row, titles);
        this.feedRequests = feedRequest;
    }

    @Override
    public int getCount() {
        return feedRequests.size();
    }

    static class ViewHolderItem{
        TextView title;
        TextView description;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_my_requests_row, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.title = (TextView) convertView.findViewById(R.id.RowRequestTitle);
            viewHolder.description = (TextView) convertView.findViewById(R.id.RowRequestDescription);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        String title = getItem(position);
        viewHolder.title.setText(title);
        viewHolder.description.setText(feedRequests.get(position).getDescription());

        return convertView;
    }
}
