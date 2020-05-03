package com.example.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String Database_Name = "PetMates.db";
    private static final String Table1 = "user";
    private static final String Table2 = "user_info";
    //private static final String Table3 = "user_image";

    private static final int version = 1;

    public DatabaseHelper(Context context) {
        super(context, Database_Name, null, version);
    }

    @Override
    //create database
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Table1 + " (Email varchar(50) PRIMARY KEY, Password varchar(50), Phone varchar(20))");
        db.execSQL("CREATE TABLE " + Table2 + " (Email TEXT PRIMARY KEY, Name TEXT, Bio TEXT,Pet_type TEXT, Pet_Breed TEXT, Pet_gender TEXT, Zip TEXT," +
                "P_Pet_type TEXT, P_Pet_Breed TEXT, P_Pet_gender TEXT, P_Other TEXT, Image blob)");
        //db.execSQL("CREATE TABLE " + Table3 + " (Email TEXT PRIMARY KEY, Image blob NOT NULL)");

    }
    //add trigger update before insert table2
    //begin
    //if insert image

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table1);
        db.execSQL("DROP TABLE IF EXISTS " + Table2);
        //db.execSQL("DROP TABLE IF EXISTS " + Table3);
        onCreate(db);
    }
    //////////////////////////////////////////////////////////////////////
    /////////////////////insert data to database//////////////////////////
    /////////////////////////////////////////////////////////////////////

    //insert data to user
    public boolean insert_user(String Email, String Password, String Phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();

        user.put("email", Email);
        user.put("password", Password);
        user.put("phone", Phone);
        long ins = db.insert(Table1, null, user);
        if (ins == -1) return false;
        else return true;
    }

    //insert data to user_info
    public Boolean insertImage(String Email, String Name, String Bio, String Pet_type,
                               String Pet_breed, String Pet_gender, String Zip, String P_Pet_type,
                               String P_Pet_breed, String P_Pet_gender, String P_Other, String ImagePath ){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            FileInputStream fs = new FileInputStream(ImagePath);
            byte[] imgbyte = new byte[fs.available()];
            fs.read(imgbyte);
            ContentValues contentValues = new ContentValues();
            contentValues.put("Email",Email);
            contentValues.put("Name", Name);
            contentValues.put("Bio", Bio);
            contentValues.put("Pet_type", Pet_type);
            contentValues.put("Pet_Breed", Pet_breed);
            contentValues.put("Pet_gender", Pet_gender);
            contentValues.put("Zip", Zip);
            contentValues.put("P_Pet_type", P_Pet_type);
            contentValues.put("P_Pet_breed", P_Pet_breed);
            contentValues.put("P_Pet_gender", P_Pet_gender);
            contentValues.put("P_Other", P_Other);
            contentValues.put("Image", imgbyte);
            db.insert(Table2,null,contentValues);
            fs.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////check account or profile exist or not///////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    //checking if email exist;
    public Boolean email_Unique(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table1 + " WHERE Email = ?", new String[]{email});
        if (cursor.getCount() > 0) return false;
        else return true;
    }

    //checking the email and password match or not
    public Boolean emailpassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table1 + " WHERE Email=? AND Password=?", new String[]{email, password});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////update user database////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    //update all user account information
    public void update_user(String Email, String Password, String Phone, String OldEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Email", Email);
        user.put("Password", Password);
        user.put("Phone", Phone);
        db.update(Table1, user, "Email=?", new String[]{String.valueOf(OldEmail)});
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////update user account individually///////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    public void update_user_Email(String Email, String OldEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Email", Email);
        db.update(Table1, user, "Email=?", new String[]{String.valueOf(OldEmail)});
    }
    //update user's account password
    public void update_user_Password(String Password, String OldEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Password", Password);
        db.update(Table1, user, "Email=?", new String[]{String.valueOf(OldEmail)});
    }
    //update user's account phone
    public void update_user_Phone(String Phone, String OldEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Phone", Phone);
        db.update(Table1, user, "Email=?", new String[]{String.valueOf(OldEmail)});
    }

    //after update primary key email,we have to update other table's primary key at same time
    public void update_info_email(String Email, String OldEmail) {
        if (chkprofile_exist(Email)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues user = new ContentValues();
            user.put("Email", Email);
            db.update(Table2, user, "Email=?", new String[]{String.valueOf(OldEmail)});
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////update user profile individually///////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    public Boolean update_info_image(String Email,String ImagePath ){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            FileInputStream fs = new FileInputStream(ImagePath);
            byte[] imgbyte = new byte[fs.available()];
            fs.read(imgbyte);
            ContentValues contentValues = new ContentValues();
            contentValues.put("Email",Email);
            contentValues.put("Image", imgbyte);
            db.update(Table2, contentValues, "Email=?", new String[]{String.valueOf(Email)});
            fs.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void update_P_Other(String P_Other,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("P_Other", P_Other);
        db.update(Table2, user, "Email=?", new String[]{String.valueOf(email)});
    }

    public void update_P_Pet_gender(String P_Pet_gender,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("P_Pet_gender", P_Pet_gender);
        db.update(Table2, user, "Email=?", new String[]{String.valueOf(email)});
    }

    public void update_P_Pet_Breed(String P_Pet_Breed,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("P_Pet_Breed", P_Pet_Breed);
        db.update(Table2, user, "Email=?", new String[]{String.valueOf(email)});
    }

    public void update_P_Pet_type(String P_Pet_type,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("P_Pet_type", P_Pet_type);
        db.update(Table2, user, "Email=?", new String[]{String.valueOf(email)});
    }

    public void update_info_Zip(String Zip,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Zip", Zip);
        db.update(Table2, user, "Email=?", new String[]{String.valueOf(email)});
    }

    public void update_info_Pet_gender(String Pet_gender,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Pet_gender", Pet_gender);
        db.update(Table2, user, "Email=?", new String[]{String.valueOf(email)});
    }

    public void update_info_Pet_breed(String Pet_breed,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Pet_Breed", Pet_breed);
        db.update(Table2, user, "Email=?", new String[]{String.valueOf(email)});
    }


    public void update_info_Pet_type(String Pet_type,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Pet_type", Pet_type);
        db.update(Table2, user, "Email=?", new String[]{String.valueOf(email)});
    }

    public void update_info_Name(String name,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Name", name);
        db.update(Table2, user, "Email=?", new String[]{String.valueOf(email)});
    }

    public void update_info_Bio(String Bio,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Bio", Bio);
        db.update(Table2, user, "Email=?", new String[]{String.valueOf(email)});
    }
    //////////////////////////////////////////////////////////////////////////////////////


    //check the user profile exist or not
    public Boolean chkprofile_exist(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table2 + " WHERE Email = ?", new String[]{email});
        if (cursor.getCount() > 0) return false;
        else return true;
    }


   //get all data from a table
    public Cursor getAll_Data() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table1,null);
        return cursor;
    }
    //get entire row of account data for current user
    public Cursor getAll_User(String Email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table1+" WHERE Email=?",new String[]{Email});
        return cursor;
    }
    //get entire row of profile data for current user
    public Cursor getAll_User_info(String Email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table2+" WHERE Email=?",new String[]{Email});
        return cursor;
    }

    //get user name
    public Cursor getName(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table2 + " WHERE Email = ?", new String[]{email});
        return cursor;
    }


    ///////////////////////////////////////////////////////
    ///////////////////for pairing/////////////////////////
    ///////////////////////////////////////////////////////
    //randomly pick 1 mate by preferences
    public Cursor pair_pre(String email, String P_Pet_type, String P_Pet_breed, String P_Pet_gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Email, Name, Bio, Pet_type, Pet_breed, Pet_gender FROM " + Table2 +
                " WHERE Pet_type =? AND Pet_breed=? AND Pet_gender=? AND Email !=? COLLATE NOCASE ORDER BY RANDOM() LIMIT 1", new String[]{P_Pet_type, P_Pet_breed, P_Pet_gender, email});
        return cursor;
    }

    //randomly pick 1 mate by location
    public Cursor pair_location(String email, String zip) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Email, Name, Bio, Pet_type, Pet_breed, Pet_gender FROM " + Table2 +
                " WHERE zip = (SELECT Zip FROM " + Table2 + " WHERE Email =? AND Email !=? ORDER BY RANDOM() LIMIT 1)", new String[]{zip, email});
        return cursor;
    }

    //Retrieve all database from user_info
    public Cursor allData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table2 + ";", null);
        return cursor;
    }

    //Retrieve user preferences from user_info
    public Cursor getPreferences(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT P_Pet_type, P_Pet_breed, P_Pet_gender FROM " + Table2 + " WHERE Email =?;", new String[]{email});
        return cursor;
    }

    //Retrieve user zip from user_info
    public Cursor getZip(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Zip FROM " + Table2 + " WHERE Email =?;", new String[]{email});
        return cursor;
    }

    //if couldn't find any pair mate, give a random mate
    public Cursor getRandom(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Email, Name, Bio, Pet_type, Pet_breed, Pet_gender FROM " + Table2 +
                " WHERE Email !=? ORDER BY RANDOM() LIMIT 1", new String[]{email});
        return cursor;
    }

    //////////////////////////////////////////////////////////////
    //////////////////////get image////////////////////////////
    //////////////////////////////////////////////////////////////

    //retrieve image from database where email = given
    public Bitmap getimage(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bt = null;
        Cursor cursor = db.rawQuery("SELECT * from "+Table2+" WHERE email=?",new String[]{email});
        if(cursor.moveToNext()){
            byte[] Image = cursor.getBlob(11);
            bt = BitmapFactory.decodeByteArray(Image,0,Image.length);
        }
        return bt;
    }
    //////////////////////////////////////////////////////////////////////////////////////////


}
