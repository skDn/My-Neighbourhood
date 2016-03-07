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
import android.widget.Spinner;

import com.myneighbourhood.Kiril_Hristov.NonScrollListView;
import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.BaseActivity;
import com.myneighbourhood.Velin_Kerkov.MyProfileActivity;
import com.myneighbourhood.Velin_Kerkov.ProfileActivity;
import com.myneighbourhood.utils.User;
import com.myneighbourhood.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MyNeighbourhoodActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ArrayList<User> neighbours;
    NonScrollListView NeighbourListView;
    SearchView searchNeighbours;
    LinearLayout neighbourhoodLayout;
    ScrollView scrollNeighbourhood;
    private CustomNeighbourhoodRowAdapter neighbourhoodRowAdapter;
    private Spinner sortS;
    private Comparator<User> byRequesterRating;
    private Comparator<User> byEndrosedBy;
    private Comparator<User> byApplicantRating;

    @Override
    protected boolean showHomeInSteadOfMyNeighbourhood() {
        return true;
    }

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


        sortS = (Spinner) findViewById(R.id.my_neighbourhood_S_sorting);
        List<String> sortOptions = new ArrayList<String>();
        sortOptions.add("None");
        sortOptions.add("By Requester rating");
        sortOptions.add("By Applicant rating");
        sortOptions.add("By Endorsed by");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sortOptions);
        dataAdapter.setDropDownViewResource(R.layout.custom_spinner_row);
        sortS.setAdapter(dataAdapter);


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
                        scrollNeighbourhood.scrollTo(0, (int) searchNeighbours.getY());
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
        User toBeRemoved = null;
        for (User neighbour: neighbours){
            if (neighbour.getId() == user.getId()){
                toBeRemoved = neighbour;
                break;
            }
        }
        neighbours.remove(toBeRemoved);

        // getting user names
        String[] userNames = new String[neighbours.size()];
        for (int i = 0; i < neighbours.size(); i++) {
            userNames[i] = neighbours.get(i).getUsername();
        }
        // instantiating the custom row adapter
        neighbourhoodRowAdapter = new CustomNeighbourhoodRowAdapter(this, userNames, neighbours);
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


        byRequesterRating = new Comparator<User>() {
            @Override
            public int compare(User lhs, User rhs) {
                return Integer.valueOf(lhs.getRating().getRatingAsRequester()).compareTo(rhs.getRating().getRatingAsRequester());
            }
        };

        byApplicantRating = new Comparator<User>() {
            @Override
            public int compare(User lhs, User rhs) {
                return Integer.valueOf(lhs.getRating().getRatingAsApplicant()).compareTo(rhs.getRating().getRatingAsApplicant());
            }
        };

        byEndrosedBy = new Comparator<User>() {
            @Override
            public int compare(User lhs, User rhs) {
                return Integer.valueOf(lhs.getRating().getEndorsedBy()).compareTo(rhs.getRating().getEndorsedBy());
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        neighbours = DB.getUsers();
        neighbourhoodRowAdapter.setNeighbours(neighbours);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (view.getId() == sortS.getId()) {
            switch (position) {
                case 1://requester rating
                    Collections.sort(neighbours, byRequesterRating);
                    System.out.println("sorting by requester rating");
                    break;
                case 2://applicant rating
                    System.out.println("sorting by applicant rating");
                    Collections.sort(neighbours, byApplicantRating);
                    break;
                case 3://endorced by
                    System.out.println("sorting by endorsed by");
                    Collections.sort(neighbours, byEndrosedBy);
                    break;
                default:// None
                    System.out.println("sorting by None");
                    neighbours = DB.getUsers();
            }
            neighbourhoodRowAdapter.setNeighbours(neighbours);
        }
    }
}

