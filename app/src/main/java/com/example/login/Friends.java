package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Friends extends AppCompatActivity {
    TextView back,title,friendRequest;
    DatabaseHelper db;
    String email,friendEmail;
    ListView friendList;


    ArrayList<String>friendemail = new ArrayList<>();
    ArrayList<String>friendbio = new ArrayList<>();
    ArrayList<String>friendname = new ArrayList<>();
    ArrayList<Bitmap>friendimage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        friendList = findViewById(R.id.listView);

        title = findViewById(R.id.friendtitle);
        Cursor titleValue= db.getName(email);
        titleValue.moveToFirst();
        String string = titleValue.getString(1)+"\'s Friend List";
        title.setText(string);

        Cursor data = db.get_Friendlist(email);
        if(data.getCount() == 0){
            Toast.makeText(getApplicationContext(), "You don't have any friend!", Toast.LENGTH_SHORT).show();
        }else{
            while(data.moveToNext()){
                //add frend's email array
                friendEmail = data.getString(0);
                friendemail.add(friendEmail);


                //fill friend name array
                Cursor Name = db.getName(friendEmail);
                Name.moveToFirst();
                String name = Name.getString(1);
                friendname.add(name);

                //fill email---bio array
                String bio = Name.getString(2);
                friendbio.add(bio);

                //fill image bitmap array
                Bitmap Imagepath = db.getimage(friendEmail);
                friendimage.add(Imagepath);
            }
        }

        friendlist_adapter myAdapter = new friendlist_adapter(this,friendbio,friendname,friendimage);
        friendList.setAdapter(myAdapter);
        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Friends.this, Message.class);
                intent.putExtra("email",email);
                intent.putExtra("friendEmail",friendemail.get(position));
                intent.putExtra("friendName",friendname.get(position));
                intent.putExtra("friendBio",friendbio.get(position));
                startActivity(intent);
            }
        });

        friendRequest = findViewById((R.id.btnviewRequest));

        friendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Friends.this, Friend_request.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });


        back = findViewById(R.id.btnBackF);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Friends.this, Main_menu.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }
}

