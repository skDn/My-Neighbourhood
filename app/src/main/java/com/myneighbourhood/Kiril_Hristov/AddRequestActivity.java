package com.myneighbourhood.Kiril_Hristov;

import android.content.Intent;
import android.os.Bundle;
import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.BaseActivity;
import com.myneighbourhood.Velin_Kerkov.MainActivity;

public class AddRequestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myIntent = new Intent(this, MainActivity.class);
        myIntent.putExtra("tab", 1);
        startActivity(myIntent);
        finish();
    }
}
