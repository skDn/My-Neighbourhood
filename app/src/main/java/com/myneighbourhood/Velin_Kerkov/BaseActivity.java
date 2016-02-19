package com.myneighbourhood.Velin_Kerkov;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.myneighbourhood.R;
import com.myneighbourhood.utils.Utils;

public class BaseActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private SharedPreferences SP;
    private SharedPreferences.Editor SP_EDITOR;

    @Override
    public void setContentView(int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, null);
        configureToolbar(view);
        super.setContentView(view);
    }

    protected boolean useToolbar() {
        return true;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SP == null || SP_EDITOR == null) {
            SP = getSharedPreferences(Utils.SP, MODE_PRIVATE);
            SP_EDITOR = SP.edit();
        }

    }
}
