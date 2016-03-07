package com.myneighbourhood.Kiril_Hristov;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.myneighbourhood.R;
import com.myneighbourhood.Velin_Kerkov.BaseActivity;
import com.myneighbourhood.Velin_Kerkov.MainActivity;
import com.myneighbourhood.utils.CustomNotification;
import com.myneighbourhood.utils.Request;

import java.util.Calendar;

public class AddRequestActivity extends BaseActivity implements NumberPicker.OnValueChangeListener {

    LinearLayout addRequestLayout;
    EditText titleField, descriptionField;
    TextView displayExpires;
    Button postButton, selectExpiresIn;

    private int hourSelected = 1;
    String[] hours ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
        getSupportActionBar().setTitle("Add Request");
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        titleField = (EditText) findViewById(R.id.AddRequestTitle);
        descriptionField = (EditText) findViewById(R.id.AddRequestDescription);
        postButton = (Button) findViewById(R.id.AddRequestPostButton);
        selectExpiresIn = (Button) findViewById(R.id.selectExpiresIn);
        displayExpires = (TextView) findViewById(R.id.displayExpires);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleField.getText().toString();
                String description = descriptionField.getText().toString();
                if (title.equals("")) showDialogWithOkButton("Please specify a title for the request!");
                else{
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.HOUR, hourSelected);
                    CustomNotification notification = new CustomNotification(CustomNotification.Type.NEW_REQUEST, null, user );
                    DB.addRequest(new Request(user, title, description, 1, cal.getTimeInMillis(), 0), notification);
                    onBackPressed();
                }
            }
        });

        selectExpiresIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExpirePicker();
            }
        });

        hours = new String[]{"1 Hour", "2 Hours", "3 Hours", "4 Hours", "5 Hours", "12 Hours", "24 Hours", "2 Days", "3 Days", "4 Days"};

        // hide keyboard on ACTION_DOWN
        addRequestLayout = (LinearLayout) findViewById(R.id.AddRequestLinearLayout);
        addRequestLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });
        addRequestLayout.requestFocus();
    }

    protected void changeDisplays() {
        super.onResume();
        if(hourSelected > 1){
            String text = hourSelected+"h";
            displayExpires.setText(text);
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        switch (newVal){
            case 6:
                hourSelected = 12;
                break;
            case 7:
                hourSelected = 24;
                break;
            case 8:
                hourSelected = 48;
                break;
            case 9:
                hourSelected = 72;
                break;
            case 10:
                hourSelected = 96;
                break;

            default: hourSelected = newVal;
        }
    }

    public void showExpirePicker() {
        final Dialog exDialog = new Dialog(AddRequestActivity.this);
        exDialog.setTitle("Expires in(h):");
        exDialog.setContentView(R.layout.dialog_expires_picker);
        Button done = (Button) exDialog.findViewById(R.id.ExpiresPickerButton);
        final NumberPicker expiresPicker = (NumberPicker) exDialog.findViewById(R.id.ExpiresPicker);
        expiresPicker.setMaxValue(hours.length);
        expiresPicker.setMinValue(1);
        expiresPicker.setDisplayedValues(hours);
        expiresPicker.setWrapSelectorWheel(false);
        expiresPicker.setOnValueChangedListener(this);
        setDividerColor(expiresPicker);
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

    private void setDividerColor(NumberPicker picker) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        int c = Color.parseColor("#FFA000");
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(c);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
