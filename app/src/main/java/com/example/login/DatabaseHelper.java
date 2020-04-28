package com.example.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




public class DatabaseHelper extends SQLiteOpenHelper {
    private static String name = "PetMates.db";
    private static int version = 1;


    public DatabaseHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS user (Email varchar(50) primary key, Password varchar(50), Phone varchar(20))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
    }
    //inserting in database
    public boolean insert(String Email, String Password, String Phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",Email);
        contentValues.put("password",Password);
        contentValues.put("phone",Phone);
        long ins = db.insert("user",null, contentValues);
        if
        (ins==-1) return false;
        else return true;

    }
    //checking if email exist;
    public  Boolean chkemail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE Email = ?",new String[]{email});
        if(cursor.getCount()>0) return false;
        else return true;
    }
    //checking the email and password match or not
    public  Boolean emailpassword(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE Email=? AND Password=?",new String[]{email,password});
        if(cursor.getCount()>0)return true;
        else return false;
    }

}
