package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText email,password;
    private TextView info;
    private Button login,register;
    private int counter = 3;
    DatabaseHelper databaseHelper;

    //Testing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText)findViewById(R.id.etName);
        password = (EditText)findViewById(R.id.etPassword);
        info = (TextView)findViewById(R.id.tvInfo);
        login = (Button) findViewById(R.id.btnLogin);
        register = (Button) findViewById(R.id.btnRegister);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();

                validate(emailValue, passwordValue);

                if(databaseHelper.isLoginVaild(emailValue,passwordValue)) {
                    Intent intent = new Intent(Login.this, MainMenu.class);
                    startActivity(intent);
                    Toast.makeText(Login.this,"Login Successful!", Toast.LENGTH_SHORT).show();

                }


            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private void validate(String userName, String userPassword){
        if((userName.equals("Admin")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(Login.this, MainMenu.class);
            startActivity(intent);
        }else{
            counter--;
            info.setText("Number of attempts remaining:" + String.valueOf(counter));
            if(counter == 0){
                login.setEnabled(false); //disable log button after three times
            }
        }
    }

}
