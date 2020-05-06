package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Admin_view_question extends AppCompatActivity {

    EditText questionNumber,answer;
    TextView back;
    ListView allQuestion;
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

        ArrayList<String> list = new ArrayList<>();

        Cursor Question = db.getAll_question();
        if(Question.getCount() == 0){
            Toast.makeText(getApplicationContext(), "No more unanswered question!", Toast.LENGTH_SHORT).show();
        }
        while(Question.moveToNext()){
            list.add("\nQuestion number: \n"+Question.getString(0)+
                            "\nQuestion title: \n"+Question.getString(1)+
                            "\nQuestion content: \n"+Question.getString(2)+
                            "\n");
            ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
            allQuestion.setAdapter(listAdapter);
        }


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
