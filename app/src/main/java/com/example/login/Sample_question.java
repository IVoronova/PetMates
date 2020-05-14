package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Sample_question extends AppCompatActivity {

    String email,question, answer;
    TextView title,answer1,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_question);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        question = intent.getStringExtra("question");
        answer = intent.getStringExtra("answer");

        answer1 = findViewById(R.id.etquestionContent1);
        title = findViewById(R.id.samplequestiontitle);
        back = findViewById(R.id.btnquestion_back1);

        answer1.setText(answer);
        title.setText(question);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sample_question.this, FandQ.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }
}
