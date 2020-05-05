package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class New_Post extends AppCompatActivity {
    TextView Back, Post;
    EditText Title, Description;
    String BackActivity,email;
    ImageButton Picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__post);

        //gets the string of the last activity directed
        Intent intent = getIntent();
        BackActivity = intent.getStringExtra("Back Activity");
        email = intent.getStringExtra("email");

        Title = (EditText) findViewById(R.id.etNewPost_Title);
        Description = (EditText) findViewById(R.id.etNewPost_Description);
        Back = (TextView) findViewById(R.id.tvPetNews_Back);
        Post = (TextView) findViewById(R.id.tvPetNews_Post);
        Picture = (ImageButton) findViewById(R.id.btnNewPost_AddPic);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goes to which activity last directed
                if(BackActivity.equals("Pet_News")){
                    Intent intent = new Intent(New_Post.this, Pet_News.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                }
            }
        });

        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
