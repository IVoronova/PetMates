package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Admin_view_suggestion extends AppCompatActivity {

    TextView allSuggestion,back;
    String showSuggestion ="";
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_suggestion);

        db = new DatabaseHelper(this);

        allSuggestion = findViewById(R.id.etallSuggestion);
        back = findViewById(R.id.btnBackadS);

        Cursor suggestion = db.getAll_suggest();
        while(suggestion.moveToNext()){
            showSuggestion += "Suggestion number: "+suggestion.getString(0)+
                    "\nSuggestion title: "+suggestion.getString(1)+
                    "\nSuggestion content: "+suggestion.getString(2)+
                    "\n\n\n";
        }

        allSuggestion.setText(showSuggestion);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_view_suggestion.this, Admin_page.class);
                startActivity(intent);
            }
        });
    }
}
