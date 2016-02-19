package com.myneighbourhood.Kiril_Hristov;

/**
 * Created by kirchoni on 19/02/16.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.myneighbourhood.Yordan_Yordanov.NewsFeedFragment;

public class TabAdapter extends FragmentStatePagerAdapter{

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public TabAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }


    @Override
    public Fragment getItem(int position) {
        if(position == 0) // if the position is 0 we are returning the First tab
        {
            RequestFeedFragment requestFeedFragment = new RequestFeedFragment();
            return requestFeedFragment;
        }
        else
        {
            NewsFeedFragment newsFeedFragment = new NewsFeedFragment();
            return newsFeedFragment;
        }
    }

    @Override
    public int getCount() {
        return 0;
    }
}
