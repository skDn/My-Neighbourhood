package com.myneighbourhood.Velin_Kerkov;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.myneighbourhood.Kiril_Hristov.DBHelper;
import com.myneighbourhood.R;
import com.myneighbourhood.utils.User;
import com.myneighbourhood.utils.UserSharedPref;
import com.myneighbourhood.utils.Utils;

public class BaseActivity extends AppCompatActivity {

    // shared globals
    protected static UserSharedPref SP;
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_profile:
                Intent i = new Intent(this, ProfileActivity.class);
                startActivity(i);
                return true;
            default:
                return false;
        }
    }

    protected boolean useToolbar() {
        return true;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SP == null) {
            SP = UserSharedPref.getInstance(this);
        }

        if (SP_VILI == null) {
            SP_VILI = getSharedPreferences(Utils.SP, MODE_PRIVATE);
            SP_VILI_EDITOR = SP_VILI.edit();
        }

        if (DB == null) {
            DB = DBHelper.getInstance(this);
        }

    }
}
