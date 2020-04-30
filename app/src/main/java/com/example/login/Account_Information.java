package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Account_Information extends AppCompatActivity {
    EditText Email, Password, Comfirm_Password, Phone_Number;
    Button Save, Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__information);

        //Initialize all values
        Email = (EditText)findViewById(R.id.etAccount_email);
        Password = (EditText)findViewById(R.id.etAccount_Password);
        Comfirm_Password = (EditText)findViewById(R.id.etAccount_comfirm);
        Phone_Number = (EditText)findViewById(R.id.etAccount_phone);
        Save = (Button)findViewById(R.id.btnAccount_save);
        Back = (Button)findViewById(R.id.btnAccount_back);

        //Goes back to Profile settings
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account_Information.this, Profile.class);
                startActivity(intent);
            }
        });
    }
}
