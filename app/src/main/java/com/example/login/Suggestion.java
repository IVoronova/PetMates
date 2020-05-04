package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Suggestion extends AppCompatActivity {

    EditText title,content;
    Button submit;
    TextView back;
    String email;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        title = findViewById(R.id.etsuggestiontitle);
        content = findViewById(R.id.etsuggestionContent);
        submit = findViewById(R.id.btnSuggest_save);
        back = findViewById(R.id.btnSuggest_back);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleValue = title.getText().toString();
                String contentValue = content.getText().toString();
                if(titleValue.equals("")||contentValue.equals("")){
                    Toast.makeText(getApplicationContext(), "Title or Content can not be empty.", Toast.LENGTH_SHORT).show();
                }else {
                    boolean insert = db.insert_suggestion(titleValue, contentValue);
                    if (insert == true) {
                        Toast.makeText(getApplicationContext(), "Thank you for your valuable suggestions", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "Submit suggestion failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Suggestion.this, Application_Support.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
    }
}
