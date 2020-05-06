package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Pet_News extends AppCompatActivity {
    TextView Back, Next, New, Title, Description;
    ImageView Picture;
    String BackActivity = "Pet_News";
    String email;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_news);

        db = new DatabaseHelper(this);


        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        Back = (TextView) findViewById(R.id.tvPetNews_Back);
        Next = (TextView) findViewById(R.id.tvPetNews_Next);
        New = (TextView) findViewById(R.id.tvPetNews_Post);
        Title = findViewById(R.id.tvPetNews_ArticleTitle);
        Description = findViewById(R.id.tvPetvNews_ArticleDescription);
        Picture = findViewById(R.id.imageView7);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pet_News.this, Forum.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pet_News.this, New_Post.class);
                intent.putExtra("BackActivity", BackActivity);
                startActivity(intent);
            }
        });

        Cursor cursor = db.news_data();
        while(cursor.moveToNext()){
            Title.setText(cursor.getString(0));
            //Description.setText(cursor.getString(1));
        }
        //Picture.setImageBitmap(db.news_image());

    }
}
