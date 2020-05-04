package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Search_question_result extends AppCompatActivity {

    TextView result1,back;
    DatabaseHelper db;
    String resultValue;
    String finalValue="";
    String email,searchkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_question_result);

        db = new DatabaseHelper(this);

        //get email value from last activity
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        searchkey = intent.getStringExtra("search_key");

        result1 = findViewById(R.id.etsearchResult1);
        back = findViewById(R.id.btnBackRS);

        Cursor result = db.search_question(searchkey);
        while(result.moveToNext()){
            resultValue = "Qusetion Title: "+ result.getString(1)+"\n"+
                    "\nQuestion Content: "+result.getString(2)+"\n"+
                    "\nAnswer: ";
            if(!result.getString(3).equals("0")){
                finalValue += resultValue+result.getString(3)+"\n\n\n";
            }else{
                finalValue += resultValue + "No answer yet, we will answer it soon!\n\n\n";
            }

        }

        result1.setText(finalValue);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search_question_result.this, FandQ.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });



    }
}
