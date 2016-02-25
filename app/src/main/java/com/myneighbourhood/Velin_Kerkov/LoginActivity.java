package com.myneighbourhood.Velin_Kerkov;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.myneighbourhood.R;
import com.myneighbourhood.utils.User;
import com.myneighbourhood.utils.Utils;

public class LoginActivity extends BaseActivity {

    private EditText passwordET;
    private EditText usernameET;
    private RelativeLayout mainLayoutLL;

    @Override
    protected boolean useToolbar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // bind to UI elements
        mainLayoutLL = (RelativeLayout) findViewById(R.id.login_RL_main);
        passwordET = (EditText) findViewById(R.id.login_ET_password);
        usernameET = (EditText) findViewById(R.id.login_ET_username);
        Button loginBTN = (Button) findViewById(R.id.login_B_login);
        Button registerBTN = (Button) findViewById(R.id.login_B_register);


        mainLayoutLL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });

        // set up listeners
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordET.getText().toString();
                String username = usernameET.getText().toString();

                User user = DB.getUser(username, password);
                if (user != null) {
                    SP_VILI_EDITOR.putInt(Utils.SP_LAST_USER_ID, user.getId());
                    SP_VILI_EDITOR.apply();
                    setLoggedInUser(user);
                    SP.setUserLoggedIn(true);

                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "User credentials wrong", Toast.LENGTH_LONG).show();
                }
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
