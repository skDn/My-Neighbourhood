package com.myneighbourhood.Kiril_Hristov;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.MainActivity;

import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.widget.TextView;

public class RequestFeedFragment extends Fragment implements View.OnClickListener{


    public RequestFeedFragment(){}

    public static RequestFeedFragment newInstance(){
        RequestFeedFragment requestFeedFragment= new RequestFeedFragment();
        return requestFeedFragment;
    }

    MainActivity mainActivity;
    ListView RequestFeedListView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_request_feed, container, false);
        mainActivity = (MainActivity) getActivity();
        RequestFeedListView = (ListView) v.findViewById(R.id.RequestFeedListView);
        RequestFeedListView.setOnClickListener(this);
        return v;
    }




    @Override
    public void onClick(View v) {

    }
}
