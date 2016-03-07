package com.myneighbourhood.Yordan_Yordanov;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.BaseActivity;
import com.myneighbourhood.Velin_Kerkov.MainActivity;
import com.myneighbourhood.utils.News;

import java.util.Calendar;

public class AddNewsActivity extends BaseActivity {

    ImageView addNewsImage;
    EditText newsTitle;
    EditText newsDescription;
    Button submitNews;
    Bitmap imageToSave;
    int tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        Intent i = getIntent();
        tab = i.getIntExtra("tab", 0);

        newsTitle = (EditText) findViewById(R.id.addNewsTitle);
        newsDescription = (EditText) findViewById(R.id.addNewsDescription);
        submitNews = (Button) findViewById(R.id.addNewsSubmit);

        addNewsImage = (ImageView) findViewById(R.id.addNewsImage);
        addNewsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseGallery();
            }
        });


        submitNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("adding news to db");
                String title = newsTitle.getText().toString();
                String description = newsDescription.getText().toString();
                if (title.equals(""))
                    showDialogWithOkButton("Please specify a title for the request!");
                else {
                    Calendar cal = Calendar.getInstance();
                    /// id?
                    DB.addNews(new News(getUser(), title, description, cal.getTimeInMillis(), imageToSave));
                    onBackPressed();
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_BROWSE_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri targetUri = data.getData();

            Bitmap bitmapFromURI = getBitmapFromURI(targetUri, 500, 300);
            addNewsImage.setImageBitmap(bitmapFromURI);

            imageToSave = bitmapFromURI;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myIntent = new Intent(this, MainActivity.class);
        myIntent.putExtra("tab", tab);
        startActivity(myIntent);
        finish();
    }
}
