package com.myneighbourhood.Yordan_Yordanov;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;

import com.myneighbourhood.Kiril_Hristov.NonScrollListView;
import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.BaseActivity;
import com.myneighbourhood.Velin_Kerkov.MyProfileActivity;
import com.myneighbourhood.Velin_Kerkov.ProfileActivity;
import com.myneighbourhood.utils.User;
import com.myneighbourhood.utils.Utils;

import java.util.ArrayList;

public class MyNeighbourhoodActivity extends BaseActivity {

    private ArrayList<User> neighbours;
    NonScrollListView NeighbourListView;
    SearchView searchNeighbours;
    LinearLayout neighbourhoodLayout;
    ScrollView scrollNeighbourhood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_neighbourhood);

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("My Area");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }



        NeighbourListView = (NonScrollListView) findViewById(R.id.MyNeighbourhoodListView);
        neighbourhoodLayout = (LinearLayout) findViewById(R.id.MyNeighbourhoodLayout);
        scrollNeighbourhood = (ScrollView) findViewById(R.id.scrollNeighbourhood);
        searchNeighbours = (SearchView) findViewById(R.id.searchNeighbours);
        searchNeighbours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchNeighbours.onActionViewExpanded();
                scrollNeighbourhood.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollNeighbourhood.scrollTo(0, (int)searchNeighbours.getY());
                    }
                }, 500);
            }
        });


        neighbourhoodLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });

        // getting users from the database
        neighbours = DB.getUsers();

        // getting user names
        String[] userNames = new String[neighbours.size()];
        for (int i = 0; i < neighbours.size(); i++) {
            userNames[i] = neighbours.get(i).getUsername();
        }
        // instantiating the custom row adapter
        ArrayAdapter<String> neighbourhoodRowAdapter =
                new CustomNeighbourhoodRowAdapter(this, userNames, neighbours);
        NeighbourListView.setAdapter(neighbourhoodRowAdapter);
        NeighbourListView.setScrollContainer(false);

        // setting onclicklistener to navigate to the user's profile.
        NeighbourListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        long idClicked = neighbours.get(position).getId();
                        Intent i = new Intent(MyNeighbourhoodActivity.this, ProfileActivity.class);
                        if (idClicked == user.getId()) {
                            i = new Intent(MyNeighbourhoodActivity.this, MyProfileActivity.class);
                        }
                        i.putExtra(Utils.EXTRA_USER_ID_FOR_PROFILE_ACTIVITY, idClicked);
                        startActivity(i);
                    }
                }
        );

    }

}

