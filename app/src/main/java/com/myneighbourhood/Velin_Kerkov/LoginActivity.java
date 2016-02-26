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
import com.myneighbourhood.utils.Address;
import com.myneighbourhood.utils.Chat;
import com.myneighbourhood.utils.Request;
import com.myneighbourhood.utils.User;
import com.myneighbourhood.utils.Utils;

import java.util.Calendar;

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

        User admin = new User("admin", "fName", "lName", "pass", "mail@mail.com", "080808", null);
        User vili = new User("vili", "fName", "lName", "pass", "mail@mail.com", "080808", null);
        DB.registerUser(admin, new Address("100 Gibson Street", 55.8734611, -4.2890117));
        DB.registerUser(vili, new Address("100 Gibson Street", 55.8734611, -4.2890117));

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 10);

        Request adminRequest = new Request(admin.getId(), "Test adminRequest", "Test description", 1, cal.getTimeInMillis());
        adminRequest = DB.addRequestFromUI(adminRequest);
        Chat chat = DB.addChat(admin, vili, adminRequest);


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
