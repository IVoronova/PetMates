package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Application_Support extends AppCompatActivity {

    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application__support);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");

        back = findViewById(R.id.btnBackA);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Application_Support.this,Main_menu.class);
                intent.putExtra("email",email);
                startActivity(intent);

            }
        });
    }
}
