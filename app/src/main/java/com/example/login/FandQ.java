package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FandQ extends AppCompatActivity {

    EditText search_key;
    Button search,q1,q2,q3;
    TextView back;
    String email,question1,question2,question3,answer1,answer2,answer3;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fand_q);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        db = new DatabaseHelper(this);

        search_key = findViewById(R.id.etquestionTitle);
        search = findViewById(R.id.etsearchButton);
        q1 = findViewById(R.id.btnq1);
        q2 = findViewById(R.id.btnq2);
        q3 = findViewById(R.id.btnq3);
        back = findViewById(R.id.btnFQ_back);

        question1 = "How can I send friend request to others?";
        answer1 = "Go to Main menu --> Pairing  --> choose pair by preferences or location --> send friend request";
        question2 = "What happened if no one satisfied my preferences when paring?";
        answer2 = "Don't worry! We will find a random mate for you!";
        question3 = "How can I chat with my friend?";
        answer3 = "Go to Main menu --> friend --> click one of your friend --> start chat";

        q1.setText(question1);
        q2.setText(question2);
        q3.setText(question3);

        q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FandQ.this, Sample_question.class);
                intent.putExtra("email", email);
                intent.putExtra("question", question1);
                intent.putExtra("answer", answer1);
                startActivity(intent);
            }
        });
        q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FandQ.this, Sample_question.class);
                intent.putExtra("email", email);
                intent.putExtra("question", question2);
                intent.putExtra("answer", answer2);
                startActivity(intent);
            }
        });
        q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FandQ.this, Sample_question.class);
                intent.putExtra("email", email);
                intent.putExtra("question", question3);
                intent.putExtra("answer", answer3);
                startActivity(intent);
            }
        });



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search_keyValue = search_key.getText().toString();
                if(search_keyValue.equals("")){
                    Toast.makeText(getApplicationContext(), "Search key box can be empty", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor result = db.search_question(search_keyValue);
                    if (result.getCount() > 0) {
                        Intent intent = new Intent(FandQ.this, Search_question_result.class);
                        intent.putExtra("search_key", search_keyValue);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    } else
                        Toast.makeText(getApplicationContext(), "No result!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FandQ.this, Application_Support.class);
                intent.putExtra("email", email);
                startActivity(intent);

            }
        });

    }
}
