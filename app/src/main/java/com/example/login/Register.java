package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.service.autofill.AutofillService;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;




@SuppressLint("Registered")
public class Register extends AppCompatActivity {
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Button _Register;
    EditText _Email,_Password,_Phone;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        _Register = (Button)findViewById(R.id.btnRegister);
        _Email = (EditText)findViewById(R.id.etEmail);
        _Password = (EditText)findViewById(R.id.etPassword);
        _Phone = (EditText)findViewById(R.id.etPhone);

        databaseHelper = new DatabaseHelper(this);

        _Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openHelper.getWritableDatabase();
                String EmailValue = _Email.getText().toString();
                String PasswordValue = _Password.getText().toString();
                String PhoneValue = _Phone.getText().toString();

                if(EmailValue.length()!=0) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("Email", EmailValue);
                    contentValues.put("Password", PasswordValue);
                    contentValues.put("Phone", PhoneValue);

                    databaseHelper.insertUser(contentValues);
                    Toast.makeText(Register.this, "register Successful!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Register.this,"Invalid user input!",Toast.LENGTH_SHORT).show();



                }
            };


        });
    }



}


