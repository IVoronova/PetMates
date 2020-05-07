package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Search_friend extends AppCompatActivity {

    EditText searchbox;
    Button search,add;
    TextView name,bio,type,breed,gender,back,zip,nameshow;
    ImageView userImage;
    DatabaseHelper db;
    String email,searchValue,NameValue,BioValue,PetTypeValue,PetBreedValue,PetGenderValue,ZipValue,titleValue,Searchedemail,showname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        db = new DatabaseHelper(this);

        searchbox = findViewById(R.id.etquestionTitle);
        search = findViewById(R.id.etsearchButton);
        add = findViewById(R.id.btnsend_friend_requestS);
        name = findViewById(R.id.etNameViewSearch);
        bio = findViewById(R.id.etBioViewSearch);
        type = findViewById(R.id.etPet_typeViewSearch);
        breed = findViewById(R.id.etPet_breedViewSearch);
        gender = findViewById(R.id.etPet_genderViewSearch);
        zip = findViewById(R.id.etzipViewSearch);
        back = findViewById(R.id.btnBacksearch);
        nameshow = findViewById(R.id.titleSearchinfo);
        userImage = findViewById(R.id.user_imageViewSearch);

        searchValue = searchbox.getText().toString();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchValue = searchbox.getText().toString();

                if(searchValue.equals("")){
                    Toast.makeText(getApplicationContext(), "Search input can not be empty!", Toast.LENGTH_SHORT).show();
                }else {
                    Cursor get_profile = db.getAll_User_info(searchValue);
                    if (get_profile.getCount() > 0) {
                        get_profile.moveToFirst();

                        Searchedemail = get_profile.getString(0);
                        titleValue = get_profile.getString(1) + "\' User Profile";
                        nameshow.setText(titleValue);

                        showname = get_profile.getString(1);
                        NameValue = "Name: " + get_profile.getString(1);
                        name.setText(NameValue);

                        BioValue = "Bio: " + get_profile.getString(2);
                        bio.setText(BioValue);

                        PetTypeValue = "Pet Type: " + get_profile.getString(3);
                        type.setText(PetTypeValue);

                        PetBreedValue = "Pet Breed: " + get_profile.getString(4);
                        breed.setText(PetBreedValue);

                        PetGenderValue = "Pet Gender: " + get_profile.getString(5);
                        gender.setText(PetGenderValue);

                        ZipValue = "Zip :" + get_profile.getString(6);
                        zip.setText(ZipValue);

                        userImage.setImageBitmap(db.getimage(searchValue));
                    } else {
                        Toast.makeText(getApplicationContext(), "No result!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchValue.equals("")){
                    Toast.makeText(getApplicationContext(), "You haven't search a target!", Toast.LENGTH_SHORT).show();
                }
                else if(searchValue.equals(email)){
                    Toast.makeText(getApplicationContext(), "THIS IS YOU! You can not send friend request to yourself", Toast.LENGTH_SHORT).show();
                } else if (db.checkfriend(email, Searchedemail)) {
                    Toast.makeText(getApplicationContext(), "You and "+showname+" are already friends!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean sent = db.send_Request(email, Searchedemail);
                    if (sent) {
                        Toast.makeText(getApplicationContext(), "Friend request sent!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "You have already sent request to this user!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search_friend.this, Friend_request.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });


    }
}
