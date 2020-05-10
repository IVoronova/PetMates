package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class report_chat_history extends AppCompatActivity {

    TextView back,title;
    ListView Fullhistory;
    String reportedEmail, id;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_chat_history);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        reportedEmail = intent.getStringExtra("reportedEmail");
        id = intent.getStringExtra("id");

        title = findViewById(R.id.titlefull);
        Fullhistory = findViewById(R.id.fullChathistory);
        back = findViewById(R.id.backfull);

        title.setText(id);

        ArrayList<String> list = new ArrayList<>();

        Cursor messageHistory = db.get_message_history(reportedEmail);
        if(messageHistory.getCount()==0){
            Toast.makeText(getApplicationContext(), "No chat history!", Toast.LENGTH_SHORT).show();
        }else {
            while (messageHistory.moveToNext()) {
                list.add("\nSent to: " + messageHistory.getString(1) +
                        "\n" + messageHistory.getString(0) +
                        "\n");
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
                Fullhistory.setAdapter(listAdapter);
            }
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(report_chat_history.this, report_detail.class);
                intent.putExtra("reportedEmail",reportedEmail);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }
}

