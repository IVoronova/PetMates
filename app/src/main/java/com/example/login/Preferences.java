package com.example.login;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Preferences extends AppCompatActivity {
    EditText Type, Breed, Gender, Other;
    Button Save;
    DatabaseHelper db;
    TextView Back,test;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        Intent intent = new Intent();
        email = intent.getStringExtra("email");

        test = findViewById(R.id.tvPreferences);
        String test1 = "Email: "+email;
        test.setText(test1);


        db = new DatabaseHelper(this);

        Type = (EditText)findViewById(R.id.etPreference_type);
        Breed = (EditText)findViewById(R.id.etPreference_breed);
        Gender = (EditText)findViewById(R.id.etPreference_gender);
        Other = (EditText)findViewById(R.id.etPreference_other);

        Save = (Button)findViewById(R.id.btnPreference_save);
        Back = findViewById(R.id.btnPreference_back);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String TypeValue = Type.getText().toString();
                String BreedValue = Breed.getText().toString();
                String GenderValue = Gender.getText().toString();
                String OtherValue = Other.getText().toString();
                if (TypeValue.equals("") || BreedValue.equals("")|| GenderValue.equals("") || OtherValue.equals("")){
                    Toast.makeText(getApplicationContext(), "Some fields are empty, please answer all the questions", Toast.LENGTH_SHORT).show();
                } else {
                    db.update_preferences(email,TypeValue,BreedValue,GenderValue,OtherValue);
                    Toast.makeText(getApplicationContext(),"Updated Preferences Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Preferences.this, Main_menu.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                }
            }
        });

        //goes back to profile page
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jump = new Intent(Preferences.this, Main_menu.class);
                jump.putExtra("email",email);
                startActivity(jump);
            }
        });

    }
}

