package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class View_account_information extends AppCompatActivity {

    TextView Email,Password,Phone,Back,title;
    Button Edit_information;
    String email,titleValue;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account_information);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        title = findViewById(R.id.tvView_account_information);

        Email = findViewById(R.id.etViewAccount_email);
        Password = findViewById(R.id.etViewAccount_Password);
        Phone = findViewById(R.id.etViewAccount_phone);

        Edit_information = findViewById(R.id.btnEdit_account_info);
        Back = findViewById(R.id.btnAccount_info_back);
        Cursor get_name = db.getName(email);
        get_name.moveToFirst();
        titleValue = get_name.getString(1)+"\' Account Information";
        title.setText(titleValue);

        Cursor get_profile = db.getAll_User(email);
        get_profile.moveToFirst();

        String EmailValue = "Email: "+ get_profile.getString(0);
        Email.setText(EmailValue);

        String PasswordValue = "Password: " +get_profile.getString(1);
        Password.setText(PasswordValue);

        String PhoneValue ="Phone: "+ get_profile.getString(2);
        Phone.setText(PhoneValue);

        Edit_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(View_account_information.this, Account_Information.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(View_account_information.this, View_prefile.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
    }
}
