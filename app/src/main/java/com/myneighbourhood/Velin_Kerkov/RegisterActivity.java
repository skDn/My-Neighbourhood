package com.myneighbourhood.Velin_Kerkov;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.myneighbourhood.R;
import com.myneighbourhood.utils.User;
import com.myneighbourhood.utils.Utils;

public class RegisterActivity extends BaseActivity {

    private static final int REQUEST_BROWSE_GALLERY = 1;
    private EditText emailET;
    private EditText passwordET;
    private EditText phoneET;
    private EditText usernameET;
    private EditText addressET;
    private ImageView profilePicIV;

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
        addressET = (EditText) findViewById(R.id.register_ET_address);
        profilePicIV = (ImageView) findViewById(R.id.register_IV_profile_picture);

        // set up onClick listeners
        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                String phone = phoneET.getText().toString();
                String username = usernameET.getText().toString();
                String address = addressET.getText().toString();

                User user = DB.registerUser(username, password, email, phone);
                setLoggedInUser(user);
                SP_VILI_EDITOR.putInt(Utils.SP_LAST_USER_ID, user.getId());
                SP_VILI_EDITOR.apply();
                SP.setUserLoggedIn(true);

                Intent i = new Intent(RegisterActivity.this, SMSAuthorisationActivity.class);
                startActivity(i);
            }
        });

        profilePicIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseGallery();
            }
        });

    }

    public void browseGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_BROWSE_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_BROWSE_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri targetUri = data.getData();
            System.out.println(targetUri.toString());
            profilePicIV.setImageBitmap(getBitmapFromURI(targetUri, 150, 150));
        }
    }
}
