package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Edit_profile extends AppCompatActivity {

    Button Save,Edit_image;
    EditText NewName,NewBio,NewPetType,NewPetBreed,NewPetGender,NewZip,NewP_PetType,NewP_PetBreed,NewP_PetGender,NewP_Other;
    TextView Back;
    DatabaseHelper db;
    String email;
    String imagepath = "";


    private static final int PICK_IMAGE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        db = new DatabaseHelper(this);

        //get email value from last activity
        Intent intent = getIntent();
        email = intent.getStringExtra("email");


        NewName = (EditText) findViewById(R.id.etEName);
        NewBio = (EditText) findViewById(R.id.etEBio);
        NewPetType = (EditText) findViewById(R.id.etEPet_type);
        NewPetBreed = (EditText) findViewById(R.id.etEPet_breed);
        NewPetGender = (EditText) findViewById(R.id.etEPet_gender);
        NewZip = (EditText) findViewById(R.id.etEzip);
        NewP_PetType = (EditText) findViewById(R.id.etEPreference_type);
        NewP_PetBreed = (EditText) findViewById(R.id.etEPreference_breed);
        NewP_PetGender = (EditText) findViewById(R.id.etEPreference_gender);
        NewP_Other = (EditText) findViewById(R.id.etEPreference_other);
        Edit_image = findViewById(R.id.btnInsert_image);

        Edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse(
                        "content://media/internal/images/media"));
                startActivityForResult(intent, PICK_IMAGE);
            }
        });


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
                String NP_PetTypeValue = NewP_PetType.getText().toString();
                String NP_PetBreedValue = NewP_PetBreed.getText().toString();
                String NP_PetGenderValue = NewP_PetGender.getText().toString();
                String NP_OtherValue = NewP_Other.getText().toString();


                if(!imagepath.equals("")) {
                    boolean success = db.update_info_image(email,imagepath);
                    if(success){
                        Toast.makeText(getApplicationContext(),"Create_Profile image update success",Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(getApplicationContext(),"Upload image failed! Please allow access file permissions in settings!",Toast.LENGTH_SHORT).show();
                }

                if(!NNameValue.equals("")){
                    db.update_info_Name(NNameValue,email);
                }

                if(!NBioValue.equals("")) {
                    db.update_info_Bio(NBioValue, email);
                }

                if(!NPetTypeValue.equals("")){
                    db.update_info_Pet_type(NPetTypeValue,email);
                }

                if(!NPetBreedValue.equals("")){
                    db.update_info_Pet_breed(NPetBreedValue,email);
                }

                if(!NPetGenderValue.equals("")){
                    db.update_info_Pet_gender(NPetGenderValue,email);
                }

                if(!NZipValue.equals("")){
                    db.update_info_Zip(NZipValue,email);
                }

                if(!NP_PetTypeValue.equals("")){
                    db.update_P_Pet_type(NP_PetTypeValue,email);
                }

                if(!NP_PetGenderValue.equals("")){
                    db.update_P_Pet_gender(NP_PetGenderValue,email);
                }

                if(!NP_PetBreedValue.equals("")){
                    db.update_P_Pet_Breed(NP_PetBreedValue,email);
                }

                if(!NP_OtherValue.equals("")){
                    db.update_P_Other(NP_OtherValue,email);
                }
                Intent intent = new Intent(Edit_profile.this, View_prefile.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Edit_profile.this, Main_menu.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,requestCode,data);
        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE){
            Uri uri = data.getData();
            imagepath += getPath(uri);
            //Toast.makeText(getApplicationContext(),x,Toast.LENGTH_SHORT).show();
            //if(db.insertImage(imagepath,email)){
            //    Toast.makeText(getApplicationContext(),"Successful!",Toast.LENGTH_SHORT).show();
            //}else Toast.makeText(getApplicationContext(),"Please allow access file permissions in settings!",Toast.LENGTH_SHORT).show();
        }
    }

    public String getPath(Uri uri){
        if(uri==null)return null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri,projection,null,null,null);
        if(cursor!=null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();return cursor.getString(column_index);
        }return uri.getPath();
    }
}


