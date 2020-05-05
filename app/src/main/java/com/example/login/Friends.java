package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Friends extends AppCompatActivity {
    TextView back;
    ListView listView;
    DatabaseHelper db;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        List<String> friendlist = new ArrayList<>();
        Cursor friend_name = db.get_Friendlist(email);
        while(friend_name.moveToNext()){


        }

        back = findViewById(R.id.btnBackF);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Friends.this,Main_menu.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
    }
}
