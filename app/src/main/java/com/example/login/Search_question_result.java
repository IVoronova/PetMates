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

import java.util.ArrayList;

public class Search_question_result extends AppCompatActivity {

    ListView result1;
    TextView back;
    DatabaseHelper db;
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

        ArrayList<String> Result = new ArrayList<>();

        Cursor result = db.search_question(searchkey);
        while(result.moveToNext()){
            if(!result.getString(3).equals("0")){
                Result.add("\nQusetion Title:\n"+ result.getString(1)+"\n"+
                        "\nQuestion Content: \n"+result.getString(2)+"\n"+
                        "\nAnswer: \n"+ result.getString(3)+"\n");
            }else{
                Result.add("\nQusetion Title: \n"+ result.getString(1)+"\n"+
                        "\nQuestion Content: \n"+result.getString(2)+"\n"+
                        "\nAnswer: \nNo answer yet, we will answer it soon!\n");
            }
            ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,Result);
            result1.setAdapter(listAdapter);
        }



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
