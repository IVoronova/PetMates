package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Admin_page extends AppCompatActivity {

    ArrayList<String> listUser;
    ArrayAdapter adapter;
    DatabaseHelper db;

    ListView userlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        db = new DatabaseHelper(this);

        listUser = new ArrayList<>();

        userlist = findViewById(R.id.ReportResult);

        viewData();

        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = userlist.getItemAtPosition(position).toString();
                Toast.makeText(Admin_page.this,""+text,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewData(){
        Cursor cursor = db.allData();

        if(cursor.getCount()==0) {
            Toast.makeText(this,"No data to show", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                listUser.add(cursor.getString(0));
            }
            adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listUser);
            userlist.setAdapter(adapter);
        }
    }
}
