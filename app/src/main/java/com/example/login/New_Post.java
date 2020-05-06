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
    EditText Title, Description;
    String BackActivity, imagepath;
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

        Title = (EditText) findViewById(R.id.etNewPost_Title);
        Description = (EditText) findViewById(R.id.etNewPost_Description);
        Back = (TextView) findViewById(R.id.tvNewPost_Back);
        Post = (TextView) findViewById(R.id.tvNewPost_Post);
        Picture = (ImageButton) findViewById(R.id.btnNewPost_AddPic);

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
                    startActivity(intent);
                }
            }
        });

        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NewsTitle = Title.getText().toString();
                String NewsDescription = Description.getText().toString();

                if(NewsTitle.equals("") || NewsDescription.equals("") || imagepath.equals("")){
                    Toast.makeText(getApplicationContext(), "Some fields are empty, please answer all the questions", Toast.LENGTH_SHORT).show();
                }else{
                    boolean insert = db.insert_news(NewsTitle,NewsDescription,imagepath);
                    if(insert)
                        Toast.makeText(getApplicationContext(), "Upload post successful", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "Upload failed, please check permission settings on your device", Toast.LENGTH_SHORT).show();
                }

                if(BackActivity.equals("Pet_News")){
                    Intent intent = new Intent(New_Post.this, Pet_News.class);
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
