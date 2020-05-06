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

public class Accept_friend_request extends AppCompatActivity {

    TextView title,back,Name,Bio,PetType,PetBreed,PetGender,Zip;
    Button accept,decline,report;
    String email,requsetedemail,NameValue,BioValue,PetTypeValue,PetBreedValue,PetGenderValue,ZipValue,titleValue;
    String depend = null;
    DatabaseHelper db;
    ImageView userImage;

    String condition = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_friend_request);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        requsetedemail = intent.getStringExtra("friendEmail");

        userImage = findViewById(R.id.user_imageViewrequest);

        Name = findViewById(R.id.etNameViewrequest);
        Bio = findViewById(R.id.etBioViewrequest);
        PetType = findViewById(R.id.etPet_typeViewrequest);
        PetBreed = findViewById(R.id.etPet_breedViewrequest);
        PetGender = findViewById(R.id.etPet_genderViewrequest);
        Zip = findViewById(R.id.etzipViewrequest);

        title = findViewById(R.id.friendsrequest);
        back = findViewById(R.id.btnBackrequest);
        accept = findViewById(R.id.btnaccept);
        decline = findViewById(R.id.btndeclind);
        report = findViewById(R.id.btnreport_request);

        Cursor get_profile = db.getAll_User_info(requsetedemail);
        get_profile.moveToFirst();
        titleValue = get_profile.getString(1)+"\' User Create_Profile";
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

        userImage.setImageBitmap(db.getimage(requsetedemail));

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accept_friend_request.this, Report_page.class);
                intent.putExtra("email", email);
                intent.putExtra("reportedEmail", requsetedemail);
                intent.putExtra("depend", depend);
                intent.putExtra("condition", condition);
                startActivity(intent);
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean add1 = db.add_friend(email, requsetedemail);
                boolean add2 = db.add_friend_inverse(email, requsetedemail);
                if (add1 && add2) {
                    db.deleterequest(email,requsetedemail);
                    Toast.makeText(getApplicationContext(), "Accept successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Accept_friend_request.this, Friend_request.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleterequest(email,requsetedemail);
                Toast.makeText(getApplicationContext(), "Declined!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Accept_friend_request.this, Friend_request.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accept_friend_request.this, Friend_request.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

    }
}
