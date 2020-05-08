package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class New_Post extends AppCompatActivity {
    TextView Back, Post;
    EditText Title, Description, Contact;
    String BackActivity, imagepath, email;
    ImageButton Picture;
    DatabaseHelper db;

    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        db = new DatabaseHelper(this);

        //gets the string of the last activity directed
        Intent intent = getIntent();
        BackActivity = intent.getStringExtra("BackActivity");
        email = intent.getStringExtra("email");

        Title = (EditText) findViewById(R.id.etNewPost_Title);
        Description = (EditText) findViewById(R.id.etNewPost_Description);
        Contact = findViewById(R.id.etNewPost_contact);
        Back = (TextView) findViewById(R.id.tvNewPost_Back);
        Post = (TextView) findViewById(R.id.tvNewPost_Post);
        Picture = (ImageButton) findViewById(R.id.btnNewPost_AddPic);

        Contact.setVisibility(View.INVISIBLE);
        if(BackActivity.equals("Galleries")){
            Title.setHint("Name");
            Description.setHint("No Description Needed");
        }
        if(BackActivity.equals("Lost_and_Found")){
            Title.setHint("Name");
            Description.setHint("Please provide the following information: breed, location, age, " +
                    "distinct traits, and reward");
            Contact.setVisibility(View.VISIBLE);
        }

        Picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse(
                        "content://media/internal/images/media"));
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goes to which activity last directed
                if(BackActivity.equals("Pet_News")){
                    Intent intent = new Intent(New_Post.this, Pet_News.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                } else if(BackActivity.equals("Galleries")){
                    Intent intent = new Intent(New_Post.this, Galleries.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                } else if(BackActivity.equals("Lost_and_Found")){
                    Intent intent = new Intent(New_Post.this, Lost_and_Found.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                } else if(BackActivity.equals("AcademicReport")){
                    Intent intent = new Intent(New_Post.this, AcademicReport.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            }
        });

        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = Title.getText().toString();
                String newDescription = Description.getText().toString();

                if(BackActivity.equals("Pet_News")){
                    if(newTitle.equals("") || newDescription.equals("") || imagepath.equals("")){
                        Toast.makeText(getApplicationContext(), "Some fields are empty, please answer all the questions", Toast.LENGTH_SHORT).show();
                    }else{
                        boolean insert = db.insert_news(newTitle,newDescription,imagepath);
                        if(insert)
                            Toast.makeText(getApplicationContext(), "Upload post successful", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Upload failed, please check permission settings on your device", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(New_Post.this, Pet_News.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }else if(BackActivity.equals("Galleries")){
                    if(newTitle.equals("") || imagepath.equals(""))
                        Toast.makeText(getApplicationContext(), "Some fields are empty, please answer all the questions", Toast.LENGTH_SHORT).show();
                    else{
                        boolean insert = db.insert_galleries(newTitle, imagepath);
                        if(insert)
                            Toast.makeText(getApplicationContext(), "Upload post successful", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Upload failed, please check permission settings on your device", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(New_Post.this, Galleries.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }else if(BackActivity.equals("Lost_and_Found")){
                    String contact = Contact.getText().toString();
                    if(newTitle.equals("") || newDescription.equals("") || imagepath.equals("") || contact.equals("")){
                        Toast.makeText(getApplicationContext(), "Some fields are empty, please answer all the questions", Toast.LENGTH_SHORT).show();
                    }else{
                        boolean insert = db.insert_lostandfounddata(newTitle,newDescription,imagepath, contact);
                        if(insert)
                            Toast.makeText(getApplicationContext(), "Upload post successful", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Upload failed, please check permission settings on your device", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(New_Post.this, Lost_and_Found.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                } else if(BackActivity.equals("AcademicReport")){
                    if(newTitle.equals("") || newDescription.equals("") || imagepath.equals("")){
                        Toast.makeText(getApplicationContext(), "Some fields are empty, please answer all the questions", Toast.LENGTH_SHORT).show();
                    }else{
                        boolean insert = db.insert_academicreportdata(newTitle,newDescription,imagepath);
                        if(insert)
                            Toast.makeText(getApplicationContext(), "Upload post successful", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Upload failed, please check permission settings on your device", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(New_Post.this, AcademicReport.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, requestCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri uri = data.getData();
            imagepath = getPath(uri);
        }
    }

    public String getPath(Uri uri){
        if(uri==null)return null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri,projection,null,null,null);
        if(cursor!=null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }
}
