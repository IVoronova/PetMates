package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Ask_question extends AppCompatActivity {

    EditText title,content;
    Button submit;
    TextView back;
    String email;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        title = findViewById(R.id.etquestionTitle);
        content = findViewById(R.id.etquestionContent);
        submit = findViewById(R.id.btnqestion_save);
        back = findViewById(R.id.btnquestion_back);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleValue = title.getText().toString();
                String contentValue = content.getText().toString();
                if(titleValue.equals("")||contentValue.equals("")){
                    Toast.makeText(getApplicationContext(), "Title or Content can not be empty.", Toast.LENGTH_SHORT).show();
                }else {
                    boolean insert = db.insert_question(titleValue, contentValue);
                    if (insert) {
                        Toast.makeText(getApplicationContext(), "Submit question successful!\nYou can view your question in \"View My Question\"!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "Submit question failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ask_question.this, Application_Support.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
    }
}
