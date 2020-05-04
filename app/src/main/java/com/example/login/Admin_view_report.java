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

public class Admin_view_report extends AppCompatActivity {

    EditText email,reason;
    TextView allReport,back;
    Button submit;
    DatabaseHelper db;
    String showReport ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_report);

        db = new DatabaseHelper(this);

        email = findViewById(R.id.enterEmail);
        reason = findViewById(R.id.answerReason);
        submit = findViewById(R.id.btnsubmitb);
        allReport = findViewById(R.id.etallQuestion);
        back = findViewById(R.id.btnBackadR);

        Cursor report = db.getReported_information();
        while(report.moveToNext()){
            showReport += "Report number: "+report.getString(0)+
                    "\nuser received report: "+report.getString(1)+
                    "\nReason: "+report.getString(2)+
                    "\nuser send report"+report.getString(3)+
                    "\n\n\n";
        }

        allReport.setText(showReport);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailValue = email.getText().toString();
                String reasonValue = reason.getText().toString();
                if(emailValue.equals("")||reasonValue.equals("")){
                    Toast.makeText(getApplicationContext(),"Email or reason can not be empty!",Toast.LENGTH_SHORT).show();
                }else{
                    db.insert_block(emailValue,reasonValue);
                    Toast.makeText(getApplicationContext(),"Block successful!",Toast.LENGTH_SHORT).show();
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

