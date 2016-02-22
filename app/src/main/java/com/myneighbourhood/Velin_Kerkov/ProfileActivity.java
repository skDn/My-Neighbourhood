package com.myneighbourhood.Velin_Kerkov;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.myneighbourhood.R;
import com.myneighbourhood.utils.Utils;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button logOffBTN = (Button) findViewById(R.id.profile_B_log_off);
        logOffBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileActivity.user = null;
                SP.setUserLoggedIn(false);
                SP_VILI_EDITOR.remove(Utils.SP_LAST_USER_ID);
                SP_VILI_EDITOR.apply();

                Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }
}
