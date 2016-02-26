package com.myneighbourhood.Kiril_Hristov;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.MainActivity;
import com.myneighbourhood.utils.Request;
import com.myneighbourhood.utils.User;

import java.util.ArrayList;

public class RequestFeedFragment extends Fragment {

    MainActivity mainActivity;
    ListView RequestFeedListView;
    private ArrayList<Request> requestFeed;
    private DBHelper dbHelper;
    View v;

    public RequestFeedFragment() {
    }

    public static RequestFeedFragment newInstance() {
        RequestFeedFragment requestFeedFragment = new RequestFeedFragment();
        return requestFeedFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_request_feed, container, false);
        mainActivity = (MainActivity) getActivity();
        dbHelper = mainActivity.getDB();
        RequestFeedListView = (ListView) v.findViewById(R.id.RequestFeedListView);
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();

        System.out.println("V onResume sym");
        ProgressDialog progressDialog = new ProgressDialog(mainActivity, R.style.AppTheme);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large);
        progressDialog.show();
        requestFeed = dbHelper.getRequests(mainActivity.getUser().getId(), "feed");
        progressDialog.dismiss();

        String[] titles = new String[requestFeed.size()];
        for (int i = 0; i < requestFeed.size(); i++) {
            titles[i] = requestFeed.get(i).getTitle();
        }

        ArrayAdapter<String> requestFeedAdapter = new CustomRequestRowAdapter(mainActivity, titles, requestFeed);
        RequestFeedListView.setAdapter(requestFeedAdapter);
        RequestFeedListView.setEmptyView(v.findViewById(R.id.noFeedRequestsYet));

        RequestFeedListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        long idClicked = requestFeed.get(position).getId();
                        Intent myIntent = new Intent(mainActivity, ViewRequestActivity.class);
                        myIntent.putExtra("requestId", idClicked);
                        myIntent.putExtra("tab", 0);
                        startActivity(myIntent);
                    }
                }
        );
    }
}
