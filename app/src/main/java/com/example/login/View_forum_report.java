package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class View_forum_report extends AppCompatActivity {


    TextView back;
    EditText num;
    Button delete;
    ListView allReport;
    DatabaseHelper db;
    int ID;
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> section = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> content = new ArrayList<>();
    ArrayList<Bitmap> image = new ArrayList<>();
    ArrayList<String> reason = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_forum_report);
        db = new DatabaseHelper(this);

        num = findViewById(R.id.enterNumber1);
        delete = findViewById(R.id.btndelete);
        back = findViewById(R.id.btnBackaaa);
        allReport = findViewById(R.id.etallPost1);

        Cursor data = db.getAll_reported_forum();
        if (data.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No report recored!", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()) {
                ID = data.getInt(1);
                id.add(data.getString(1));
                Cursor alldata = db.all_data_id(ID);
                alldata.moveToFirst();
                section.add(section(alldata.getInt(1)));
                title.add(alldata.getString(2));
                content.add("Content: "+ alldata.getString(3));
                image.add(db.getforumimage(ID));
                reason.add(data.getString(2));
            }
        }

        forum_report_adapter myAdapter = new forum_report_adapter(this,id,section,title,content,image,reason);
        allReport.setAdapter(myAdapter);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postNumber = Integer.parseInt(num.getText().toString());
                if (postNumber != 0) {
                    db.delete_post(postNumber);
                    Toast.makeText(getApplicationContext(), "Delete successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(View_forum_report.this, View_forum_report.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Post number can not be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(View_forum_report.this, Admin_page.class);
                startActivity(intent);
            }
        });
    }
    public String section(int id){
        if(id == 1) return "Pet News";
        else if(id == 2) return "Galleries";
        else if(id == 3) return "Lost and Found";
        else if(id == 4) return "Pet Jobs";
        else return "Report";
        }
 }


