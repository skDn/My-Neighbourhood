package com.myneighbourhood.Velin_Kerkov;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.google.android.gms.maps.MapView;
import com.myneighbourhood.R;
import com.myneighbourhood.utils.User;
import com.myneighbourhood.utils.Utils;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends BaseActivity {

    private static final int REQUEST_BROWSE_GALLERY = 1;
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
        mainLayoutSV = (ScrollView) findViewById(R.id.register_SV_main);
        emailET = (EditText) findViewById(R.id.register_ET_email);
        passwordET = (EditText) findViewById(R.id.register_ET_password);
        phoneET = (EditText) findViewById(R.id.register_ET_phone);
        usernameET = (EditText) findViewById(R.id.register_ET_username);
        addressET = (EditText) findViewById(R.id.register_ET_address);
        profilePicIV = (CircleImageView) findViewById(R.id.register_CIV_profile_picture);


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

            Bitmap bitmapFromURI = getBitmapFromURI(targetUri, 150, 150);
            profilePicIV.setImageBitmap(bitmapFromURI);
        }
    }
}
