package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Account_Information extends AppCompatActivity {
    EditText NewEmail, NewPassword, Confirm_NewPassword, NewPhone_Number;
    Button Save;
    DatabaseHelper db;
    String NewEmailValue;
    TextView Back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__information);
        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");

        NewEmail = (EditText)findViewById(R.id.etAccount_email);
        NewPassword = (EditText)findViewById(R.id.etAccount_Password);
        Confirm_NewPassword = (EditText)findViewById(R.id.etAccount_comfirm);
        NewPhone_Number = (EditText)findViewById(R.id.etAccount_phone);

        Save = (Button)findViewById(R.id.btnAccount_save);
        Back = findViewById(R.id.btnAccount_back);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NewEmailValue = NewEmail.getText().toString();
                String NewPasswordValue = NewPassword.getText().toString();
                String NewConfirm_PasswordValue = Confirm_NewPassword.getText().toString();
                String NewPhoneValue = NewPhone_Number.getText().toString();

                //if all the box is not empty
                //if(NewEmailValue.equals("")||NewPasswordValue.equals("")||NewConfirm_PasswordValue.equals("")||NewPhoneValue.equals("")) {
                //    Toast.makeText(getApplicationContext(), "Fields are empty, check your input.", Toast.LENGTH_SHORT).show();
                //}
                //else
                /*
                //if new email is not empty, update the email
                //if new email is not unique, sent alert and stop rest function
                if(!NewEmailValue.equals("")) {
                    //check the email is unique
                    if (db.email_Unique(NewEmailValue) == true) {
                        db.update_user_Email(NewEmailValue, email);
                        db.update_info_email(NewEmailValue, email);
                    } else {
                        Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Account_Information.this, Account_Information.class);
                        finish();
                        overridePendingTransition(0, 0);
                        i.putExtra("email", email);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                    }
                }
                //if user want to change both email and password
                else if(!NewEmailValue.equals("")&&!NewPasswordValue.equals("") && !NewConfirm_PasswordValue.equals("")){
                    if (NewPasswordValue.equals(NewConfirm_PasswordValue)) {
                            db.update_user_Password(NewPasswordValue, NewEmailValue);
                    }else {
                        Toast.makeText(getApplicationContext(), "Password doesn't match", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Account_Information.this, Account_Information.class);
                        finish();
                        overridePendingTransition(0, 0);
                        i.putExtra("email", email);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                    }
                }
                //if user only want to change the password
                else if(!NewPasswordValue.equals("") && !NewConfirm_PasswordValue.equals("")){
                    if (NewPasswordValue.equals(NewConfirm_PasswordValue)) {
                        db.update_user_Password(NewPasswordValue, email);
                    }else {
                        Toast.makeText(getApplicationContext(), "Password doesn't match", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Account_Information.this, Account_Information.class);
                        finish();
                        overridePendingTransition(0, 0);
                        i.putExtra("email", email);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                    }
                }
                //if user want to change both email and phone
                else if(!NewPhoneValue.equals("")&&!NewEmailValue.equals("")) {
                    db.update_user_Phone(NewPhoneValue, NewEmailValue);
                    email.replace(, 'T')
                }
                //if user only want to change phone
                else if(!NewPhoneValue.equals("")) {
                    db.update_user_Phone(NewPhoneValue, email);
                }
                Toast.makeText(getApplicationContext(), "Account information edit successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Account_Information.this, Main_menu.class);
                intent.putExtra("email", NewEmailValue);
                startActivity(intent);
                }
        });*/
                if(NewEmailValue.equals("")||NewPasswordValue.equals("")||NewConfirm_PasswordValue.equals("")||NewPhoneValue.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields are empty, check your input.", Toast.LENGTH_SHORT).show();
                }
                else {//check the email is unique
                    if (NewPasswordValue.equals(NewConfirm_PasswordValue)) {
                        if(db.email_Unique(NewEmailValue) == true) {
                            db.update_user(NewEmailValue, NewPasswordValue, NewPhoneValue, email);
                            db.update_info_email(NewEmailValue, email);
                            db.update_image_email(NewEmailValue,email);
                            Toast.makeText(getApplicationContext(), "Account information edit successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Account_Information.this, Main_menu.class);
                            intent.putExtra("email", NewEmailValue);
                            startActivity(intent);
                        }else Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(getApplicationContext(), "Password doesn't match", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account_Information.this, View_account_information.class);
                intent.putExtra("email",NewEmailValue);
                startActivity(intent);
            }
        });
    }
}
