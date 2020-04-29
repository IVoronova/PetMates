package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main_menu extends AppCompatActivity {
    Button _Profile,_Friends,_Pairing,_Forum,_Support;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");

        _Profile = (Button)findViewById(R.id.btnProfile);
        _Friends = (Button)findViewById(R.id.btnFriends);
        _Pairing = (Button)findViewById(R.id.btnPairing);
        _Forum = (Button)findViewById(R.id.btnForum);
        _Support = (Button)findViewById(R.id.btnApplication_support);

        _Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_menu.this,Profile.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        _Friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_menu.this,Friends.class);
                startActivity(intent);
            }
        });
        _Pairing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_menu.this,Pairing.class);
                startActivity(intent);
            }
        });
        _Forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_menu.this,Forum.class);
                startActivity(intent);
            }
        });
        _Support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_menu.this,Application_Support.class);
                startActivity(intent);
            }
        });
    }
}
