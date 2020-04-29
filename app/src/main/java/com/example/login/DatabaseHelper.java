package com.example.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String Database_Name = "PetMates.db";
    private static final String Table1 = "user";
    private static final String Table2 = "user_info";
    private static final int version = 1;
    private String id_value;


    public DatabaseHelper(Context context) {
        super(context, Database_Name, null, version);
    }

    @Override
    //create database
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+Table1+ " (Email varchar(50) PRIMARY KEY, Password varchar(50), Phone varchar(20))");
        db.execSQL("CREATE TABLE "+Table2+" (Email TEXT PRIMARY KEY, Name TEXT, Bio TEXT, Preferences TEXT,Pet_type TEXT, Pet_Breed TEXT, Pet_gender TEXT, Zip TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table1);
        db.execSQL("DROP TABLE IF EXISTS "+Table2);
        onCreate(db);
    }

    //insert data to user
    public boolean insert_user(String Email, String Password, String Phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();

        user.put("email",Email);
        user.put("password",Password);
        user.put("phone",Phone);
        long ins = db.insert(Table1,null, user);
        if (ins==-1) return false;
        else return true;
    }

    //insert data to user_info
    public boolean insert_user_info(String Email, String Name, String Bio, String Preferences, String Pet_type,
                          String Pet_breed, String Pet_gender, String Zip) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user_info = new ContentValues();

        user_info.put("Email",Email);
        user_info.put("Name",Name);
        user_info.put("Bio",Bio);
        user_info.put("Preferences",Preferences);
        user_info.put("Pet_type",Pet_type);
        user_info.put("Pet_Breed",Pet_breed);
        user_info.put("Pet_gender",Pet_gender);
        user_info.put("Zip",Zip);

        long ins = db.insert(Table2,null, user_info);
        if (ins==-1) return false;
        else return true;
    }
    //checking if email exist;
    public  Boolean chkemail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Table1+ " WHERE Email = ?",new String[]{email});
        if(cursor.getCount()>0) return false;
        else return true;
    }
    //checking the email and password match or not
    public  Boolean emailpassword(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Table1+" WHERE Email=? AND Password=?",new String[]{email,password});
        if(cursor.getCount()>0)return true;
        else return false;
    }

    //check the user profile exist or not
    public  Boolean chkprofile_exist(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Table2+ " WHERE Email = ?",new String[]{email});
        if(cursor.getCount()>0) return false;
        else return true;
    }
    //get user name
    public Cursor getName(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Table2+ " WHERE Name = ?",new String[]{email});
        return cursor;
    }
    /*get the id after user sign in
    public String get_id(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM "+Table1+" WHERE Email=?",new String[]{email});
        if(cursor.getCount()>0){
            id_value = (cursor.getString(0));
        }
        return id_value;
    }*/

    //Retrieve all database from user_info
    public Cursor allData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Table2+";",null);
        return cursor;
    }
    ////////////////////////////////////////////////////////////////////////////////////
    public Cursor pair_pre(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Email, Name, Bio, Pet_type, Pet_breed, Pet_gender FROM "+Table2+";",null);
        return cursor;
    }// need to fix///////////////////////////////////////////////////////////////////////

    public Cursor pair_location(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Email, Name, Bio, Pet_type, Pet_breed, Pet_gender FROM "+Table2+
                " WHERE zip = (SELECT Zip FROM "+Table2+" WHERE Email =?)",new String[]{email});
        return cursor;
    }

}
