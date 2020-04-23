package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;

public class Login extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private TextView info;
    private Button login;
    private int counter = 3;

    //Testing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = (EditText)findViewById(R.id.etName);
        password = (EditText)findViewById(R.id.etPassword);
        info = (TextView)findViewById(R.id.tvInfo);
        login = (Button) findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(name.getText().toString(), password.getText().toString());
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
