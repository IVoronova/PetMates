package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Oops extends AppCompatActivity {
    Button CreateMore;
    TextView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oops);

        //get email value from last activity
        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");

        CreateMore = findViewById(R.id.btnhaveFun);
        Back = findViewById(R.id.btnBackOO);

        CreateMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Oops.this, Register.class);
                Toast.makeText(getApplicationContext(),"You Can Create Right Now!",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Oops.this, Pairing.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
    }


}
