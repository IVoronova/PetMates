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
    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);
        db = new DatabaseHelper(this);
        back = findViewById(R.id.btnBackP);


        By_Preferences = findViewById(R.id.btnPair_Preferences);
        By_location = findViewById(R.id.btnPair_Location);
        By_Preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = db.allData();
                if(cursor.getCount()==0){
                    Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_SHORT).show();
                }
                /////////////////////////////////////////////////////////////////

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pairing.this,Main_menu.class);
                startActivity(intent);

            }
        });
    }
}
