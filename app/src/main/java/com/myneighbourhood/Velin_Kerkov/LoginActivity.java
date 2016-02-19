package com.myneighbourhood.Velin_Kerkov;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.myneighbourhood.Kiril_Hristov.MainActivity;
import com.myneighbourhood.R;
import com.myneighbourhood.utils.User;

public class LoginActivity extends BaseActivity {

    private EditText passwordET;
    private EditText usernameET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // bind to UI elements
        passwordET = (EditText) findViewById(R.id.login_ET_password);
        usernameET = (EditText) findViewById(R.id.login_ET_username);
        Button loginBTN = (Button) findViewById(R.id.login_B_login);
        Button registerBTN = (Button) findViewById(R.id.login_B_register);


        // set up listeners
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordET.getText().toString();
                String username = usernameET.getText().toString();
                SP.setUserLoggedIn(true);
                SP.storeUserData(new User(username, password));
                
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

    }
}
