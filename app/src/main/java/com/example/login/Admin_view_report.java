package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Admin_view_report extends AppCompatActivity {

    TextView back;
    ListView allReport;
    DatabaseHelper db;

    ArrayList<String> Id = new ArrayList<>();
    ArrayList<Bitmap> senderImage = new ArrayList<>();
    ArrayList<Bitmap> reportImage = new ArrayList<>();
    ArrayList<String> senderEmail = new ArrayList<>();
    ArrayList<String> reportedEmail = new ArrayList<>();
    ArrayList<String> reportReason = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_report);

        db = new DatabaseHelper(this);

        allReport = findViewById(R.id.etallQuestion);
        back = findViewById(R.id.btnBackadR);

        Cursor report = db.getReported_information();
        if(report.getCount()==0){
            Toast.makeText(getApplicationContext(), "No report!", Toast.LENGTH_SHORT).show();
        }else {
            while (report.moveToNext()) {

                String emails = report.getString(3);
                String emailr = report.getString(1);
                Bitmap ImagepathS = db.getimage(emails);
                Bitmap ImagepathR = db.getimage(emailr);

                Id.add(report.getString(0));
                senderImage.add(ImagepathS);
                reportImage.add(ImagepathR);
                senderEmail.add(emails);
                reportedEmail.add(emailr);
                reportReason.add(report.getString(2));
            }
        }
        Admin_report_adapter myAdapter = new Admin_report_adapter(this,Id,senderImage,reportImage,senderEmail,reportedEmail,reportReason);
        allReport.setAdapter(myAdapter);
        allReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Admin_view_report.this, report_detail.class);
                intent.putExtra("reportedEmail",reportedEmail.get(position));
                intent.putExtra("id",Id.get(position));
                intent.putExtra("senderEmail",senderEmail.get(position));

                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_view_report.this, Admin_page.class);
                startActivity(intent);
            }
        });
    }
}



