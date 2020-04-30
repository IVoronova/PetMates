package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Preferences extends AppCompatActivity {
    EditText Type, Breed, Gender, Other;
    Button Save, Back;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        db = new DatabaseHelper(this);
        Intent intent = new Intent();
        final String email = intent.getStringExtra("email");

        Type = (EditText)findViewById(R.id.etPreference_type);
        Breed = (EditText)findViewById(R.id.etPreference_breed);
        Gender = (EditText)findViewById(R.id.etPreference_gender);
        Other = (EditText)findViewById(R.id.etPreference_other);

        Save = (Button)findViewById(R.id.btnPreference_save);
        Back = (Button)findViewById(R.id.btnPreference_back);

        //goes back to profile page
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Preferences.this, Profile.class);
                startActivity(intent);
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String TypeValue = Type.getText().toString();
                String BreedValue = Breed.getText().toString();
                String GenderValue = Gender.getText().toString();
                String OtherValue = Other.getText().toString();

                //boolean insert = db.insert_user_preference(email,TypeValue,BreedValue,GenderValue,OtherValue);
                //if(insert == true){
                    //if insert successfully, message and jump to main menu
                    Toast.makeText(getApplicationContext(),"Updated Preferences Successfully", Toast.LENGTH_SHORT).show();
                //} else {
                  //  Toast.makeText(getApplicationContext(), "Something is wrong, please try again", Toast.LENGTH_SHORT).show();
                //}
            }
        });

    }
}
