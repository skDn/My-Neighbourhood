package com.myneighbourhood.Velin_Kerkov;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;

import com.myneighbourhood.Kiril_Hristov.ApplicantsActivity;
import com.myneighbourhood.Kiril_Hristov.MyRequestsFragment;
import com.myneighbourhood.Kiril_Hristov.RequestFeedFragment;
import com.myneighbourhood.R;
import com.myneighbourhood.Yordan_Yordanov.NewsFeedFragment;
import com.myneighbourhood.utils.CustomNotification;
import com.myneighbourhood.utils.DBHelper;
import com.myneighbourhood.utils.User;
import com.myneighbourhood.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RequestFeedFragment requestFeedFragment;
    private MyRequestsFragment myRequestsFragment;
    private NewsFeedFragment newsFeedFragment;
    public static final int NOTIFICATION_ID = 1;
    static private int notId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
        if (user == null) {
            long lastLoginUserId = SP_VILI.getLong(Utils.SP_LAST_USER_ID, -1);
            if (lastLoginUserId == -1) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                User user = DB.getUser(lastLoginUserId);
                if (user == null) {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                } else {
                    setLoggedInUser(user);
                }
            }
        }


        TabLayout tabs = (TabLayout) findViewById(R.id.main_TL_tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_VP_viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        if (requestFeedFragment == null) {
            requestFeedFragment = RequestFeedFragment.newInstance();
        }

        if (myRequestsFragment == null) {
            myRequestsFragment = MyRequestsFragment.newInstance();
        }

        if (newsFeedFragment == null) {
            newsFeedFragment = NewsFeedFragment.newInstance();
        }

        adapter.addFragment(requestFeedFragment, "Requests");
        adapter.addFragment(myRequestsFragment, "My Requests");
        adapter.addFragment(newsFeedFragment, "News");
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
        if (getIntent().getIntExtra("tab", 0) == 1) {
            viewPager.setCurrentItem(1);
        } else if (getIntent().getIntExtra("tab", 0) == 2) {
            viewPager.setCurrentItem(2);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<CustomNotification> notifications = DB.getAllNotifications(user);
        for (CustomNotification not : notifications) {
            sendNotification(not.getText(), not.getId());
        }
    }

    private void sendNotification(String msg, long notId) {
        NotificationManager mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, ApplicantsActivity.class), 0);

        DB.notificationSeen(notId);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("MyNeighbourhood")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg)
                        .setSound(alarmSound)
                        .setAutoCancel(true);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify((int) notId, mBuilder.build());
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public DBHelper getDB() {
        return DB;
    }
}
