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

public class Admin_view_report extends AppCompatActivity {

    EditText email,reason;
    TextView back;
    ListView allReport;
    Button block,unblock;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_report);

        db = new DatabaseHelper(this);

        email = findViewById(R.id.enterEmail);
        reason = findViewById(R.id.answerReason);
        block = findViewById(R.id.btnsubmitb);
        allReport = findViewById(R.id.etallQuestion);
        back = findViewById(R.id.btnBackadR);
        unblock = findViewById(R.id.btunclock);

        ArrayList<String>list = new ArrayList<>();

        Cursor report = db.getReported_information();
        if(report.getCount()==0){
            Toast.makeText(getApplicationContext(), "No report!", Toast.LENGTH_SHORT).show();
        }else {
            while (report.moveToNext()) {
                list.add("Report number: \n" + report.getString(0) +
                        "\nuser received report: \n" + report.getString(1) +
                        "\nreport user: \n" + report.getString(3) +
                        "\nreason\n" + report.getString(2) +
                        "\n");
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
                allReport.setAdapter(listAdapter);
            }
        }


        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailValue = email.getText().toString();
                String reasonValue = reason.getText().toString();
                if(emailValue.equals("")||reasonValue.equals("")){
                    Toast.makeText(getApplicationContext(),"Email or reason can not be empty!",Toast.LENGTH_SHORT).show();
                }else{
                    db.insert_block(emailValue,reasonValue);
                    Toast.makeText(getApplicationContext(),"Block user successful!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Admin_view_report.this, Admin_view_report.class);
                    startActivity(intent);
                }
            }
        });

        unblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailValue = email.getText().toString();
                if (emailValue.equals("")) {
                    Toast.makeText(getApplicationContext(), "Email can not be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    db.unblock(emailValue);
                    Toast.makeText(getApplicationContext(), "User unblocked!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Admin_view_report.this, Admin_view_report.class);
                    startActivity(intent);
                }
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



