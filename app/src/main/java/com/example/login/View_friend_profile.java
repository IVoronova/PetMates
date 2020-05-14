package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class View_friend_profile extends AppCompatActivity {

    TextView title,back,Name,Bio,PetType,PetBreed,PetGender,Zip;
    Button report,delete;
    String email,friendEmail,friendName,friendBio,NameValue,BioValue,PetTypeValue,PetBreedValue,PetGenderValue,ZipValue,titleValue;
    DatabaseHelper db;
    ImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friend_profile);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        friendEmail = intent.getStringExtra("friendEmail");
        friendName = intent.getStringExtra("friendName");
        friendBio = intent.getStringExtra("friendBio");

        userImage = findViewById(R.id.user_imageViewrequest1);

        Name = findViewById(R.id.etNameViewrequest1);
        Bio = findViewById(R.id.etBioViewrequest1);
        PetType = findViewById(R.id.etPet_typeViewrequest1);
        PetBreed = findViewById(R.id.etPet_breedViewrequest1);
        PetGender = findViewById(R.id.etPet_genderViewrequest1);
        Zip = findViewById(R.id.etzipViewrequest1);

        title = findViewById(R.id.friendsrequest1);
        back = findViewById(R.id.btnBackrequest1);
        report = findViewById(R.id.reportuser1);
        delete = findViewById(R.id.btndeletefriend1);

        Cursor get_profile = db.getAll_User_info(friendEmail);
        get_profile.moveToFirst();
        titleValue = get_profile.getString(1)+"\' Profile";
        title.setText(titleValue);

        NameValue = "Name: "+ get_profile.getString(1);
        Name.setText(NameValue);

        BioValue = "Bio: " +get_profile.getString(2);
        Bio.setText(BioValue);

        PetTypeValue ="Pet Type: "+ get_profile.getString(3);
        PetType.setText(PetTypeValue);

        PetBreedValue = "Pet Breed: "+get_profile.getString(4);
        PetBreed.setText(PetBreedValue);

        PetGenderValue = "Pet Gender: "+get_profile.getString(5);
        PetGender.setText(PetGenderValue);

        ZipValue = "Zip :"+get_profile.getString(6);
        Zip.setText(ZipValue);

        userImage.setImageBitmap(db.getimage(friendEmail));

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(View_friend_profile.this, message_report.class);
                intent.putExtra("email",email);
                intent.putExtra("friendEmail",friendEmail);
                intent.putExtra("friendName",friendName);
                intent.putExtra("friendBio",friendBio);
                startActivity(intent);
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(View_friend_profile.this, confirm_deletefriend.class);
                intent.putExtra("email",email);
                intent.putExtra("friendEmail",friendEmail);
                intent.putExtra("friendName",friendName);
                intent.putExtra("friendBio",friendBio);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(View_friend_profile.this, Message.class);
                intent.putExtra("email",email);
                intent.putExtra("friendEmail",friendEmail);
                intent.putExtra("friendName",friendName);
                intent.putExtra("friendBio",friendBio);
                startActivity(intent);
            }
        });

    }
}
