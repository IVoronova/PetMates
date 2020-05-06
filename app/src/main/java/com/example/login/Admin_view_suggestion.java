package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Admin_view_suggestion extends AppCompatActivity {

    TextView back;
    ListView allSuggestion;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_suggestion);

        db = new DatabaseHelper(this);

        allSuggestion = findViewById(R.id.etallSuggestion);
        back = findViewById(R.id.btnBackadS);

        ArrayList<String> list = new ArrayList<>();

        Cursor suggestion = db.getAll_suggest();
        if(suggestion.getCount()==0){
            Toast.makeText(getApplicationContext(), "No suggestion!", Toast.LENGTH_SHORT).show();
        }else {
            while (suggestion.moveToNext()) {
                list.add("\nSuggestion number: \n" + suggestion.getString(0) +
                        "\nSuggestion title: \n" + suggestion.getString(1) +
                        "\nSuggestion content: \n" + suggestion.getString(2) +
                        "\n");
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
                allSuggestion.setAdapter(listAdapter);
            }
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_view_suggestion.this, Admin_page.class);
                startActivity(intent);
            }
        });
    }
}
