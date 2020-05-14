package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Message extends AppCompatActivity {

    TextView friendtitle,friendbio,back,viewprofile;
    ImageView friendicon;
    String email,friendEmail,friendName,friendBio;
    DatabaseHelper db;
    EditText inputText;
    MsgAdapter adapter;
    Button send;
    ListView msgListView;

    List<Msg> msgList = new ArrayList<Msg>();

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
        friendbio = findViewById(R.id.friendbioo);
        send = findViewById(R.id.btnSend_message);
        inputText = findViewById(R.id.typehere);
        msgListView = findViewById(R.id.listViewmessage);


        viewprofile= findViewById(R.id.viewfriendsprofile);

        friendicon.setImageBitmap(db.getimage(friendEmail));
        friendtitle.setText(friendName);
        friendbio.setText(friendBio);

        db = new DatabaseHelper(this);
        Cursor message = db.get_message(email,friendEmail);
        if(message.getCount()>0){
            while(message.moveToNext()){
                String getmessageValue = message.getString(0);
                String sender = message.getString(1);
                Msg showmessageValue;
                if(sender.equals(email)){
                    showmessageValue = new Msg(getmessageValue,Msg.TYPE_SEND);
                    msgList.add(showmessageValue);
                }else{
                    showmessageValue = new Msg(getmessageValue,Msg.TYPE_RECEIVED);
                    msgList.add(showmessageValue);
                }
            }
        }

        adapter = new MsgAdapter(Message.this, R.layout.msg_item, msgList);
        msgListView.setAdapter(adapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Message.this, Friends.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                if(!content.equals("")) {
                    //Instant message display
                    Msg msg = new Msg(content, Msg.TYPE_SEND);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();
                    msgListView.setSelection(msgList.size());
                    inputText.setText("");
                    db.send_message(email,friendEmail,content);
                }
            }
        });


        viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Message.this, View_friend_profile.class);
                intent.putExtra("email",email);
                intent.putExtra("friendEmail",friendEmail);
                intent.putExtra("friendName",friendName);
                intent.putExtra("friendBio",friendBio);
                startActivity(intent);
            }
        });
    }
}