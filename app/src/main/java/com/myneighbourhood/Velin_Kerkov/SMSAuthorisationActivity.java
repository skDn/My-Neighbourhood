package com.myneighbourhood.Velin_Kerkov;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.myneighbourhood.R;

public class SMSAuthorisationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsauthorisation);

        Button goBTN = (Button) findViewById(R.id.sms_authorisatoin_B_go);
        EditText codeET = (EditText) findViewById(R.id.sms_authorisatoin_ET_code);

        goBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SMSAuthorisationActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }
}
