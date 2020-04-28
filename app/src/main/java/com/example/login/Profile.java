package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Profile extends AppCompatActivity {

    Button _Save,_Edit_information,_Logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        _Save = (Button) findViewById(R.id.btnProfile_save);
        _Edit_information = (Button) findViewById(R.id.btnedit_account);
        _Logout = (Button) findViewById(R.id.btnProfile_logout);
        _Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
