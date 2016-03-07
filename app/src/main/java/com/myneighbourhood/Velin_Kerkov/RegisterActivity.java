package com.myneighbourhood.Velin_Kerkov;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.google.android.gms.maps.MapView;
import com.myneighbourhood.R;
import com.myneighbourhood.utils.Address;
import com.myneighbourhood.utils.User;
import com.myneighbourhood.utils.Utils;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends BaseActivity {


    private EditText emailET;
    private EditText passwordET;
    private EditText phoneET;
    private EditText usernameET;
    private EditText addressET;
    private CircleImageView profilePicIV;
    private FrameLayout mapLayout;
    private Fragment mapFragment;
    private ScrollView mainLayoutSV;
    private MapView mapView;
    private EditText firstNameET;
    private EditText lastNameET;
    Bitmap profilePicture = null;

    @Override
    protected boolean useToolbar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        profilePicture = BitmapFactory.decodeResource(getResources(), R.drawable.ic_account_circle_black_36dp);

        // bind UI elements
        Button registerBTN = (Button) findViewById(R.id.register_B_register);
        mainLayoutSV = (ScrollView) findViewById(R.id.register_SV_main);
        emailET = (EditText) findViewById(R.id.register_ET_email);
        passwordET = (EditText) findViewById(R.id.register_ET_password);
        phoneET = (EditText) findViewById(R.id.register_ET_phone);
        usernameET = (EditText) findViewById(R.id.register_ET_username);
        addressET = (EditText) findViewById(R.id.register_ET_address);
        profilePicIV = (CircleImageView) findViewById(R.id.register_CIV_profile_picture);
        firstNameET = (EditText) findViewById(R.id.register_ET_first_name);
        lastNameET = (EditText) findViewById(R.id.register_ET_last_name);

        // set up onClick listeners
        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                String phone = phoneET.getText().toString();
                String username = usernameET.getText().toString();
                String firstName = firstNameET.getText().toString();
                String lastName = lastNameET.getText().toString();
                String address = addressET.getText().toString();

                User user = DB.registerUser(new User(username, firstName, lastName, password, email, phone, profilePicture), new Address("100 Gibson Street", 0, 0));
                setLoggedInUser(user);
                SP_VILI_EDITOR.putLong(Utils.SP_LAST_USER_ID, user.getId());
                SP_VILI_EDITOR.apply();

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_BROWSE_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri targetUri = data.getData();

            Bitmap bitmapFromURI = getBitmapFromURI(targetUri, 150, 150);
            profilePicIV.setImageBitmap(bitmapFromURI);
            profilePicture = bitmapFromURI;
        }
    }
}
