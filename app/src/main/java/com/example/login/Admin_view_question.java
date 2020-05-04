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

public class Admin_view_question extends AppCompatActivity {

    EditText questionNumber,answer;
    TextView allQuestion,back;
    Button submit;
    DatabaseHelper db;
    String showQuestion ="";
    int qNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_question);

        db = new DatabaseHelper(this);
        questionNumber = findViewById(R.id.enterNumber);
        answer = findViewById(R.id.answerQuestion);
        submit = findViewById(R.id.btnProfile);
        allQuestion = findViewById(R.id.etallQuestion);
        back = findViewById(R.id.btnBackadQ);

        Cursor Question = db.getAll_question();
        while(Question.moveToNext()){
            showQuestion += "Question number: "+Question.getString(0)+
                            "\nQuestion title: "+Question.getString(1)+
                            "\nQuestion content: "+Question.getString(2)+
                            "\n\n\n";
        }

        allQuestion.setText(showQuestion);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qNumber += Integer.parseInt(questionNumber.getText().toString());
                String answerValue = answer.getText().toString();
                if(answerValue.equals("")||qNumber==0){
                    Toast.makeText(getApplicationContext(),"Answer can not be empty!",Toast.LENGTH_SHORT).show();
                }else{
                    db.answer_question(qNumber,answerValue);
                    Toast.makeText(getApplicationContext(),"Answer successful!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Admin_view_question.this, Admin_view_question.class);
                    startActivity(intent);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_view_question.this, Admin_page.class);
                startActivity(intent);
            }
        });
    }
}
