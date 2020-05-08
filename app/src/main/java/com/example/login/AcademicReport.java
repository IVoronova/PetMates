package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AcademicReport extends AppCompatActivity {
    TextView Report1, Report2, Report3, Report4, Back, Next, Post;
    String email, BackRead = "AcademicReport";
    Cursor cursor;
    DatabaseHelper db;
    int art1num , art2num, art3num, art4num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academicreport);

        db = new DatabaseHelper(this);
        cursor = db.all_data(5);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        Report1 = findViewById(R.id.tvAcademic_Report1);
        Report2 = findViewById(R.id.tvAcademic_Report2);
        Report3 = findViewById(R.id.tvAcademic_Report3);
        Report4 = findViewById(R.id.tvAcademic_Report4);
        Back = findViewById(R.id.tvReport_Back);
        Next = findViewById(R.id.tvReport_Next);
        Post = findViewById(R.id.tvReport_Post);

        update();
        refresh();

        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcademicReport.this, New_Post.class);
                intent.putExtra("email", email);
                intent.putExtra("BackActivity", BackRead);
                startActivity(intent);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcademicReport.this, Forum.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
                refresh();
            }
        });

    }

    public void update(){
        if (cursor.moveToNext()) {
            Report1.setText(cursor.getString(2));
            art1num = cursor.getInt(0);
        }
        if (cursor.moveToNext()) {
            Report2.setText(cursor.getString(2));
            art2num = cursor.getInt(0);
        }
        if (cursor.moveToNext()) {
            Report3.setText(cursor.getString(2));
            art3num = cursor.getInt(0);
        }
        if (cursor.moveToNext()) {
            Report4.setText(cursor.getString(2));
            art4num = cursor.getInt(0);
        }
    }

    public void refresh(){
        Report1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcademicReport.this, Read_ForumData.class);
                intent.putExtra("email", email);
                intent.putExtra("artnum", art1num);
                intent.putExtra("BackRead", BackRead);
                startActivity(intent);
            }
        });

        Report2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcademicReport.this, Read_ForumData.class);
                intent.putExtra("email", email);
                intent.putExtra("artnum", art2num);
                intent.putExtra("BackRead", BackRead);
                startActivity(intent);
            }
        });

        Report3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcademicReport.this, Read_ForumData.class);
                intent.putExtra("email", email);
                intent.putExtra("artnum", art3num);
                intent.putExtra("BackRead", BackRead);
                startActivity(intent);
            }
        });

        Report4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcademicReport.this, Read_ForumData.class);
                intent.putExtra("email", email);
                intent.putExtra("artnum", art4num);
                intent.putExtra("BackRead", BackRead);
                startActivity(intent);
            }
        });
    }
}
