package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Forum extends AppCompatActivity {

    TextView back;
    Button Pet_News, Gallery, Lost_and_Found, Pet_Jobs, Report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");

        back = (TextView) findViewById(R.id.btnBackF);
        Pet_News = (Button) findViewById(R.id.btnPet_News);
        Gallery = (Button) findViewById(R.id.btnGalleries);
        Lost_and_Found = (Button) findViewById(R.id.btnLost_Found);









































        Pet_Jobs = (Button) findViewById(R.id.btnPet_Jobs);
        Report = (Button) findViewById(R.id.btnReport);

        Pet_News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forum.this, Pet_News.class);
                startActivity(intent);
            }
        });

        //back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forum.this,Main_menu.class);
                intent.putExtra("email",email);
                startActivity(intent);

            }
        });
    }
}
