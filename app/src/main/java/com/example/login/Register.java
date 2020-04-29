package com.example.login;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class Register extends AppCompatActivity {
    Button _Register;
    EditText _Email,_Password,_Repassword,_Phone;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new DatabaseHelper(this);


        _Email = (EditText)findViewById(R.id.etEmail);
        _Password = (EditText)findViewById(R.id.etPassword);
        _Repassword = (EditText)findViewById(R.id.etrePassword);
        _Phone = (EditText)findViewById(R.id.etPhone);
        _Register = (Button)findViewById(R.id.btnRegister);
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
                            boolean insert = db.insert_user(EmailValue,PasswordValue,PhoneValue);
                            if(insert == true){
                                Toast.makeText(getApplicationContext(),"Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Register.this, Login.class);
                                startActivity(intent);
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


