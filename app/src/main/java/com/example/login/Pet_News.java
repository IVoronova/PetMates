package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Pet_News extends AppCompatActivity {
    int num_news = 0;
    TextView Back, Next, New;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_news);

        Back = (TextView) findViewById(R.id.tvPetNews_Back);
        Next = (TextView) findViewById(R.id.tvPetNews_Next);
        New = (TextView) findViewById(R.id.tvPetNews_Post);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pet_News.this, Forum.class);
                startActivity(intent);
            }
        });

        New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
}
