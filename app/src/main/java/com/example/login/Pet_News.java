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

public class Pet_News extends AppCompatActivity {
    TextView Back, Next, New, Article1, Article2, Article3, Article4;
    String BackRead = "Pet_News",email ;
    ImageView Pic1, Pic2, Pic3, Pic4;
    DatabaseHelper db;
    Bitmap bt = null;
    int art1num , art2num, art3num, art4num;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_news);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        Back = (TextView) findViewById(R.id.tvPetNews_Back);
        Next = (TextView) findViewById(R.id.tvPetNews_Next);
        New = (TextView) findViewById(R.id.tvPetNews_Post);
        Article1 = findViewById(R.id.tvPetNews_Article1);
        Article2 = findViewById(R.id.tvPetNews_Article2);
        Article3 = findViewById(R.id.tvPetNews_Article3);
        Article4 = findViewById(R.id.tvPetNews_Article4);
        Pic1 = findViewById(R.id.imageView10);
        Pic2 = findViewById(R.id.imageView7);
        Pic3 = findViewById(R.id.imageView9);
        Pic4 = findViewById(R.id.imageView11);

        cursor = db.all_data(1);
        update();
        refresh();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pet_News.this, Forum.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pet_News.this, New_Post.class);
                intent.putExtra("BackActivity", BackRead);
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
            Article1.setText(cursor.getString(2));
            art1num = cursor.getInt(0);
            Pic1.setImageBitmap(bt);
        }
        if (cursor.moveToNext()) {
            byte[] Image = cursor.getBlob(4);
            bt = BitmapFactory.decodeByteArray(Image,0,Image.length);
            Article2.setText(cursor.getString(2));
            art2num = cursor.getInt(0);
            Pic2.setImageBitmap(bt);
        }
        if (cursor.moveToNext()) {
            byte[] Image = cursor.getBlob(4);
            bt = BitmapFactory.decodeByteArray(Image,0,Image.length);
            Article3.setText(cursor.getString(2));
            art3num = cursor.getInt(0);
            Pic3.setImageBitmap(bt);
        }
        if (cursor.moveToNext()) {
            byte[] Image = cursor.getBlob(4);
            bt = BitmapFactory.decodeByteArray(Image,0,Image.length);
            Article4.setText(cursor.getString(2));
            art4num = cursor.getInt(0);
            Pic4.setImageBitmap(bt);
        }
    }

    public void refresh(){
        Article1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pet_News.this, Read_ForumData.class);
                intent.putExtra("email", email);
                intent.putExtra("artnum", art1num);
                intent.putExtra("BackRead", BackRead);
                startActivity(intent);
            }
        });

        Article2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pet_News.this, Read_ForumData.class);
                intent.putExtra("email", email);
                intent.putExtra("artnum", art2num);
                intent.putExtra("BackRead", BackRead);
                startActivity(intent);
            }
        });

        Article3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pet_News.this, Read_ForumData.class);
                intent.putExtra("email", email);
                intent.putExtra("artnum", art3num);
                intent.putExtra("BackRead", BackRead);
                startActivity(intent);
            }
        });

        Article4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pet_News.this, Read_ForumData.class);
                intent.putExtra("email", email);
                intent.putExtra("artnum", art4num);
                intent.putExtra("BackRead", BackRead);
                startActivity(intent);
            }
        });
    }
}
