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
    String email;
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
        q1 = findViewById(R.id.btnQ1);
        q2 = findViewById(R.id.btnq2);
        q3 = findViewById(R.id.btnq3);
        back = findViewById(R.id.btnFQ_back);

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
