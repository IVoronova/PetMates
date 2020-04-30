package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Preferences extends AppCompatActivity {
    EditText Type, Breed, Gender, Other;
    Button Save, Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        Type = (EditText)findViewById(R.id.etPreference_type);
        Breed = (EditText)findViewById(R.id.etPreference_breed);
        Gender = (EditText)findViewById(R.id.etPreference_gender);
        Other = (EditText)findViewById(R.id.etPreference_other);

        Save = (Button)findViewById(R.id.btnPreference_save);
        Back = (Button)findViewById(R.id.btnPreference_back);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Preferences.this, Profile.class);
                startActivity(intent);
            }
        });
    }
}
