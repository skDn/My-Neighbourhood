package com.myneighbourhood.Velin_Kerkov;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.myneighbourhood.R;
import com.myneighbourhood.utils.User;
import com.myneighbourhood.utils.Utils;

public class RegisterActivity extends BaseActivity {

    private EditText emailET;
    private EditText passwordET;
    private EditText phoneET;
    private EditText usernameET;

    @Override
    protected boolean useToolbar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // bind UI elements
        Button registerBTN = (Button) findViewById(R.id.register_B_register);
        emailET = (EditText) findViewById(R.id.register_ET_email);
        passwordET = (EditText) findViewById(R.id.register_ET_password);
        phoneET = (EditText) findViewById(R.id.register_ET_phone);
        usernameET = (EditText) findViewById(R.id.register_ET_username);

        // set up onClick listeners
        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                String phone = phoneET.getText().toString();
                String username = usernameET.getText().toString();

                User user = DB.registerUser(username, password, email, phone);
                setLoggedInUser(user);
                SP_VILI_EDITOR.putInt(Utils.SP_LAST_USER_ID, user.getId());
                SP_VILI_EDITOR.apply();
                SP.setUserLoggedIn(true);

                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}
