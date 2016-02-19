package com.myneighbourhood.Velin_Kerkov;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.myneighbourhood.R;
import com.myneighbourhood.utils.Utils;

public class BaseActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private SharedPreferences SP;
    private SharedPreferences.Editor SP_EDITOR;
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
