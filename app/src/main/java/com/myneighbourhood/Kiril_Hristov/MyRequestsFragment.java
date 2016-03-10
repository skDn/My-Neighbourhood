package com.myneighbourhood.Kiril_Hristov;
/**
 * Created by Kiril on 19/02/16.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import java.util.ArrayList;


public class MyRequestsFragment extends Fragment {

    MainActivity mainActivity;
    ListView myRequestsListView;
    FloatingActionButton addRequestActionButton;
    private ArrayList<Request> myRequests;
    View v;
    public MyRequestsFragment(){}

    public static MyRequestsFragment newInstance(){
        MyRequestsFragment myRequestsFragment= new MyRequestsFragment();
        return myRequestsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_my_requests, container, false);
        mainActivity = (MainActivity) getActivity();
        myRequestsListView = (ListView) v.findViewById(R.id.myRequestsListView);
        addRequestActionButton = (FloatingActionButton) v.findViewById(R.id.addRequestActionButton);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        ProgressDialog progressDialog = new ProgressDialog(mainActivity,R.style.AppTheme);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large);
        progressDialog.show();
        myRequests = mainActivity.getDB().getRequests(mainActivity.getUser().getId(), "my");
        progressDialog.dismiss();

        String[] titles = new String[myRequests.size()];
        for (int i = 0; i < myRequests.size(); i++) {
            System.out.println(" title " + titles[i]);
            titles[i] = myRequests.get(i).getTitle();
        }

        ArrayAdapter<String> myRequestAdapter =
                new CustomMyRequestRowAdapter(mainActivity, titles, myRequests);
        myRequestsListView.setAdapter(myRequestAdapter);
        myRequestsListView.setEmptyView(v.findViewById(R.id.noMyRequestsYet));
        myRequestsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        long idClicked = myRequests.get(position).getId();
                        Intent myIntent = new Intent(mainActivity, ApplicantsActivity.class);
                        myIntent.putExtra("requestId", idClicked);
                        myIntent.putExtra("tab", 1);
                        startActivity(myIntent);
                        //mainActivity.finish();
                    }
                }
        );

        addRequestActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mainActivity, AddRequestActivity.class);
                myIntent.putExtra("tab", 1);
                startActivity(myIntent);
                //mainActivity.finish();
            }
        });
    }
}
