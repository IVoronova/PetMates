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

public class View_prefile extends AppCompatActivity {
    Button View_Account_Information, Edit_profile,Log_out;
    TextView Name,Bio,PetType,PetBreed,PetGender,Zip,P_PetType,P_PetBreed,P_PetGender,P_Other,title;
    TextView Back;
    ImageView userImage;
    String email,NameValue,BioValue,PetTypeValue,PetBreedValue,PetGenderValue,ZipValue,P_PetTypeValue,P_PetBreedValue,P_PetGenderValue,P_OtherValue,TitleValue;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prefile);

        db = new DatabaseHelper(this);

        //get email value from last activity
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        Name = findViewById(R.id.etNameView);
        Bio = findViewById(R.id.etBioView);
        PetType = findViewById(R.id.etPet_typeView);
        PetBreed = findViewById(R.id.etPet_breedView);
        PetGender = findViewById(R.id.etPet_genderView);
        Zip = findViewById(R.id.etzipView);
        P_PetType = findViewById(R.id.etPreference_typeView);
        P_PetBreed = findViewById(R.id.etPreference_breedView);
        P_PetGender = findViewById(R.id.etPreference_genderView);
        P_Other = findViewById(R.id.etPreference_otherView);

        userImage = (ImageView)findViewById(R.id.user_imageView);

        View_Account_Information = findViewById(R.id.btnview_account);
        Edit_profile = findViewById((R.id.btnedit_profile));
        Log_out = findViewById(R.id.btnProfile_logout);
        Back = findViewById(R.id.btnBackAA);
        title = findViewById(R.id.user_information_title);


        View_Account_Information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(View_prefile.this, View_account_information.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        Edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Some bug exist,edit profile not working right now.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(View_prefile.this, Edit_profile.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        Log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(View_prefile.this, Login.class);
                startActivity(intent);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(View_prefile.this, Main_menu.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });


        Cursor get_profile = db.getAll_User_info(email);
        get_profile.moveToFirst();
            TitleValue = get_profile.getString(1)+"\' User Create_Profile";
            title.setText(TitleValue);

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

            P_PetTypeValue = "Pet Type: "+get_profile.getString(7);
            P_PetType.setText(P_PetTypeValue);

            P_PetBreedValue = "Pet Type: "+get_profile.getString(8);
            P_PetBreed.setText(P_PetBreedValue);

            P_PetGenderValue = "Pet Gender: "+get_profile.getString(9);
            P_PetGender.setText(P_PetGenderValue);

            P_OtherValue = "Other: "+get_profile.getString(10);
            P_Other.setText(P_OtherValue);

            userImage.setImageBitmap(db.getimage(email));
    }
}
