package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class Register extends AppCompatActivity {
    SQLiteOpenHelper openHelper;
    Button _Register, _Back;
    EditText _Email,_Password,_Repassword,_Phone;
    DatabaseHelper databaseHelper;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new DatabaseHelper(this);

        _Register = (Button)findViewById(R.id.btnRegister);
        _Email = (EditText)findViewById(R.id.etEmail);
        _Password = (EditText)findViewById(R.id.etPassword);
        _Repassword = (EditText)findViewById(R.id.etrePassword);
        _Phone = (EditText)findViewById(R.id.etPhone);
        _Back = (Button)findViewById(R.id.btnback);

        //goes back to main activity
        _Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
            }
        });


        _Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EmailValue = _Email.getText().toString();
                String PasswordValue = _Password.getText().toString();
                String RepasswordValue = _Repassword.getText().toString();
                String PhoneValue = _Phone.getText().toString();

                if(EmailValue.equals("")||PasswordValue.equals("")||RepasswordValue.equals("")||PhoneValue.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields are empty, check your input.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(PasswordValue.equals(RepasswordValue)){
                        Boolean chkemail = db.chkemail(EmailValue);
                        if(chkemail == true) {
                            Boolean insert = db.insert(EmailValue,PasswordValue,PhoneValue);
                            if(insert == true){
                                Toast.makeText(getApplicationContext(),"Registered Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Email already exists",Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(getApplicationContext(),"Password doesn't match",Toast.LENGTH_SHORT).show();
                }
            };
        });
    }
}


