package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Message extends AppCompatActivity {

    TextView friendtitle,back;
    ImageView friendicon;
    String email,friendEmail,friendName,friendBio;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        friendEmail = intent.getStringExtra("friendEmail");
        friendName = intent.getStringExtra("friendName");
        friendBio = intent.getStringExtra("friendBio");

        friendicon = findViewById(R.id.friendimage);
        friendtitle = findViewById(R.id.friendname);
        back = findViewById(R.id.btnBackmessage);

        friendicon.setImageBitmap(db.getimage(friendEmail));
        friendtitle.setText(friendName);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Message.this, Friends.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });


    }
}
