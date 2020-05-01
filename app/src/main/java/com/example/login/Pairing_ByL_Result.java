package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Pairing_ByL_Result extends AppCompatActivity {
    DatabaseHelper db;
    TextView resultText,Back;
    Button Send_Friend_Request,Pairing_again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing__by_l__result);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");
        db = new DatabaseHelper(this);
        String zip;

        TextView resultText = findViewById(R.id.bntP_result2);
        Send_Friend_Request = findViewById(R.id.btnsend_friend_request1);
        Pairing_again = findViewById(R.id.btnPair_again1);
        Back = findViewById(R.id.btnBackBL);

        Cursor preferences = db.getZip(email);
        preferences.moveToFirst();
        zip = preferences.getString(0);

        Cursor pairingResult = db.pair_location(email,zip);
        if(pairingResult.getCount()>0) {
            pairingResult.moveToFirst();
            String result = ("We found a mate for you! " +
                    "\nName: " + pairingResult.getString(1) +
                    "\nEmail: " + pairingResult.getString(0) +
                    "\nBio: " + pairingResult.getString(2) +
                    "\nPet Type: " + pairingResult.getString(3) +
                    "\nPet breed: " + pairingResult.getString(4) +
                    "\nPet gender: " + pairingResult.getString(5));
            resultText.setText(result);
        }else {
            Cursor randomPair = db.getRandom(email);
            if (randomPair.getCount() > 0) {
                randomPair.moveToFirst();
                String result = ("Unfortunately! No result match your location.\nHowever, We find a random mate for you." +
                        "\nName: " + randomPair.getString(1) +
                        "\nEmail: " + randomPair.getString(0) +
                        "\nBio: " + randomPair.getString(2) +
                        "\nPet Type: " + randomPair.getString(3) +
                        "\nPet breed: " + randomPair.getString(4) +
                        "\nPet gender: " + randomPair.getString(5));
                resultText.setText(result);
            }
                else{
                Toast.makeText(getApplicationContext(), "Oops!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(Pairing_ByL_Result.this, Oops.class);
                intent.putExtra("email", email);
                startActivity(intent1);
            }
        }
        Pairing_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pairing_ByL_Result.this, Pairing_ByL_Result.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pairing_ByL_Result.this, Pairing.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }

}
