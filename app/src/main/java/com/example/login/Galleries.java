package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Galleries extends AppCompatActivity {
    ImageView Pic1, Pic2, Pic3, Pic4;
    TextView Name1, Name2, Name3, Name4, Back, Next, Post;
    String email;
    DatabaseHelper db;
    Cursor cursor;
    Bitmap bt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galleries);

        db = new DatabaseHelper(this);
        cursor = db.all_data(2);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        Pic1 = findViewById(R.id.Galleries_Pic1);
        Pic2 = findViewById(R.id.Galleries_Pic2);
        Pic3 = findViewById(R.id.Galleries_Pic3);
        Pic4 = findViewById(R.id.Galleries_Pic4);
        Name1 = findViewById(R.id.tv_Galleries_name1);
        Name2 = findViewById(R.id.tv_Galleries_name2);
        Name3 = findViewById(R.id.tv_Galleries_name3);
        Name4 = findViewById(R.id.tv_Galleries_name4);
        Back = findViewById(R.id.tvGalleries_Back);
        Next = findViewById(R.id.tvGalleries_Next);
        Post = findViewById(R.id.tvGalleries_Post);

        update();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Galleries.this, Forum.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Galleries.this, New_Post.class);
                intent.putExtra("email", email);
                intent.putExtra("BackActivity", "Galleries");
                startActivity(intent);
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

    }

    public void update(){
        if (cursor.moveToNext()) {
            byte[] Image = cursor.getBlob(4);
            bt = BitmapFactory.decodeByteArray(Image,0,Image.length);
            Name1.setText(cursor.getString(2));
            Pic1.setImageBitmap(bt);
        }
        if (cursor.moveToNext()) {
            byte[] Image = cursor.getBlob(4);
            bt = BitmapFactory.decodeByteArray(Image,0,Image.length);
            Name2.setText(cursor.getString(2));
            Pic2.setImageBitmap(bt);
        }
        if (cursor.moveToNext()) {
            byte[] Image = cursor.getBlob(4);
            bt = BitmapFactory.decodeByteArray(Image,0,Image.length);
            Name3.setText(cursor.getString(2));
            Pic3.setImageBitmap(bt);
        }
        if (cursor.moveToNext()) {
            byte[] Image = cursor.getBlob(4);
            bt = BitmapFactory.decodeByteArray(Image,0,Image.length);
            Name4.setText(cursor.getString(2));
            Pic4.setImageBitmap(bt);
        }
    }
}
