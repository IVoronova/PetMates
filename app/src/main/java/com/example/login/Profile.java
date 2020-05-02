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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    Button Save,Logout,Select_Image,Upload_Image;
    EditText Name,Bio,PetType,PetBreed,PetGender,Zip,P_PetType,P_PetBreed,P_PetGender,P_Other;
    TextView Back;
    ImageView userImage;

    DatabaseHelper db;
    String email;

    TextView test;

    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        db = new DatabaseHelper(this);

        //get email value from last activity
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        ///////////////////////////////////
        test = findViewById(R.id.tvtext);
        String test1 = "Email: " + email;
        //after test,change test1 to "Enter User Profile"
        ///////////////////////////////////
        test.setText(test1);
        Name = (EditText) findViewById(R.id.etName);
        Bio = (EditText) findViewById(R.id.etBio);
        PetType = (EditText) findViewById(R.id.etPet_type);
        PetBreed = (EditText) findViewById(R.id.etPet_breed);
        PetGender = (EditText) findViewById(R.id.etPet_gender);
        Zip = (EditText) findViewById(R.id.etzip);
        P_PetType = (EditText) findViewById(R.id.etPreference_type);
        P_PetBreed = (EditText) findViewById(R.id.etPreference_breed);
        P_PetGender = (EditText) findViewById(R.id.etPreference_gender);
        P_Other = (EditText) findViewById(R.id.etPreference_other);
        userImage = (ImageView)findViewById(R.id.user_image);


        //insert image and get image preview
        Select_Image = (Button) findViewById(R.id.btnInsert_image);
        Select_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse(
                        "content://media/internal/images/media"));
                startActivityForResult(intent, PICK_IMAGE);
                }
        });

        Upload_Image = findViewById(R.id.btnUpload_image);
        Upload_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userImage.setImageBitmap(db.getimage(email));
                /**
                 *

                Intent i = new Intent(Profile.this, Profile.class);
                finish();
                overridePendingTransition(0, 0);
                i.putExtra("email", email);
                startActivity(i);
                overridePendingTransition(0, 0);
                 */
            }
        });


        Save = (Button) findViewById(R.id.btnProfile_save);
        Logout = (Button) findViewById(R.id.btnProfile_logout);
        Back = findViewById(R.id.btnBackA);
        //once check save, save the data to database
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String NameValue = Name.getText().toString();
                String BioValue = Bio.getText().toString();
                String PetTypeValue = PetType.getText().toString();
                String PetBreedValue = PetBreed.getText().toString();
                String PetGenderValue = PetGender.getText().toString();
                String ZipValue = Zip.getText().toString();
                String P_PetTypeValue = P_PetType.getText().toString();
                String P_PetBreedValue = P_PetBreed.getText().toString();
                String P_PetGenderValue = P_PetGender.getText().toString();
                String P_OtherValue = P_Other.getText().toString();

                //if one of user inputs are empty, give alert
                if (NameValue.equals("") || BioValue.equals("")|| PetTypeValue.equals("") || PetBreedValue.equals("") || PetGenderValue.equals("") || ZipValue.equals("")||
                        P_PetTypeValue.equals("") ||P_PetBreedValue.equals("") ||P_PetGenderValue.equals("") ||P_OtherValue.equals("")) {
                    Toast.makeText(getApplicationContext(), "Some fields are empty, please answer all the questions", Toast.LENGTH_SHORT).show();
                } else {
                    //else connect to database and insert data
                    boolean insert = db.insert_user_info(email, NameValue, BioValue, PetTypeValue, PetBreedValue, PetGenderValue, ZipValue,
                            P_PetTypeValue, P_PetBreedValue, P_PetGenderValue, P_OtherValue);
                    if (insert) {
                        //if insert successfully, message and jump to main menu
                        Toast.makeText(getApplicationContext(), "User profile complete!", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(getApplicationContext(), "You already entered your user profile\nIf you want to change your user profile," +
                            "please check \"Edit User Profile\" button!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //while user check log out, jump to login page
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this,Login.class);
                Toast.makeText(getApplicationContext(),"Logout successfully",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this,Main_menu.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
    }

    //setting the onactivityresult method and fetching the location of image;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,requestCode,data);
        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE){
            Uri uri = data.getData();
            String path = getPath(uri);
            //Toast.makeText(getApplicationContext(),x,Toast.LENGTH_SHORT).show();
            if(db.insertImage(path,email)){
                Toast.makeText(getApplicationContext(),"Successful!",Toast.LENGTH_SHORT).show();
            }else Toast.makeText(getApplicationContext(),"Please allow access file permissions in settings!",Toast.LENGTH_SHORT).show();
        }
    }


    public String getPath(Uri uri){
        if(uri==null)return null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri,projection,null,null,null);
        if(cursor!=null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

}
