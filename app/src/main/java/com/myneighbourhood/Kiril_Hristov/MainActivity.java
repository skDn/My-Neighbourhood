package com.myneighbourhood.Kiril_Hristov;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.LoginActivity;
import com.myneighbourhood.utils.UserSharedPref;

public class MainActivity extends AppCompatActivity {

    UserSharedPref userSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userSharedPref = UserSharedPref.getInstance(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!userSharedPref.getIfLoggedIn()){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}
