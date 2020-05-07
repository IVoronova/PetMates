package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Pairing_ByP_Result extends AppCompatActivity {
    DatabaseHelper db;
    TextView resultText,Back,name,bio,type,breed,gender;
    Button Send_Friend_Request,Pairing_again,report;
    String result,nameResult,bioResult,typerResult,breedResult,genderResult,pairedEmail;
    ImageView imageResult;
    String depend = "P";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing__by_p__result);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");
        db = new DatabaseHelper(this);

        resultText = findViewById(R.id.bntP_resultP);
        Send_Friend_Request = findViewById(R.id.btnsend_friend_requestP);
        Pairing_again = findViewById(R.id.btnPair_againP);
        imageResult = findViewById(R.id.pairing_imageP);
        name = findViewById(R.id.etNameResultP);
        bio = findViewById(R.id.etBioResultP);
        type = findViewById(R.id.etPet_typeResultP);
        breed = findViewById(R.id.etPet_breedResultP);
        gender = findViewById(R.id.etPet_genderResultP);
        Back = findViewById(R.id.btnBackBP);
        report = findViewById(R.id.btnReportP);
        Send_Friend_Request = findViewById(R.id.btnsend_friend_requestP);


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

            //save paired Email
            pairedEmail = pairingResult.getString(0);

            result = "We found a mate for you!";
            resultText.setText(result);

            imageResult.setImageBitmap(db.getimage(pairingResult.getString(0)));

            nameResult = "Name: " + pairingResult.getString(1);
            name.setText(nameResult);

            bioResult = "Bio: " + pairingResult.getString(2);
            bio.setText(bioResult);

            typerResult = "Pet Type: " + pairingResult.getString(3);
            type.setText(typerResult);

            breedResult = "Pet breed: " + pairingResult.getString(4);
            breed.setText(breedResult);

            genderResult = "Pet gender: " + pairingResult.getString(5);
            gender.setText(genderResult);


        }else {
            Cursor randomPair = db.getRandom(email);
            randomPair.moveToFirst();
            if (randomPair.getCount() > 0) {

                //save paired Email
                pairedEmail = randomPair.getString(0);

                result = "Unfortunately!\nNo result match your Preferences.\nHowever, We find a random mate for you.";
                resultText.setText(result);

                imageResult.setImageBitmap(db.getimage(randomPair.getString(0)));

                nameResult = "Name: " + randomPair.getString(1);
                name.setText(nameResult);

                bioResult = "Bio: " + randomPair.getString(2);
                bio.setText(bioResult);

                typerResult = "Pet Type: " + randomPair.getString(3);
                type.setText(typerResult);

                breedResult = "Pet breed: " + randomPair.getString(4);
                breed.setText(breedResult);

                genderResult = "Pet gender: " + randomPair.getString(5);
                gender.setText(genderResult);

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

        Send_Friend_Request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.checkfriend(email, pairedEmail)) {
                    Toast.makeText(getApplicationContext(), "You are already friends!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean sent = db.send_Request(email, pairedEmail);
                    if (sent) {
                        Toast.makeText(getApplicationContext(), "Friend request sent!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "You have already sent request to this user!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pairing_ByP_Result.this, Report_page.class);
                intent.putExtra("email", email);
                intent.putExtra("reportedEmail",pairedEmail);
                intent.putExtra("depend",depend);
                startActivity(intent);
            }
        });
    }

}
