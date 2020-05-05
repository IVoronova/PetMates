package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Pairing_ByL_Result extends AppCompatActivity {
    DatabaseHelper db;
    TextView resultText,Back,name,bio,type,breed,gender;
    Button Send_Friend_Request,Pairing_again,report;
    String result,nameResult,bioResult,typerResult,breedResult,genderResult;
    ImageView imageResult;
    String email,pairedEmail;
    String depend = "L";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing__by_l__result);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        db = new DatabaseHelper(this);
        String zip;

        resultText = findViewById(R.id.bntP_result2);
        Send_Friend_Request = findViewById(R.id.btnsend_friend_requestL);
        Pairing_again = findViewById(R.id.btnPair_again);
        imageResult = findViewById(R.id.pairing_imageL);
        name = findViewById(R.id.etNameResult);
        bio = findViewById(R.id.etBioResult);
        type = findViewById(R.id.etPet_typeResult);
        breed = findViewById(R.id.etPet_breedResult);
        gender = findViewById(R.id.etPet_genderResult);
        report = findViewById(R.id.btnReportL);

        Back = findViewById(R.id.btnBackBL);

        Cursor preferences = db.getZip(email);
        preferences.moveToFirst();
        zip = preferences.getString(0);

        Cursor pairingResult = db.pair_location(email,zip);

        if(pairingResult.getCount()>0) {
            pairingResult.moveToFirst();

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

                result = "Unfortunately! \nNo result match your location.\nHowever, We find a random mate for you.";
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
                Intent intent1 = new Intent(Pairing_ByL_Result.this, Oops.class);
                intent.putExtra("email", email);
                startActivity(intent1);
            }
        }
        Send_Friend_Request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean sent = db.send_Request(email,pairedEmail);
                if(sent){
                    Toast.makeText(getApplicationContext(), "Friend request sent!", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(getApplicationContext(), "You have already sent request to this user!", Toast.LENGTH_SHORT).show();
            }
        });

        Pairing_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pairing_ByL_Result.this, Pairing_ByL_Result.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pairing_ByL_Result.this, Report_page.class);
                intent.putExtra("email", email);
                intent.putExtra("reportedEmail",pairedEmail);
                intent.putExtra("depend",depend);
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
