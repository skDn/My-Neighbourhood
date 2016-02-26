package com.myneighbourhood.Yordan_Yordanov;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.BaseActivity;
import com.myneighbourhood.Velin_Kerkov.ProfileActivity;
import com.myneighbourhood.utils.User;

import java.util.ArrayList;

public class MyNeighbourhoodActivity extends BaseActivity {

    private ArrayList<User> neighbours;
    ListView NeighbourListView;
    SearchView searchNeighbours;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_neighbourhood);

        NeighbourListView = (ListView) findViewById(R.id.MyNeighbourhoodListView);

        searchNeighbours = (SearchView) findViewById(R.id.searchNeighbours);
        searchNeighbours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchNeighbours.onActionViewExpanded();
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

        // setting onclicklistener to navigate to the user's profile.
        NeighbourListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        long idClicked = neighbours.get(position).getId();
                        Intent myIntent = new Intent(MyNeighbourhoodActivity.this, ProfileActivity.class);
                        myIntent.putExtra("userId", idClicked);
                        startActivity(myIntent);
                        finish();
                    }
                }
        );

    }

}

