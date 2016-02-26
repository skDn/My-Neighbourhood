package com.myneighbourhood.Yordan_Yordanov;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.BaseActivity;
import com.myneighbourhood.Velin_Kerkov.MainActivity;
import com.myneighbourhood.utils.News;
import com.myneighbourhood.utils.User;

/**
 * Created by yordanyordanov on 26/02/2016.
 */
public class NewsActivity extends BaseActivity {

    private long newsID;
    private int tab;

    private News currentNews;
    private User createdByUser;

    ImageView userImage;
    TextView userName;

    ImageView newsImage;
    TextView newsTitle;
    TextView newsDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);

        Intent i = getIntent();
        newsID = i.getLongExtra("newsId", 0);
        tab = i.getIntExtra("tab", 0);

        // getting news and user from the database
        currentNews = DB.getNews(newsID);
        createdByUser = currentNews.getCreator();

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(currentNews.getTitle());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
//        userImage = (ImageView) findViewById(R.id.newsCreatedByUserImage);
        userName = (TextView) findViewById(R.id.newsCreatedByUserName);

//        newsImage = (ImageView) findViewById(R.id.newsCoverImage);
        newsDescription = (TextView) findViewById(R.id.newsDescriptionText);
        newsTitle = (TextView) findViewById(R.id.newsDescriptionTitle);

        userName.setText(createdByUser.getUsername());

        newsDescription.setText(currentNews.getText());
        newsTitle.setText(currentNews.getTitle());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myIntent = new Intent(this, MainActivity.class);
        myIntent.putExtra("tab", tab);
        startActivity(myIntent);
    }
}
