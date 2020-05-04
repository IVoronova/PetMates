package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Application_Support extends AppCompatActivity {

    Button question,suggestion,fq;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application__support);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");

        question = findViewById(R.id.btnask_question);
        suggestion = findViewById(R.id.suggest);
        fq = findViewById(R.id.FandQ);
        back = findViewById(R.id.btnBackA);

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Application_Support.this,Ask_question.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Application_Support.this,Suggestion.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        fq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Application_Support.this,FandQ.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Application_Support.this,Main_menu.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
    }
}
