package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Inet4Address;

public class PetJobs_Post extends AppCompatActivity {
    EditText Contact, Payment, Description;
    TextView Back, Post;
    DatabaseHelper db;
    String email, activity;
    Cursor cursor;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_jobs__post);
        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        index = intent.getIntExtra("index", index);
        activity = intent.getStringExtra("activity");

        cursor = db.petjobs_data(index);

        Contact = findViewById(R.id.etJobPost_Contact);
        Payment = findViewById(R.id.etJobPost_Payment);
        Description = findViewById(R.id.etJobPost_Description);
        Back = findViewById(R.id.tvJobPost_Back);
        Post = findViewById(R.id.tvJobPost_Post);

        if(activity.equals("view")){
            if(cursor.moveToNext()){
                Contact.setText(cursor.getString(5));
                Payment.setText(cursor.getString(2));
                Description.setText(cursor.getString(3));
            }
            Post.setText("");
        }

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetJobs_Post.this, Pet_Jobs.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        if(activity.equals("post")) {
            Post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String contact = Contact.getText().toString();
                    String description = Description.getText().toString();
                    String payment = Payment.getText().toString();

                    if (contact.equals("") || description.equals("") || payment.equals("")) {
                        Toast.makeText(getApplicationContext(), "Some fields are empty, please answer all the questions", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean insert = db.insert_jobdata(contact, payment, description);
                        if (insert)
                            Toast.makeText(getApplicationContext(), "Upload post successful", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Upload failed, please check permission settings on your device", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(PetJobs_Post.this, Pet_Jobs.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            });
        }

    }
}
