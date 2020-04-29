package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText email,password;
    public Button login;
    public TextView registerNew;
    private int counter = 3;
    DatabaseHelper db;
    String emailValue,passwordValue;

    //Testing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //connect database
        db = new DatabaseHelper(this);
        //get the input
        email = (EditText)findViewById(R.id.etName);
        password = (EditText)findViewById(R.id.etPassword);
        registerNew = findViewById(R.id.btnRegister_new);

        login = (Button) findViewById(R.id.btnLogin);
        //when user check login check the input
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailValue = email.getText().toString();
                passwordValue = password.getText().toString();
                Boolean Chkemailpassword = db.emailpassword(emailValue,passwordValue);
                //if input is correct, user login successfully and jump to main meun
                if(Chkemailpassword==true){
                    Toast.makeText(getApplicationContext(),"Successfully Login",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this,Main_menu.class);
                    //save emailValue to next activity
                    intent.putExtra("email",emailValue);
                    startActivity(intent);
                }else {//else number of attempts minus 1
                    counter--;
                    Toast.makeText(getApplicationContext(), "Wrong email or password!\nNumber of attempts remaining:" + String.valueOf(counter), Toast.LENGTH_SHORT).show();
                    if (counter == 0) {
                        login.setEnabled(false); //disable log button after three times
                    }
                }
            }
        });

        registerNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });


    }
}


