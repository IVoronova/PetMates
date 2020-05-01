package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Pairing_ByP_Result extends AppCompatActivity {
    DatabaseHelper db;
    TextView resultText,Back;
    Button Send_Friend_Request,Pairing_again;
    String result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing__by_p__result);

        Send_Friend_Request = findViewById(R.id.btnsend_friend_request);
        Pairing_again = findViewById(R.id.btnPair_again);
        Back = findViewById(R.id.btnBackBP);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");
        db = new DatabaseHelper(this);

        resultText = findViewById(R.id.bntP_result1);

        //get the user preferences
        Cursor preferences = db.getPreferences(email);
        preferences.moveToFirst();
        //get user preferences of pet type
        String Preferences1 = preferences.getString(0);
        //get user preferences of pet breed
        String Preferences2 = preferences.getString(1);
        //get user preferences of pet gender
        String Preferences3 = preferences.getString(2);

        //find the match result base on the user preferences we got
        Cursor pairingResult = db.pair_pre(email,Preferences1, Preferences2, Preferences3);
        pairingResult.moveToFirst();
        if(pairingResult.getCount()>0) {
            result = ("We found a mate for you! \nName: " + pairingResult.getString(1) +
                    "\nEmail: " + pairingResult.getString(0) +
                    "\nBio: " + pairingResult.getString(2) +
                    "\nPet Type: " + pairingResult.getString(3) +
                    "\nPet breed: " + pairingResult.getString(4) +
                    "\nPet gender: " + pairingResult.getString(5));
            //put the string result on the textview
            resultText.setText(result);
        }else {
            Cursor randomPair = db.getRandom(email);
            if (randomPair.getCount() > 0) {
                randomPair.moveToFirst();
                result = ("Unfortunately! No result match your preferences.\nHowever, We find a random mate for you. \nName: " + pairingResult.getString(1) +
                        "\nEmail: " + randomPair.getString(0) +
                        "\nBio: " + randomPair.getString(2) +
                        "\nPet Type: " + randomPair.getString(3) +
                        "\nPet breed: " + randomPair.getString(4) +
                        "\nPet gender: " + randomPair.getString(5));
                resultText.setText(result);
            }
            else{
                Toast.makeText(getApplicationContext(), "Oops!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(Pairing_ByP_Result.this, Oops.class);
                intent.putExtra("email", email);
                startActivity(intent1);
            }
        }
        Pairing_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pairing_ByP_Result.this, Pairing_ByP_Result.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pairing_ByP_Result.this, Pairing.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }

}
