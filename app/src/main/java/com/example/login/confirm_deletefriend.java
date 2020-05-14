package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class confirm_deletefriend extends AppCompatActivity {

    String email,friendEmail,friendName,friendBio,textvalue;
    TextView text;
    Button yes, no;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_deletefriend);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        friendEmail = intent.getStringExtra("friendEmail");
        friendName = intent.getStringExtra("friendName");
        friendBio = intent.getStringExtra("friendBio");

        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        text = findViewById(R.id.makesure);

        textvalue = "Are you sure you want to delete your friend "+friendName+"?";
        text.setText(textvalue);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deletefriend(email,friendEmail);
                Toast.makeText(getApplicationContext(), "You and "+friendName+" now are no longer friend.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(confirm_deletefriend.this, Friends.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(confirm_deletefriend.this,View_friend_profile.class);
                intent.putExtra("email",email);
                intent.putExtra("friendEmail",friendEmail);
                intent.putExtra("friendName",friendName);
                intent.putExtra("friendBio",friendBio);
                startActivity(intent);
            }
        });
    }
}
