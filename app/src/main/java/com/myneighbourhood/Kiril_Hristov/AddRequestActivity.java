package com.myneighbourhood.Kiril_Hristov;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.BaseActivity;
import com.myneighbourhood.Velin_Kerkov.MainActivity;

public class AddRequestActivity extends BaseActivity implements NumberPicker.OnValueChangeListener {

    LinearLayout addRequestLayout;
    EditText titleField;
    EditText descriptionField;
    DatePicker datePicker;
    Button postButton;
    Button selectNumberPeople;
    Button selectDate;

    private int numberOfPeopleSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
        getSupportActionBar().setTitle("Add Request");

        titleField = (EditText) findViewById(R.id.AddRequestTitle);
        descriptionField = (EditText) findViewById(R.id.AddRequestDescription);

        datePicker = (DatePicker) findViewById(R.id.AddRequestDatePicker);
        postButton = (Button) findViewById(R.id.AddRequestPostButton);
        selectNumberPeople = (Button) findViewById(R.id.selectNumberPeopleButton);
        selectDate = (Button) findViewById(R.id.selectDateButton);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("VLIZAM TUKA");
                String title = titleField.getText().toString();
                String description = descriptionField.getText().toString();
                if (title.equals("")) showError("Please specify a title for the request!");
            }
        });

        numberOfPeopleSelected = 1;
        selectNumberPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPicker();
            }
        });

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        numberOfPeopleSelected = newVal;
        System.out.println(numberOfPeopleSelected);
    }

    public void showNumberPicker() {
        final Dialog d = new Dialog(AddRequestActivity.this);
        d.setTitle("How many people do you need?");
        d.setContentView(R.layout.dialog_number_picker);
        Button done = (Button) d.findViewById(R.id.NumberPickerButton);
        final NumberPicker peoplePicker = (NumberPicker) d.findViewById(R.id.NumberPickerPeople);
        peoplePicker.setMaxValue(10);
        peoplePicker.setMinValue(1);
        peoplePicker.setWrapSelectorWheel(false);
        peoplePicker.setOnValueChangedListener(this);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
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
