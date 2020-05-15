package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.icu.lang.UCharacterEnums;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class report_detail extends AppCompatActivity {

    String title,id,reportedEmail,senderEmail,reportReason;
    Button block,unblock,checkchathistory,deleteReport;
    TextView back,name,bio,type,breed,gender,zip,Title,reason1;
    EditText reason;
    ImageView image;
    int ID;


    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        reportedEmail = intent.getStringExtra("reportedEmail");
        id = intent.getStringExtra("id");
        reportReason = intent.getStringExtra("reportReason");
        assert id != null;
        ID = Integer.parseInt(id);
        senderEmail = intent.getStringExtra("senderEmail");


        Title = findViewById(R.id.titleReportinfo);
        name = findViewById(R.id.reportedname);
        bio = findViewById(R.id.reportedbio);
        type = findViewById(R.id.reportedtype);
        breed = findViewById(R.id.reportedbreed);
        gender = findViewById(R.id.reportedgender);
        zip = findViewById(R.id.reportedzip);
        reason = findViewById(R.id.reasonv);
        block = findViewById(R.id.block);
        unblock = findViewById(R.id.unblock);
        checkchathistory = findViewById(R.id.checkchathistory);
        back = findViewById(R.id.btnBackreportt);
        image = findViewById(R.id.reportImageview);
        deleteReport = findViewById(R.id.deletereport);
        reason1 = findViewById(R.id.reasonshow);

        title = "Report ID: "+id;
        Title.setText(title);
        reason1.setText(reportReason);


        Cursor get_profile = db.getAll_User_info(reportedEmail);
        if (get_profile.getCount() > 0) {
            get_profile.moveToFirst();

            String NameValue = "Name: " + get_profile.getString(1);
            name.setText(NameValue);

            String BioValue = "Bio: " + get_profile.getString(2);
            bio.setText(BioValue);

            String PetTypeValue = "Pet Type: " + get_profile.getString(3);
            type.setText(PetTypeValue);

            String PetBreedValue = "Pet Breed: " + get_profile.getString(4);
            breed.setText(PetBreedValue);

            String PetGenderValue = "Pet Gender: " + get_profile.getString(5);
            gender.setText(PetGenderValue);

            String ZipValue = "Zip :" + get_profile.getString(6);
            zip.setText(ZipValue);

            image.setImageBitmap(db.getimage(reportedEmail));
        }



            block.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String reasonValue = reason.getText().toString();
                    if (reasonValue.equals("")) {
                        Toast.makeText(getApplicationContext(), "Reason can not be empty!", Toast.LENGTH_SHORT).show();
                    } else {
                        db.insert_block(reportedEmail, reasonValue);
                        Toast.makeText(getApplicationContext(), "Block user successful!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            unblock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if (db.isBlock(reportedEmail)) {
                        db.unblock(reportedEmail);
                        Toast.makeText(getApplicationContext(), "User unblocked!", Toast.LENGTH_SHORT).show();
                    //}else{
                       // Toast.makeText(getApplicationContext(), "That user is not been blocked!", Toast.LENGTH_SHORT).show();
                    }
              //  }
            });

            checkchathistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(report_detail.this, report_chat_history.class);
                    intent.putExtra("reportedEmail",reportedEmail);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
            });

            deleteReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(report_detail.this, Admin_view_report.class);
                    db.deleteReport(ID);
                    Toast.makeText(getApplicationContext(), "Report deleted!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(report_detail.this, Admin_view_report.class);
                    startActivity(intent);
                }
            });
        }
}
