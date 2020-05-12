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

public class General_chat extends AppCompatActivity {

    TextView friendtitle,friendbio,back,delete,report;
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
        setContentView(R.layout.activity_general_chat);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");


        back = findViewById(R.id.btnBackmessage1);
        send = findViewById(R.id.btnSend_message1);
        inputText = findViewById(R.id.typehere1);
        msgListView = findViewById(R.id.listViewmessage1);



        db = new DatabaseHelper(this);
        Cursor message = db.get_generalchat();
        if(message.getCount()>0){
            while(message.moveToNext()){
                String getmessageValue = message.getString(1);
                String sender = message.getString(0);
                Msg showmessageValue;
                if(sender.equals(email)){
                    showmessageValue =new Msg(getmessageValue,Msg.TYPE_SEND);
                    msgList.add(showmessageValue);
                }else{
                    showmessageValue = new Msg(getmessageValue,Msg.TYPE_RECEIVED);
                    msgList.add(showmessageValue);
                }
            }
        }

        adapter = new MsgAdapter(General_chat.this, R.layout.msg_item, msgList);
        msgListView.setAdapter(adapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(General_chat.this, Forum.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor message = db.getName(email);
                message.moveToFirst();
                String name = message.getString(1);
                String content = name +": " +inputText.getText().toString();
                if(!content.equals("")) {
                    //Instant message display
                    Msg msg = new Msg(content, Msg.TYPE_SEND);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();
                    msgListView.setSelection(msgList.size());
                    inputText.setText("");
                    db.send_generalchat(email,content);
                }
            }
        });
    }
}