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
    public Button login;
    private int counter = 3;
    DatabaseHelper db;

    //Testing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DatabaseHelper(this);
        email = (EditText)findViewById(R.id.etName);
        password = (EditText)findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();
                Boolean Chkemailpassword = db.emailpassword(emailValue,passwordValue);
                if(Chkemailpassword==true){
                    Toast.makeText(getApplicationContext(),"Successfully Login",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    counter--;
                    Toast.makeText(getApplicationContext(), "Worng email or password!\nNumber of attempts remaining:" + String.valueOf(counter), Toast.LENGTH_SHORT).show();
                    if (counter == 0) {
                        login.setEnabled(false); //disable log button after three times
                    }
                }
            }
        });
    }
}


