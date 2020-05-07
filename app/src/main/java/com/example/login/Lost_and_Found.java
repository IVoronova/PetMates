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

public class Lost_and_Found extends AppCompatActivity {
    TextView Post1, Post2, Post3, Post4, Back, Next, Post;
    ImageView Pic1, Pic2, Pic3, Pic4;
    DatabaseHelper db;
    Cursor cursor;
    String email, BackRead = "Lost_and_Found";
    Bitmap bt = null;
    int art1num , art2num, art3num, art4num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and__found);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        cursor = db.all_data(3);

        Post1 = findViewById(R.id.tvLost_Report1);
        Post2 = findViewById(R.id.tvLost_Report2);
        Post3 = findViewById(R.id.tvLost_Report3);
        Post4 = findViewById(R.id.tvLost_Report4);
        Back = findViewById(R.id.tvLostnFound_Back);
        Next = findViewById(R.id.tvLostnFound_Next);
        Post = findViewById(R.id.tvLostnFound_Post);
        Pic1 = findViewById(R.id.Lost_Pic1);
        Pic2 = findViewById(R.id.Lost_Pic2);
        Pic3 = findViewById(R.id.Lost_Pic3);
        Pic4 = findViewById(R.id.Lost_Pic4);

        update();
        refresh();

        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lost_and_Found.this, New_Post.class);
                intent.putExtra("email", email);
                intent.putExtra("BackActivity", "Lost_and_Found");
                startActivity(intent);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lost_and_Found.this, Forum.class);
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
            byte[] Image = cursor.getBlob(4);
            bt = BitmapFactory.decodeByteArray(Image,0,Image.length);
            Post1.setText(cursor.getString(2));
            art1num = cursor.getInt(0);
            Pic1.setImageBitmap(bt);
        }
        if (cursor.moveToNext()) {
            byte[] Image = cursor.getBlob(4);
            bt = BitmapFactory.decodeByteArray(Image,0,Image.length);
            Post2.setText(cursor.getString(2));
            art2num = cursor.getInt(0);
            Pic2.setImageBitmap(bt);
        }
        if (cursor.moveToNext()) {
            byte[] Image = cursor.getBlob(4);
            bt = BitmapFactory.decodeByteArray(Image,0,Image.length);
            Post3.setText(cursor.getString(2));
            art3num = cursor.getInt(0);
            Pic3.setImageBitmap(bt);
        }
        if (cursor.moveToNext()) {
            byte[] Image = cursor.getBlob(4);
            bt = BitmapFactory.decodeByteArray(Image,0,Image.length);
            Post4.setText(cursor.getString(2));
            art4num = cursor.getInt(0);
            Pic4.setImageBitmap(bt);
        }
    }

    public void refresh(){
        Post1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lost_and_Found.this, Read_ForumData.class);
                intent.putExtra("email", email);
                intent.putExtra("artnum", art1num);
                intent.putExtra("BackRead", BackRead);
                startActivity(intent);
            }
        });

        Post2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lost_and_Found.this, Read_ForumData.class);
                intent.putExtra("email", email);
                intent.putExtra("artnum", art2num);
                intent.putExtra("BackRead", BackRead);
                startActivity(intent);
            }
        });

        Post3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lost_and_Found.this, Read_ForumData.class);
                intent.putExtra("email", email);
                intent.putExtra("artnum", art3num);
                intent.putExtra("BackRead", BackRead);
                startActivity(intent);
            }
        });

        Post4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lost_and_Found.this, Read_ForumData.class);
                intent.putExtra("email", email);
                intent.putExtra("artnum", art4num);
                intent.putExtra("BackRead", BackRead);
                startActivity(intent);
            }
        });
    }


}
