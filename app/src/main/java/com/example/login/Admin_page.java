package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Admin_page extends AppCompatActivity {

    DatabaseHelper db;
    Button view_question,view_suggestion,view_report;
    TextView logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        db = new DatabaseHelper(this);
        view_question = findViewById(R.id.viewQuestion1);
        view_suggestion = findViewById(R.id.Viewsuggest);
        view_report = findViewById(R.id.viewreport);
        logout = findViewById(R.id.adlogout);

        view_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_page.this, Admin_view_question.class);
                startActivity(intent);
            }
        });

        view_suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_page.this, Admin_view_suggestion.class);
                startActivity(intent);
            }
        });

        view_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_page.this, Admin_view_report.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_page.this, Login.class);
                startActivity(intent);
            }
        });
    }



}
