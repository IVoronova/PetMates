package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Read_ForumData extends AppCompatActivity {
    TextView Title, Description, Contact, Back;
    ImageView Picture;
    DatabaseHelper db;
    String email, BackRead;
    Bitmap bt = null;
    Cursor cursor;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_form);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        index = intent.getIntExtra("artnum", index);
        BackRead = intent.getStringExtra("BackRead");

        Title = findViewById(R.id.tvReadPetNews_title);
        Description = findViewById(R.id.tvReadPetNews_Description);
        Back = findViewById(R.id.tvReadPetNews_Back);
        Contact = findViewById(R.id.tvReadForum_contact);
        Picture = findViewById(R.id.ReadNews_Pic);

        if(BackRead.equals("Pet_News"))
            cursor = db.newsarticle_data(index);
        else if(BackRead.equals("Lost_and_Found")) {
            cursor = db.lostandfound_data(index);
            Title.setText("No Lost and Found Report");
        }
        else if(BackRead.equals("AcademicReport"))
            cursor = db.academcreport_data(index);

        if(cursor.moveToNext()) {
            byte[] Image = cursor.getBlob(4);
            bt = BitmapFactory.decodeByteArray(Image,0,Image.length);
            Title.setText(cursor.getString(2));
            Description.setText(cursor.getString(3));
            Picture.setImageBitmap(bt);
            if(BackRead.equals("Lost_and_Found")) {
                Contact.setText("Contact: " + cursor.getString(5));
            }
        }


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Read_ForumData.this, Pet_News.class);
                if(BackRead.equals("Lost_and_Found"))
                    intent = new Intent(Read_ForumData.this, Lost_and_Found.class);
                else if (BackRead.equals("AcademicReport"))
                    intent = new Intent(Read_ForumData.this, AcademicReport.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

    }
}
