package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText email,password;
    public TextView info;
    public Button login, back;
    private int counter = 3;
    DatabaseHelper db;
    String emailValue,passwordValue;

    //Testing
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DatabaseHelper(this);
        email = (EditText)findViewById(R.id.etName);
        password = (EditText)findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.btnLogin);
        back = (Button)findViewById(R.id.btnback);

        //goes back to main activity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //check user login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailValue = email.getText().toString();
                passwordValue = password.getText().toString();
                Boolean Chkemailpassword = db.emailpassword(emailValue,passwordValue);
                if(Chkemailpassword==true){
                    Toast.makeText(getApplicationContext(),"Successfully Login",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this,Main_menu.class);
                    intent.putExtra("email",emailValue);
                    startActivity(intent);
                }else {
                    counter--;
                    Toast.makeText(getApplicationContext(), "Wrong email or password!\nNumber of attempts remaining:" + String.valueOf(counter), Toast.LENGTH_SHORT).show();
                    if (counter == 0) {
                        login.setEnabled(false); //disable log button after three times
                    }
                }
            }
        });
    }
}


