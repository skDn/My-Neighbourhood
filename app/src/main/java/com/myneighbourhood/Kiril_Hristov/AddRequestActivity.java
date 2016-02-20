package com.myneighbourhood.Kiril_Hristov;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.BaseActivity;
import com.myneighbourhood.Velin_Kerkov.MainActivity;

public class AddRequestActivity extends BaseActivity implements View.OnClickListener{

    LinearLayout addRequestLayout;
    EditText titleField;
    EditText descriptionField;
    NumberPicker peoplePicker;
    DatePicker datePicker;
    Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
        getSupportActionBar().setTitle("Add Request");

        titleField = (EditText) findViewById(R.id.AddRequestTitle);
        descriptionField = (EditText) findViewById(R.id.AddRequestDescription);
        peoplePicker = (NumberPicker) findViewById(R.id.AddRequestNumberPickerPeople);
        datePicker = (DatePicker) findViewById(R.id.AddRequestDatePicker);
        postButton = (Button) findViewById(R.id.AddRequestPostButton);

        // hide keyboard on ACTION_DOWN
        addRequestLayout = (LinearLayout) findViewById(R.id.AddRequestLinearLayout);
        addRequestLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myIntent = new Intent(this, MainActivity.class);
        myIntent.putExtra("tab", 1);
        startActivity(myIntent);
        finish();
    }

    private void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.AddRequestPostButton:

                String title = titleField.getText().toString();
                String description = descriptionField.getText().toString();
                int peopleNeeded = peoplePicker.getValue();
                System.out.println(peopleNeeded);
                if(title.equals("")) showError();
                else{

                }
                break;
        }
    }

    private void showError(){
        AlertDialog.Builder allertBuilder = new AlertDialog.Builder(this);
        allertBuilder.setMessage("Please specify a title for the request!");
        allertBuilder.setPositiveButton("OK", null);
        allertBuilder.show();
    }
}
