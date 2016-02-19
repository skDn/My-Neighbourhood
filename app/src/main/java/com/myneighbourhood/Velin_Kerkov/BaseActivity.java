package com.myneighbourhood.Velin_Kerkov;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.myneighbourhood.R;
import com.myneighbourhood.utils.UserSharedPref;

public class BaseActivity extends AppCompatActivity {


    private Toolbar toolbar;
    protected static UserSharedPref SP;
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

        if (SP != null) {
            SP = UserSharedPref.getInstance(this);
        }

    }
}
