package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Edit_profile extends AppCompatActivity {

    Button Save,Edit_information,Logout,Edit_Profile;
    //Button Edit_Preferences;
    EditText NewName,NewBio,NewPetType,NewPetBreed,NewPetGender,NewZip,NewP_PetType,NewP_PetBreed,NewP_PetGender,NewP_Other;
    TextView Back;
    DatabaseHelper db;

    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        db = new DatabaseHelper(this);

        //get email value from last activity
        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");

        test = findViewById(R.id.tvtext);
        String test1 = "Email: "+email;
        test.setText(test1);

        NewName = (EditText)findViewById(R.id.etEName);
        NewBio = (EditText)findViewById(R.id.etEBio);
        NewPetType = (EditText)findViewById(R.id.etEPet_type);
        NewPetBreed = (EditText)findViewById(R.id.etEPet_breed);
        NewPetGender = (EditText)findViewById(R.id.etEPet_gender);
        NewZip = (EditText)findViewById(R.id.etEzip);
        NewP_PetType = (EditText)findViewById(R.id.etEPreference_type);
        NewP_PetBreed = (EditText)findViewById(R.id.etEPreference_breed);
        NewP_PetGender = (EditText)findViewById(R.id.etEPreference_gender);
        NewP_Other = (EditText)findViewById(R.id.etEPreference_other);

        Save = (Button) findViewById(R.id.btnEProfile_save);
        Back = findViewById(R.id.btnBackA);
        //once check save, save the data to database
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String NNameValue = NewName.getText().toString();
                String NBioValue = NewBio.getText().toString();
                String NPetTypeValue = NewP_PetType.getText().toString();
                String NPetBreedValue = NewPetBreed.getText().toString();
                String NPetGenderValue = NewPetGender.getText().toString();
                String NZipValue = NewZip.getText().toString();
                String NP_PetTypeValue = NewZip.getText().toString();
                String NP_PetBreedValue = NewZip.getText().toString();
                String NP_PetGenderValue = NewZip.getText().toString();
                String NP_OtherValue = NewZip.getText().toString();

                //if one of user inputs are empty, give alert
                if (NNameValue.equals("") || NBioValue.equals("")|| NPetTypeValue.equals("") || NPetBreedValue.equals("") || NPetGenderValue.equals("") || NZipValue.equals("")||
                        NP_PetTypeValue.equals("") ||NP_PetBreedValue.equals("") ||NP_PetGenderValue.equals("") ||NP_OtherValue.equals("")) {
                    Toast.makeText(getApplicationContext(), "Some fields are empty, please answer all the questions", Toast.LENGTH_SHORT).show();
                } else {
                    //else connect to database and insert data
                    db.update_whole_profile(NNameValue, NBioValue, NPetTypeValue, NPetBreedValue, NPetGenderValue, NZipValue,
                            NP_PetTypeValue, NP_PetBreedValue, NP_PetGenderValue, NP_OtherValue, email);
                        //if insert successfully, message and jump to main menu
                        Toast.makeText(getApplicationContext(), "Edit profile successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Edit_profile.this,Main_menu.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                }
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Edit_profile.this,Main_menu.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
    }
}
