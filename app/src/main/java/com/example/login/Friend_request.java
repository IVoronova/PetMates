package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Friend_request extends AppCompatActivity {

    TextView back,title;
    DatabaseHelper db;
    String email,friendEmail;
    ListView friendList;
    ArrayList<String> friendemail = new ArrayList<>();
    ArrayList<String>friendbio = new ArrayList<>();
    ArrayList<String>friendname = new ArrayList<>();
    ArrayList<Bitmap>friendimage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        friendList = findViewById(R.id.listView1);

        title = findViewById(R.id.friendtitle1);
        Cursor titleValue= db.getName(email);
        titleValue.moveToFirst();
        String string = titleValue.getString(1)+"\'s Friend Request";
        title.setText(string);




        Cursor data = db.get_Friendrequest(email);
        if(data.getCount() == 0){
            Toast.makeText(getApplicationContext(), "You don't have any friend request!", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(Friend_request.this, Accept_friend_request.class);
                intent.putExtra("email",email);
                intent.putExtra("friendEmail",friendemail.get(position));
                startActivity(intent);
            }
        });


        back = findViewById(R.id.btnBackFR);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Friend_request.this,Friends.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
    }

}
