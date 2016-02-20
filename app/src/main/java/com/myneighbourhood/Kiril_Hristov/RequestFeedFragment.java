package com.myneighbourhood.Kiril_Hristov;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.MainActivity;
import com.myneighbourhood.utils.Request;
import com.myneighbourhood.utils.UserSharedPref;

import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RequestFeedFragment extends Fragment{

    private ArrayList<Request> requestFeed;

    public RequestFeedFragment(){}

    public static RequestFeedFragment newInstance(){
        RequestFeedFragment requestFeedFragment= new RequestFeedFragment();
        return requestFeedFragment;
    }

    MainActivity mainActivity;
    ListView RequestFeedListView;
    FloatingActionButton requestFeedActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_request_feed, container, false);
        mainActivity = (MainActivity) getActivity();
        RequestFeedListView = (ListView) v.findViewById(R.id.RequestFeedListView);
        requestFeedActionButton = (FloatingActionButton) v.findViewById(R.id.requestFeedActionButton);
        return v;
    }


    @Override
    public void onResume() {
        DBHelper dbHelper = DBHelper.getInstance(mainActivity);
        UserSharedPref sp = UserSharedPref.getInstance(mainActivity);

        ProgressDialog progressDialog = new ProgressDialog(mainActivity,R.style.AppTheme);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large);
        progressDialog.show();
        requestFeed = dbHelper.getRequests(sp.getUserLoggedIn().getId(), "feed");
        progressDialog.dismiss();

        String[] titles = new String[requestFeed.size()];
        for (int i = 0; i < requestFeed.size(); i++) {
            titles[i] = requestFeed.get(i).getTitle();
        }

        ArrayAdapter<String> requestFeedAdapter =
                new CustomRequestRowAdapter(mainActivity, titles, requestFeed);
        RequestFeedListView.setAdapter(requestFeedAdapter);

        RequestFeedListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int idClicked;
                        String list = String.valueOf(parent.getItemAtPosition(position));
                        idClicked = requestFeed.get(position).getId();
                        Intent myIntent = new Intent(mainActivity, ViewRequestActivity.class);
                        myIntent.putExtra("tab", 0);
                        startActivity(myIntent);
                        mainActivity.finish();
                    }
                }
        );
    }
}
