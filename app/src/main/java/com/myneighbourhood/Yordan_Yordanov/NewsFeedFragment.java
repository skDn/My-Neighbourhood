package com.myneighbourhood.Yordan_Yordanov;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.myneighbourhood.Kiril_Hristov.DBHelper;
import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.MainActivity;
import com.myneighbourhood.utils.News;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeedFragment extends Fragment {

    MainActivity mainActivity;
    ListView NewsFeedListView;
    private ArrayList<News> newsFeed;
    FloatingActionButton addNewsActionButton;
    private DBHelper dbHelper;
    View v;


    public NewsFeedFragment() {
        // Required empty public constructor
    }

    public static NewsFeedFragment newInstance() {
        NewsFeedFragment newsFeedFragment = new NewsFeedFragment();
        return newsFeedFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_news_feed, container, false);
        mainActivity = (MainActivity) getActivity();
        dbHelper = mainActivity.getDB();
        NewsFeedListView = (ListView) v.findViewById(R.id.newsFeedListView);
        addNewsActionButton = (FloatingActionButton) v.findViewById(R.id.addNewsActionButton);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        ProgressDialog progressDialog = new ProgressDialog(mainActivity,R.style.AppTheme);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large);
        progressDialog.show();
        newsFeed = dbHelper.getNews();
        progressDialog.dismiss();

        String[] titles = new String[newsFeed.size()];
        for (int i = 0; i < newsFeed.size(); i++) {
            titles[i] = newsFeed.get(i).getTitle();
        }

        ArrayAdapter<String> requestFeedAdapter =
                new CustomNewsRowAdapter(mainActivity, titles, newsFeed);
        NewsFeedListView.setAdapter(requestFeedAdapter);
        NewsFeedListView.setEmptyView(v.findViewById(R.id.noNewsYet));
        NewsFeedListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        long idClicked = newsFeed.get(position).getNewsId();
                        Intent myIntent = new Intent(mainActivity, NewsActivity.class);
                        myIntent.putExtra("newsId", idClicked);
                        myIntent.putExtra("tab", 2);
                        startActivity(myIntent);
                        mainActivity.finish();
                    }
                }
        );
        addNewsActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mainActivity, AddNewsActivity.class);
                myIntent.putExtra("tab", 2);
                startActivity(myIntent);
                mainActivity.finish();
            }
        });
    }


}
