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
import android.widget.TextView;

import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.BaseActivity;
import com.myneighbourhood.Velin_Kerkov.MainActivity;
import com.myneighbourhood.utils.Request;

import java.util.Calendar;

public class AddRequestActivity extends BaseActivity implements NumberPicker.OnValueChangeListener {

    LinearLayout addRequestLayout;
    EditText titleField, descriptionField;
    TextView displayPeople, displayExpires;
    Button postButton, selectNumberPeople, selectExpiresIn;

    private int numberOfPeopleSelected = 1 ;
    private int hourSelected = 1;
    String[] hours ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
        getSupportActionBar().setTitle("Add Request");

        titleField = (EditText) findViewById(R.id.AddRequestTitle);
        descriptionField = (EditText) findViewById(R.id.AddRequestDescription);
        postButton = (Button) findViewById(R.id.AddRequestPostButton);
        selectNumberPeople = (Button) findViewById(R.id.selectNumberPeopleButton);
        displayPeople = (TextView) findViewById(R.id.displayPeople);
        selectExpiresIn = (Button) findViewById(R.id.selectExpiresIn);
        displayExpires = (TextView) findViewById(R.id.displayExpires);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("VLIZAM TUKA");
                String title = titleField.getText().toString();
                String description = descriptionField.getText().toString();
                if (title.equals("")) showDialogWithOkButton("Please specify a title for the request!");
                else{
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.HOUR, hourSelected);
                    DB.addRequest(new Request(user.getId(), title, description, numberOfPeopleSelected, cal.getTimeInMillis()));
                    onBackPressed();
                }
            }
        });

        numberOfPeopleSelected = 1;
        selectNumberPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPicker();
            }
        });

        selectExpiresIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExpirePicker();
            }
        });

        hours = new String[]{"1", "2", "3", "4", "5", "12", "24", "48", "72", "96"};

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

    protected void changeDisplays() {
        super.onResume();
        if(numberOfPeopleSelected > 1) {
            displayPeople.setText(numberOfPeopleSelected+"");
        }
        if(hourSelected > 1){
            String text = hourSelected+"h";
            displayExpires.setText(text);
        }

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        if(picker.getId()==R.id.NumberPickerPeople) {
            numberOfPeopleSelected = newVal;
            System.out.println(" PEOPLE " + numberOfPeopleSelected);
        }
        else if(picker.getId()==R.id.ExpiresPicker){
            hourSelected = newVal;
            System.out.println(" HORS " + hourSelected);
        }
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
        //user.getId()
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDisplays();
                d.dismiss();
            }
        });
        d.show();
    }

    public void showExpirePicker() {
        final Dialog exDialog = new Dialog(AddRequestActivity.this);
        exDialog.setTitle("Expires in(h):");
        exDialog.setContentView(R.layout.dialog_expires_picker);
        Button done = (Button) exDialog.findViewById(R.id.ExpiresPickerButton);
        final NumberPicker expiresPicker = (NumberPicker) exDialog.findViewById(R.id.ExpiresPicker);
        expiresPicker.setMaxValue(hours.length-1);
        expiresPicker.setMinValue(1);
        expiresPicker.setDisplayedValues(hours);
        expiresPicker.setWrapSelectorWheel(false);
        expiresPicker.setOnValueChangedListener(this);
        //user.getId()
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exDialog.dismiss();
                changeDisplays();
            }
        });
        exDialog.show();
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
