package com.myneighbourhood.Velin_Kerkov;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.myneighbourhood.utils.DBHelper;
import com.myneighbourhood.Kiril_Hristov.JobsActivity;
import com.myneighbourhood.R;
import com.myneighbourhood.Yordan_Yordanov.MessagesActivity;
import com.myneighbourhood.Yordan_Yordanov.MyNeighbourhoodActivity;
import com.myneighbourhood.utils.User;
import com.myneighbourhood.utils.Utils;

import java.io.FileNotFoundException;

public class BaseActivity extends AppCompatActivity {

    // shared globals
    protected static final int REQUEST_BROWSE_GALLERY = 1;
    protected static SharedPreferences SP_VILI;
    protected static SharedPreferences.Editor SP_VILI_EDITOR;
    protected static User user;
    protected static DBHelper DB;


    private Toolbar toolbar;
    private FrameLayout contentContainer;


    @Override
    public void setContentView(int layoutResID) {
        View baseView = getLayoutInflater().inflate(R.layout.activity_base, null);
        configureToolbar(baseView);
        contentContainer = (FrameLayout) baseView.findViewById(R.id.base_FL_content);

        View requestedView = getLayoutInflater().inflate(layoutResID, contentContainer, false);
        contentContainer.addView(requestedView);
        super.setContentView(baseView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (showHomeInSteadOfChat()) {
            menu.findItem(R.id.menu_action_home_messages).setVisible(true);
            menu.findItem(R.id.menu_action_messages).setVisible(false);
        }

        if (showHomeInSteadOfMyNeighbourhood()) {
            menu.findItem(R.id.menu_action_home_neighbourhood).setVisible(true);
            menu.findItem(R.id.menu_action_neighbourhood).setVisible(false);
        }

        if (showHomeInSteadOfProfile()) {
            menu.findItem(R.id.menu_action_home_profile).setVisible(true);
            menu.findItem(R.id.menu_action_profile).setVisible(false);
        }

        if (showHomeInSteadOfJobs()) {
            menu.findItem(R.id.menu_action_home_jobs).setVisible(true);
            menu.findItem(R.id.menu_action_jobs).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_profile:
                Intent i = new Intent(this, MyProfileActivity.class);
                startActivity(i);
                return true;
            case R.id.menu_action_messages:
                i = new Intent(this, MessagesActivity.class);
                startActivity(i);
                return true;
            case android.R.id.home:
                onBackPressed();
                //finish();
                return true;
            case R.id.menu_action_neighbourhood:
                i = new Intent(this, MyNeighbourhoodActivity.class);
                startActivity(i);
                return true;
            case R.id.menu_action_home_neighbourhood:
            case R.id.menu_action_home_profile:
            case R.id.menu_action_home_jobs:
            case R.id.menu_action_home_messages:
                i = new Intent(this, MainActivity.class);
                startActivity(i);
                return true;
            case R.id.menu_action_jobs:
                i = new Intent(this, JobsActivity.class);
                startActivity(i);
                return true;
            default:
                return false;
        }
    }

    protected boolean useToolbar() {
        return true;
    }

    protected boolean showHomeInSteadOfChat() {
        return false;
    }

    protected boolean showHomeInSteadOfMyNeighbourhood() {
        return false;
    }

    protected boolean showHomeInSteadOfProfile() {
        return false;
    }

    protected boolean showHomeInSteadOfJobs() {
        return false;
    }


    protected void setLoggedInUser(User user) {
        this.user = user;
    }

    private void configureToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.base_T_toolbar);
        if (toolbar != null) {
            if (useToolbar()) {
                setSupportActionBar(toolbar);
            } else {
                toolbar.setVisibility(View.GONE);
            }
        }
    }

    protected void showDialogWithOkButton(String msg) {
        AlertDialog.Builder allertBuilder = new AlertDialog.Builder(this);
        allertBuilder.setMessage(msg);
        allertBuilder.setPositiveButton("OK", null);
        allertBuilder.show();
    }

    protected void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void browseGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_BROWSE_GALLERY);
    }

    public Bitmap getBitmapFromURI(Uri targetUri, int width, int height) {
        Bitmap image = null;
        try {
            image = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(targetUri));
            int desiredWidth = image.getWidth();
            int desiredHeight = image.getHeight();
            while (desiredWidth / 2 >= width || desiredHeight / 2 >= height) {
                desiredWidth = desiredWidth / 2;
                desiredHeight = desiredHeight / 2;
            }

            image = Bitmap.createScaledBitmap(image, desiredWidth, desiredHeight, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return image;
    }

    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = this.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SP_VILI == null) {
            SP_VILI = getSharedPreferences(Utils.SP, MODE_PRIVATE);
            SP_VILI_EDITOR = SP_VILI.edit();
        }

        if (DB == null) {
            DB = DBHelper.getInstance(this);
        }
    }

    public User getUser() {
        return user;
    }
}
