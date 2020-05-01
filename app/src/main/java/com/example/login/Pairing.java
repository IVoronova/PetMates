package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Pairing extends AppCompatActivity {

    Button By_Preferences,By_location;
    TextView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");

        back = findViewById(R.id.btnBackP);


        By_Preferences = findViewById(R.id.btnPair_Preferences);
        By_Preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Pairing.this,Pairing_ByP_Result.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                }
        });

        By_location = findViewById(R.id.btnPair_Location);
        By_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Pairing.this,Pairing_ByL_Result.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                }

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pairing.this,Main_menu.class);
                intent.putExtra("email",email);
                startActivity(intent);

            }
        });
    }
}
