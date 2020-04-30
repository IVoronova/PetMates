package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    Button Save,Edit_Preferences,Edit_information,Logout;
    EditText Name,Bio,Preferences,PetType,PetBreed,PetGender,Zip;
    DatabaseHelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        db = new DatabaseHelper(this);
        //get email value from last activity
        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");

        Name = (EditText)findViewById(R.id.etName);
        Bio = (EditText)findViewById(R.id.etBio);
        PetType = (EditText)findViewById(R.id.etPet_type);
        PetBreed = (EditText)findViewById(R.id.etPet_breed);
        PetGender = (EditText)findViewById(R.id.etPet_gender);
        Zip = (EditText)findViewById(R.id.etzip);

        Save = (Button) findViewById(R.id.btnProfile_save);
        Edit_Preferences = (Button)findViewById(R.id.btnProfile_preferences);
        Edit_information = (Button) findViewById(R.id.btnedit_account);
        Logout = (Button) findViewById(R.id.btnProfile_logout);
        //once check save, save the data to database
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String NameValue = Name.getText().toString();
                String BioValue = Bio.getText().toString();
                String PreferencesValue = Preferences.getText().toString();
                String PetTypeValue = PetType.getText().toString();
                String PetBreedValue = PetBreed.getText().toString();
                String PetGenderValue = PetGender.getText().toString();
                String ZipValue = Zip.getText().toString();
                //if one of user inputs are empty, give alert
                if (NameValue.equals("") || BioValue.equals("") || PreferencesValue.equals("") || PetTypeValue.equals("") || PetBreedValue.equals("") || PetGenderValue.equals("") || ZipValue.equals("")) {
                    Toast.makeText(getApplicationContext(), "Some fields are empty, please answer all the questions", Toast.LENGTH_SHORT).show();
                } else {
                    //else connect to database and insert data
                    boolean insert = db.insert_user_info(email, NameValue, BioValue, PreferencesValue, PetTypeValue, PetBreedValue, PetGenderValue, ZipValue);
                    if(insert == true){
                        //if insert successfully, message and jump to main menu
                        Toast.makeText(getApplicationContext(),"Update profile Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Profile.this, Main_menu.class);
                        startActivity(intent);
                    } else{
                    Toast.makeText(getApplicationContext(),"Something wrong, please try again",Toast.LENGTH_SHORT).show();
                }
                }
            }
        });

        Edit_Preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Preferences.class);
                startActivity(intent);
            }
        });

        Edit_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Account_Information.class);
                startActivity(intent);
            }
        });

        //while user check log out, jump to login page
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this,Login.class);
                Toast.makeText(getApplicationContext(),"Logout successfully",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

}
