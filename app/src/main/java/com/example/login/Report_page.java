package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Report_page extends AppCompatActivity {

    Spinner reason;
    EditText discription;
    Button report;
    TextView back;
    String reasonSelection = "";
    String descriptionValue = "";
    String email,reportedEmail,depend;
    DatabaseHelper db;
    boolean isSubmit;
    String condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        reportedEmail = intent.getStringExtra("reportedEmail");
        depend = intent.getStringExtra("depend");
        db = new DatabaseHelper(this);

        reason = findViewById(R.id.spinner);
        discription = findViewById(R.id.etreportDes);
        report = findViewById(R.id.btnreport_submit);
        back = findViewById(R.id.btnreport_back);

        //for spin section array
        final String selction = "Choose an report reason";
        final String reason1 = "Inappropriate profile/picture";
        final String reason2 = "reason two";
        final String reason3 = "reason three";
        final String reason4 = "reason four";

        List<String> reasonValue = new ArrayList<>();
        reasonValue.add(0, selction);
        reasonValue.add(reason1);
        reasonValue.add(reason2);
        reasonValue.add(reason3);
        reasonValue.add(reason4);

        //Style the and pop the spinner
        ArrayAdapter <String> Adapter;
        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,reasonValue);

        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        reason.setAdapter(Adapter);
        reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals(selction)) {
                    //do nothing
                }else{
                    //on seleting a spinner item
                    reasonSelection += parent.getItemAtPosition(position).toString();
                    //show selected spinner item
                    Toast.makeText(parent.getContext(),"Selected: " +reasonSelection, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descriptionValue = "\n"+discription.getText().toString();
                reasonSelection += descriptionValue;

                if(reasonSelection.equals("")){
                    Toast.makeText(getApplicationContext(), "Reason selection can not be empty.", Toast.LENGTH_SHORT).show();
                }else{
                    isSubmit = db.insert_report(reportedEmail,reasonSelection,email);
                    if(isSubmit){
                        Toast.makeText(getApplicationContext(), "Report submit successful.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Something wrong.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(condition == "1"){
                    Intent intent = new Intent(Report_page.this, Friend_request.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                } else if(depend.equals("P")){
                    Intent intent = new Intent(Report_page.this, Pairing_ByP_Result.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(Report_page.this, Pairing_ByL_Result.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            }
        });
    }
}
